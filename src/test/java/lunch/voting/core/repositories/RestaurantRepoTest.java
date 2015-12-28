package lunch.voting.core.repositories;

import lunch.voting.core.models.entities.Restaurant;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/business-config.xml")
public class RestaurantRepoTest {

    @Autowired
    private RestaurantRepo repo;

    private Restaurant restaurant;

    @Before
    @Transactional
    @Rollback(false)
    public void setup()
    {
        restaurant = new Restaurant();
        restaurant.setTitle("test restaurant");
        restaurant.setAddress("test address");
        restaurant.setLunchMenu("{\"dish\" : \"price\"}");
        repo.create(restaurant);
    }

    @Test
    @Transactional
    public void testFind()
    {
        Restaurant restaurant = repo.find(this.restaurant.getId());
        assertNotNull(restaurant);
        assertEquals(restaurant.getTitle(), "test restaurant");
        assertEquals(restaurant.getAddress(), "test address");
    }

    @Test
    @Transactional
    public void testFindAll() {
        List<Restaurant> list = repo.findAll();
        assertEquals(list.size(), 1);
    }
}

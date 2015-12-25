package lunch.voting.core.repositories.jpa;

import lunch.voting.core.models.entities.Restaurant;
import lunch.voting.core.repositories.RestaurantRepo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 *
 */
@Repository
public class JpaRestaurantRepo implements RestaurantRepo {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Restaurant create(Restaurant data) {
        em.persist(data);
        return data;
    }

    @Override
    public Restaurant update(Long id, Restaurant data) {
        Restaurant entry = em.find(Restaurant.class, id);
        entry.setTitle(data.getTitle());
        entry.setAddress(data.getAddress());
        entry.setLunchMenu(data.getLunchMenu());
        return entry;
    }

    @Override
    public List<Restaurant> findAll() {
        Query query = em.createQuery("SELECT r from Restaurant r");
        return query.getResultList();
    }

    @Override
    public Restaurant find(Long id) {
        return em.find(Restaurant.class, id);
    }

    @Override
    public List<Restaurant> findByTitle(String title) {
        Query query = em.createQuery("SELECT r from Restaurant r where r.title=?1");
        query.setParameter(1, title);
        List<Restaurant> restaurants = query.getResultList();
        return restaurants;
    }

}

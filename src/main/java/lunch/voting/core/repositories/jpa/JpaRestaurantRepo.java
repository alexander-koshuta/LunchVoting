package lunch.voting.core.repositories.jpa;

import lunch.voting.core.models.entities.Restaurant;
import lunch.voting.core.repositories.RestaurantRepo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Restaurant findExclusive(Long id) {
        Map<String,Object> properties = new HashMap();
        properties.put("javax.persistence.lock.timeout", 2000);
        return em.find(Restaurant.class, id, LockModeType.PESSIMISTIC_WRITE, properties);
    }

    @Override
    public List<Restaurant> findByTitle(String title) {
        Query query = em.createQuery("SELECT r from Restaurant r where r.title=?1");
        query.setParameter(1, title);
        List<Restaurant> restaurants = query.getResultList();
        return restaurants;
    }

    @Override
    public void resetCounters() {
        List<Restaurant> list = findAllExclusive();
        for (Restaurant restaurant : list) {
            restaurant.setCounter(0);
        }
    }

    private List<Restaurant> findAllExclusive() {
        Query query = em.createQuery("SELECT r from Restaurant r");
        query.setLockMode(LockModeType.PESSIMISTIC_WRITE);
        return query.getResultList();
    }
}

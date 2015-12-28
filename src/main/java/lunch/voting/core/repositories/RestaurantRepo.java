package lunch.voting.core.repositories;

import lunch.voting.core.models.entities.Restaurant;

import java.util.List;

/**
 *
 */
public interface RestaurantRepo {
    public Restaurant create(Restaurant restaurant);
    public Restaurant update(Long id, Restaurant data);
    public List<Restaurant> findAll();
    public Restaurant find(Long id);

    /**
     * Performs select for update on the restaurant entity with the provided id if found.
     * @param id - id of the restaurant to find.
     * @return found restaurant entity or null.
     */
    public Restaurant findExclusive(Long id);
    public List<Restaurant> findByTitle(String title);
    public void resetCounters();
}

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
    public List<Restaurant> findByTitle(String title);
}

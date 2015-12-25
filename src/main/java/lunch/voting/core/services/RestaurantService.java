package lunch.voting.core.services;

import lunch.voting.core.models.entities.Restaurant;

import java.util.List;

/**
 *
 */
public interface RestaurantService {
    public List<Restaurant> getAllRestaurants();
    public Restaurant createRestaurant(Restaurant data);
    public Restaurant updateRestaurant(Long id, Restaurant data);
    public Restaurant vote(Long restaurantId);
}

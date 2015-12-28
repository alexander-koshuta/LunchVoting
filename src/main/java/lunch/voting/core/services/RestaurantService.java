package lunch.voting.core.services;

import lunch.voting.core.models.entities.Restaurant;

import java.util.List;

/**
 * Performs all operations related to restaurants
 */
public interface RestaurantService {
    public List<Restaurant> getAllRestaurants();
    public Restaurant createRestaurant(Restaurant data);
    public Restaurant updateRestaurant(Long id, Restaurant data);

    /**
     * Calling this method will increase the voting counter of the restaurant with the provided restaurantId.
     * Optimistic lock is used to do the update.
     * @param restaurantId - id of the restaurant to vote for.
     * @return resulting restaurant entity with updated vote counter.
     */
    public Restaurant vote(Long restaurantId);

    /**
     * Resets counters of all the restaurants to clear the results of voting.
     * It's called once per work day.
     * @see lunch.voting.core.scheduled.tasks.VotingResetTask
     */
    public void resetCounters();
}

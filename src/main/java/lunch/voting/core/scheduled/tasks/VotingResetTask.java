package lunch.voting.core.scheduled.tasks;

import lunch.voting.core.services.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Scheduled task to clear vote results every work day at midnight.
 * It acquires pessimistic write lock on all restaurants.
 */
@Component
public class VotingResetTask {

    @Autowired
    private RestaurantService restaurantService;

    @Scheduled(cron="0 0 0 * * MON-FRI")
    public void resetVotingResults() {
        restaurantService.resetCounters();
    }
}

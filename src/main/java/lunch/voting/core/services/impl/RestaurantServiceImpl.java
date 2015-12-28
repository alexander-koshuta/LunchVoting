package lunch.voting.core.services.impl;

import lunch.voting.core.models.entities.Account;
import lunch.voting.core.models.entities.Restaurant;
import lunch.voting.core.repositories.AccountRepo;
import lunch.voting.core.repositories.RestaurantRepo;
import lunch.voting.core.security.AccountUserDetails;
import lunch.voting.core.services.RestaurantService;
import lunch.voting.core.services.exceptions.AlreadyVotedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 *
 */
@Service
@Transactional
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepo restaurantRepo;
    @Autowired
    private AccountRepo accountRepo;

    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepo.findAll();
    }

    @Override
    public Restaurant createRestaurant(Restaurant data) {
        return restaurantRepo.create(data);
    }

    @Override
    public Restaurant updateRestaurant(Long id, Restaurant data) {
        return restaurantRepo.update(id, data);
    }

    @Override
    public Restaurant vote(Long restaurantId) {
        Restaurant result = null;
        String userName = AccountUserDetails.getPrincipal();
        Account account = accountRepo.findAccountByName(userName);
        if (account.getLastVoted() == null) {
            // can vote
            result = vote(account, restaurantId);
        } else {
            // check if he is not voting for the same restaurant
            if (account.getVotedRestaurant().equals(restaurantId)) {
                throw new AlreadyVotedException("Voting for the same restaurant again.");
            }
            // check the time
            Date voted = account.getLastVoted();
            LocalDate date = LocalDate.now();
            LocalDateTime graceHour = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 11, 0);
            LocalDateTime votedHour = LocalDateTime.ofInstant(voted.toInstant(), ZoneId.systemDefault());
            if (votedHour.isBefore(graceHour)) {
                // can vote
                // firstly decreasing counter for the previously voted restaurant
                undoVote(account);
                result = vote(account, restaurantId);
            } else {
                throw new AlreadyVotedException();
            }
        }
        return result;
    }

    @Override
    public void resetCounters() {
        restaurantRepo.resetCounters();
    }

    private void undoVote(Account account) {
        Restaurant restaurant = restaurantRepo.find(account.getVotedRestaurant());
        if (restaurant.getCounter() > 0) {
            restaurant.setCounter(restaurant.getCounter() - 1);
        }
    }

    private Restaurant vote(Account account, Long restaurantId) {
        Restaurant restaurant = restaurantRepo.find(restaurantId);
        restaurant.setCounter(restaurant.getCounter() + 1);
        account.setLastVoted(new Date());
        account.setVotedRestaurant(restaurant.getId());
        return restaurant;
    }
}

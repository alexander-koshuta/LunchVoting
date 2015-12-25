package lunch.voting.core.services.impl;

import lunch.voting.core.models.entities.Account;
import lunch.voting.core.models.entities.Restaurant;
import lunch.voting.core.repositories.AccountRepo;
import lunch.voting.core.repositories.RestaurantRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import lunch.voting.core.services.RestaurantService;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
        String userName = getPrincipal();
        Account account = accountRepo.findAccountByName(userName);
        if (account.getLastVoted() == null) {
            // can vote

            // TODO: concurrent update fix
            account.setLastVoted(new Date());
            Restaurant restaurant = restaurantRepo.find(restaurantId);
            restaurant.setCounter(restaurant.getCounter() + 1);
        } else {

        }
        return null;
    }

    private String getPrincipal(){
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }
}

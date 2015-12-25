package lunch.voting.rest.mvc;

import lunch.voting.core.models.entities.Account;
import lunch.voting.core.models.entities.Restaurant;
import lunch.voting.core.services.AccountService;
import lunch.voting.core.services.RestaurantService;
import lunch.voting.core.services.exceptions.AccountExistsException;
import lunch.voting.rest.exceptions.ConflictException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    private RestaurantService restaurantService;
    private AccountService accountService;

    @Autowired
    public AdminController(RestaurantService restaurantService, AccountService accountService) {
        this.restaurantService = restaurantService;
        this.accountService = accountService;
    }

    @RequestMapping(value = "/testConnection", method = RequestMethod.GET)
    public ResponseEntity<String> testConnection() {
        return new ResponseEntity<String>("connection is ok", HttpStatus.OK);
    }

    @RequestMapping(value = "/accounts", method = RequestMethod.GET)
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = accountService.findAllAccounts();
        return new ResponseEntity<List<Account>>(accounts, HttpStatus.OK);
    }

    @RequestMapping(value = "/accounts", method = RequestMethod.POST)
    public ResponseEntity<Account> createAccount(@RequestBody Account sentAccount) {
        try {
            Account createdAccount = accountService.createAccount(sentAccount);
            return new ResponseEntity<Account>(createdAccount, HttpStatus.CREATED);
        } catch(AccountExistsException exception) {
            throw new ConflictException(exception);
        }
    }

    @RequestMapping(value = "/restaurants", method = RequestMethod.POST)
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody Restaurant sentRestaurant) {
        Restaurant createdRestaurant = restaurantService.createRestaurant(sentRestaurant);
        return new ResponseEntity<Restaurant>(createdRestaurant, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/restaurants/{restaurantId}", method = RequestMethod.PUT)
    public ResponseEntity<Restaurant> updateRestaurant(@PathVariable Long restaurantId, @RequestBody Restaurant restaurant) {
        Restaurant updatedRestaurant = restaurantService.updateRestaurant(restaurantId, restaurant);
        if (updatedRestaurant != null) {
            return new ResponseEntity<Restaurant>(updatedRestaurant, HttpStatus.OK);
        } else {
            return new ResponseEntity<Restaurant>(HttpStatus.NOT_FOUND);
        }
    }
}

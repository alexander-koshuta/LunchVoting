package lunch.voting.rest.mvc;

import lunch.voting.core.models.entities.Account;
import lunch.voting.core.models.entities.Restaurant;
import lunch.voting.core.services.AccountService;
import lunch.voting.core.services.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@RestController
@RequestMapping("/test")
@PreAuthorize("permitAll")
public class TestController {

    private RestaurantService restaurantService;
    private AccountService accountService;

    @Autowired
    public TestController(RestaurantService service, AccountService accountService) {
        this.restaurantService = service;
        this.accountService = accountService;
    }

    @RequestMapping(value = "/restaurant", method = RequestMethod.GET)
    public ResponseEntity<List<Restaurant>> listAllRestaurants() {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        if(restaurants.isEmpty()){
            return new ResponseEntity<List<Restaurant>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Restaurant>>(restaurants, HttpStatus.OK);
    }

    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public ResponseEntity<List<Account>> init() {

        List<Account> accounts = new ArrayList<>();

        Account admin = new Account();
        admin.setName("admin");
        admin.setPassword("admin");
        admin.setRole("ROLE_ADMIN");

        Account createdAccount = accountService.createAccount(admin);

        accounts.add(createdAccount);

        // Regular user
        Account user = new Account();
        user.setName("user");
        user.setPassword("user");
        user.setRole("ROLE_USER");

        createdAccount = accountService.createAccount(user);

        accounts.add(createdAccount);

        return new ResponseEntity<List<Account>>(accounts, HttpStatus.OK);
    }
}

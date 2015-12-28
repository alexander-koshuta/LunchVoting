package lunch.voting.rest.mvc;

import lunch.voting.core.models.entities.Account;
import lunch.voting.core.models.entities.Restaurant;
import lunch.voting.core.models.entities.Role;
import lunch.voting.core.services.AccountService;
import lunch.voting.core.services.RestaurantService;
import lunch.voting.core.services.RoleService;
import lunch.voting.core.services.exceptions.AccountExistsException;
import lunch.voting.core.services.exceptions.RoleDoesNotExistException;
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
 * This controller represents admin console for managing the application.
 * Access to it requires admin role authority.
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    private RestaurantService restaurantService;
    private AccountService accountService;
    private RoleService roleService;

    @Autowired
    public AdminController(RestaurantService restaurantService, AccountService accountService, RoleService roleService) {
        this.restaurantService = restaurantService;
        this.accountService = accountService;
        this.roleService = roleService;
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
        } catch(AccountExistsException | RoleDoesNotExistException exception) {
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

    /**
     * This is a convenient method to list all roles for admin UI, probably for a dropdown box which can be used
     * later for creating user accounts.
     * @return the list of all existing roles.
     */
    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = roleService.getAllRoles();
        return new ResponseEntity<List<Role>>(roles, HttpStatus.OK);
    }
}

package lunch.voting.rest.mvc;

import lunch.voting.core.models.RoleTypes;
import lunch.voting.core.models.entities.Account;
import lunch.voting.core.models.entities.Restaurant;
import lunch.voting.core.models.entities.Role;
import lunch.voting.core.services.AccountService;
import lunch.voting.core.services.RestaurantService;
import lunch.voting.core.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * This controller is for test purposes only. It should not be included in a production build.
 * Performs initialization of roles and 2 user accounts for an admin and a regular user.
 * It also can list all the restaurants to check the results of any modifications including voting.
 */
@RestController
@RequestMapping("/test")
@PreAuthorize("permitAll")
public class TestController {

    private RestaurantService restaurantService;
    private AccountService accountService;
    private RoleService roleService;

    @Autowired
    public TestController(RestaurantService service, AccountService accountService, RoleService roleService) {
        this.restaurantService = service;
        this.accountService = accountService;
        this.roleService = roleService;
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

        Role roleAdmin = new Role(RoleTypes.ROLE_ADMIN);
        roleAdmin = roleService.createRole(roleAdmin);

        Role roleUser = new Role(RoleTypes.ROLE_USER);
        roleUser = roleService.createRole(roleUser);

        List<Account> accounts = new ArrayList<>();

        Account admin = new Account();
        admin.setName("admin");
        admin.setPassword("admin");
        Set<Role> roles = admin.getRoles();
        roles.add(roleAdmin);
        roles.add(roleUser);

        Account createdAccount = accountService.createAccount(admin);

        accounts.add(createdAccount);

        // Regular user
        Account user = new Account();
        user.setName("user");
        user.setPassword("user");
        roles = user.getRoles();
        roles.add(roleUser);

        createdAccount = accountService.createAccount(user);

        accounts.add(createdAccount);

        return new ResponseEntity<List<Account>>(accounts, HttpStatus.OK);
    }
}

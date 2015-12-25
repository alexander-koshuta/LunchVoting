package lunch.voting.rest.mvc;

import lunch.voting.core.models.entities.Restaurant;
import lunch.voting.core.services.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 */
@RestController
@RequestMapping("/voting")
public class VotingController {

    private RestaurantService restaurantService;

    @Autowired
    public VotingController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @RequestMapping(value = "/restaurants", method = RequestMethod.GET)
    //@PreAuthorize("permitAll")
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        List<Restaurant> list = restaurantService.getAllRestaurants();
        return new ResponseEntity<List<Restaurant>>(list, HttpStatus.OK);
    }

    @RequestMapping(value = "/{restaurantId}", method = RequestMethod.GET)
    //@PreAuthorize("permitAll")
    public ResponseEntity<Restaurant> vote(@PathVariable Long restaurantId) {
        Restaurant restaurant = restaurantService.vote(restaurantId);
        return new ResponseEntity<Restaurant>(restaurant, HttpStatus.OK);
    }

    @RequestMapping(value = "/check", method = RequestMethod.GET)
    //@PreAuthorize("permitAll")
    public ResponseEntity<Boolean> isVoted() {
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }
}

package lunch.voting.rest.mvc;

import lunch.voting.core.models.entities.Restaurant;
import lunch.voting.core.services.RestaurantService;
import lunch.voting.core.services.exceptions.AlreadyVotedException;
import lunch.voting.rest.exceptions.ApplicationIsBusyException;
import lunch.voting.rest.exceptions.ConflictException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.OptimisticLockException;
import java.util.List;

/**
 *
 */
@RestController
@RequestMapping("/voting")
public class VotingController {

    private static final int VOTE_ATTEMPTS = 5;

    private RestaurantService restaurantService;

    @Autowired
    public VotingController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @RequestMapping(value = "/restaurants", method = RequestMethod.GET)
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        List<Restaurant> list = restaurantService.getAllRestaurants();
        return new ResponseEntity<List<Restaurant>>(list, HttpStatus.OK);
    }

    @RequestMapping(value = "/{restaurantId}", method = RequestMethod.GET)
    public ResponseEntity<Restaurant> vote(@PathVariable Long restaurantId) {
        Restaurant restaurant = null;
        boolean voted = false;
        for(int i = 0; i < VOTE_ATTEMPTS; ++i) {
            try {
                restaurant = restaurantService.vote(restaurantId);
                voted = true;
                break;
            } catch (OptimisticLockException ex) {
                // Restaurant entity is being updated right now, but we can try again.
            } catch (AlreadyVotedException ex) {
                throw new ConflictException(ex.getMessage());
            }
        }
        if (!voted) {
            throw new ApplicationIsBusyException();
        }
        return new ResponseEntity<Restaurant>(restaurant, HttpStatus.OK);
    }
}

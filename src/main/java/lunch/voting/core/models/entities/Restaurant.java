package lunch.voting.core.models.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *
 */
@Entity
public class Restaurant {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String address;
    private String lunchMenu;
    private int counter;

    public Restaurant() {
    }

    public Restaurant(String title, String address) {
        this.title = title;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLunchMenu() {
        return lunchMenu;
    }

    public void setLunchMenu(String lunchMenu) {
        this.lunchMenu = lunchMenu;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}

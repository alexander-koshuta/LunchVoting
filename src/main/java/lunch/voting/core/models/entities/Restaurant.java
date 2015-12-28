package lunch.voting.core.models.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;

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
    /**
     * Storing the menu as json. It's more effective than creating a related table and more flexible, easy to
     * communicate with the web client. No-sql approach to structuring where we can redefine the menu any time.
     */
    private String lunchMenu;
    /**
     * Counter for votes.
     */
    private int counter;

    /**
     * Optimistic lock enabled.
     */
    @Version
    private Integer version;

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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Restaurant that = (Restaurant) o;

        if (!title.equals(that.title)) return false;
        return address.equals(that.address);

    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + address.hashCode();
        return result;
    }

    /*
        Useful for logging.
     */
    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", address='" + address + '\'' +
                ", lunchMenu='" + lunchMenu + '\'' +
                ", counter=" + counter +
                ", version=" + version +
                '}';
    }
}

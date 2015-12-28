package lunch.voting.core.models.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 *
 */
@Entity
public class Account {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ACCOUNT_ROLE",
            joinColumns = { @JoinColumn(name = "ACCOUNT_ID") },
            inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") })
    private Set<Role> roles = new HashSet<>();

    /**
     * Serves for vote time check.
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastVoted;
    /**
     * Stores id for the voted restaurant in case the user will vote for another restaurant.
     * In such scenario her previous vote should be subtracted from the previously voted restaurant.
     */
    private Long votedRestaurant;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Date getLastVoted() {
        return lastVoted;
    }

    public void setLastVoted(Date lastVoted) {
        this.lastVoted = lastVoted;
    }

    public Long getVotedRestaurant() {
        return votedRestaurant;
    }

    public void setVotedRestaurant(Long votedRestaurant) {
        this.votedRestaurant = votedRestaurant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        return name.equals(account.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                ", lastVoted=" + lastVoted +
                '}';
    }
}

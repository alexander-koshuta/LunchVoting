package lunch.voting.core.models.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

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
    private String role;
    private Date lastVoted;

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getLastVoted() {
        return lastVoted;
    }

    public void setLastVoted(Date lastVoted) {
        this.lastVoted = lastVoted;
    }
}

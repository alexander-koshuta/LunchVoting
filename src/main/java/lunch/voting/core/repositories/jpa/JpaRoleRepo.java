package lunch.voting.core.repositories.jpa;

import lunch.voting.core.models.RoleTypes;
import lunch.voting.core.models.entities.Role;
import lunch.voting.core.repositories.RoleRepo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 *
 */
@Repository
public class JpaRoleRepo implements RoleRepo {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Role find(Long id) {
        return em.find(Role.class, id);
    }

    @Override
    public Role findByType(RoleTypes type) {
        Query query = em.createQuery("SELECT r from Role r where r.type=?1");
        query.setParameter(1, type);
        List<Role> roles = query.getResultList();
        if(roles.size() == 0) {
            return null;
        } else {
            return roles.get(0);
        }
    }

    @Override
    public List<Role> findAll() {
        Query query = em.createQuery("SELECT r from Role r");
        return query.getResultList();
    }

    @Override
    public Role create(Role role) {
        em.persist(role);
        return role;
    }
}

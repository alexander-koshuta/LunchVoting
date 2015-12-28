package lunch.voting.core.repositories;

import lunch.voting.core.models.RoleTypes;
import lunch.voting.core.models.entities.Role;

import java.util.List;

/**
 *
 */
public interface RoleRepo {
    public Role find(Long id);
    public Role findByType(RoleTypes type);
    public List<Role> findAll();
    public Role create(Role role);
}

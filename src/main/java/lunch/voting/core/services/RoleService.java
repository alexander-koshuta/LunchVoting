package lunch.voting.core.services;

import lunch.voting.core.models.RoleTypes;
import lunch.voting.core.models.entities.Role;

import java.util.List;

/**
 *
 */
public interface RoleService {
    public List<Role> getAllRoles();
    public Role createRole(Role role);
    public Role findByType(RoleTypes type);
}

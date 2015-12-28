package lunch.voting.core.services.impl;

import lunch.voting.core.models.RoleTypes;
import lunch.voting.core.models.entities.Role;
import lunch.voting.core.repositories.RoleRepo;
import lunch.voting.core.services.RoleService;
import lunch.voting.core.services.exceptions.RoleExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepo roleRepo;

    @Override
    public List<Role> getAllRoles() {
        return roleRepo.findAll();
    }

    @Override
    public Role createRole(Role role) {
        Role entity = roleRepo.findByType(role.getType());
        if (entity != null) {
            throw new RoleExistsException();
        }
        return roleRepo.create(role);
    }

    @Override
    public Role findByType(RoleTypes type) {
        return roleRepo.findByType(type);
    }
}

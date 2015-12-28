package lunch.voting.core.services.impl;

import lunch.voting.core.models.entities.Account;
import lunch.voting.core.models.entities.Role;
import lunch.voting.core.repositories.AccountRepo;
import lunch.voting.core.repositories.RoleRepo;
import lunch.voting.core.services.AccountService;
import lunch.voting.core.services.exceptions.AccountExistsException;
import lunch.voting.core.services.exceptions.RoleDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 */
@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Override
    public Account findAccount(Long id) {
        return accountRepo.findAccount(id);
    }

    @Override
    public Account createAccount(Account data) {
        Account account = accountRepo.findAccountByName(data.getName());
        if(account != null)
        {
            throw new AccountExistsException();
        }
        Set<Role> roles = checkRoles(data.getRoles());
        data.setRoles(roles);
        return accountRepo.createAccount(data);
    }

    private Set<Role> checkRoles(Set<Role> receivedRoles) {
        if (receivedRoles == null) {
            return null;
        }
        Set<Role> roles = new HashSet<>();
        for (Role receivedRole : receivedRoles) {
            Role role = roleRepo.find(receivedRole.getId());
            if (role == null) {
                throw new RoleDoesNotExistException();
            }
            roles.add(role);
        }
        return roles;
    }

    @Override
    public List<Account> findAllAccounts() {
        return accountRepo.findAllAccounts();
    }

    @Override
    public Account findByAccountName(String name) {
        return accountRepo.findAccountByName(name);
    }

}

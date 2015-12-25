package lunch.voting.core.security;

import lunch.voting.core.models.entities.Account;
import lunch.voting.core.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 */
@Component
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private AccountService service;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        List<Account> accounts = service.findAllAccounts();
        System.out.println(accounts);
        Account account = service.findByAccountName(name);
        if(account == null) {
            throw new UsernameNotFoundException("no user found with " + name);
        }
        return new AccountUserDetails(account);
    }
}

package lunch.voting.core.services.impl;

import lunch.voting.core.models.entities.Account;
import lunch.voting.core.repositories.AccountRepo;
import lunch.voting.core.services.AccountService;
import lunch.voting.core.services.exceptions.AccountExistsException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 */
@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepo accountRepo;

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
        return accountRepo.createAccount(data);
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

package lunch.voting.core.services;


import lunch.voting.core.models.entities.Account;

import java.util.List;

/**
 *
 */
public interface AccountService {
    public Account findAccount(Long id);
    public Account createAccount(Account data);
    public List<Account> findAllAccounts();
    public Account findByAccountName(String name);
}
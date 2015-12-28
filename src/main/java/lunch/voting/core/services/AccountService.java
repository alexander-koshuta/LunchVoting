package lunch.voting.core.services;


import lunch.voting.core.models.entities.Account;

import java.util.List;

/**
 * Performs all account related operations.
 */
public interface AccountService {
    public Account findAccount(Long id);
    public Account createAccount(Account data);
    public List<Account> findAllAccounts();

    /**
     * Performs search of the user with the provided name. Names of the users are unique among all accounts so this
     * method can be used to find a unique user as well as {@link #findAccount(Long) findAccount(Long id)} method.
     * @param name - name of the user.
     * @return found account or null.
     */
    public Account findByAccountName(String name);
}

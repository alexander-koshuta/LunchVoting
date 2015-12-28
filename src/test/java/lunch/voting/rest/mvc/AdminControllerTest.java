package lunch.voting.rest.mvc;

import lunch.voting.core.models.entities.Account;
import lunch.voting.core.services.AccountService;
import lunch.voting.core.services.exceptions.AccountExistsException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 */
public class AdminControllerTest {

    @InjectMocks
    private AdminController controller;

    @Mock
    private AccountService service;

    private MockMvc mockMvc;

    private ArgumentCaptor<Account> accountCaptor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        accountCaptor = ArgumentCaptor.forClass(Account.class);
    }

    @Test
    public void createAccountNonExistingUsername() throws Exception {
        Account createdAccount = new Account();
        createdAccount.setId(1L);
        createdAccount.setPassword("test");
        createdAccount.setName("test");

        when(service.createAccount(any(Account.class))).thenReturn(createdAccount);

        mockMvc.perform(post("/admin/accounts")
                .content("{\"name\":\"test\",\"password\":\"test\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(createdAccount.getName())))
                .andExpect(status().isCreated());

        verify(service).createAccount(accountCaptor.capture());

        String password = accountCaptor.getValue().getPassword();
        assertEquals("test", password);
    }

    @Test
    public void createAccountExistingUsername() throws Exception {

        when(service.createAccount(any(Account.class))).thenThrow(new AccountExistsException());

        mockMvc.perform(post("/admin/accounts")
                .content("{\"name\":\"test\",\"password\":\"test\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    public void findAllAccounts() throws Exception {
        List<Account> accounts = new ArrayList<Account>();

        Account accountA = new Account();
        accountA.setId(1L);
        accountA.setPassword("accountA");
        accountA.setName("accountA");
        accounts.add(accountA);

        Account accountB = new Account();
        accountB.setId(1L);
        accountB.setPassword("accountB");
        accountB.setName("accountB");
        accounts.add(accountB);

        when(service.findAllAccounts()).thenReturn(accounts);

        mockMvc.perform(get("/admin/accounts"))
                .andExpect(jsonPath("$.[*].name",
                        hasItems(endsWith("accountA"), endsWith("accountB"))))
                .andExpect(status().isOk());
    }
}

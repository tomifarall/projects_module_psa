package com.pca.projects.projects_module.cucumber;

import com.pca.projects.projects_module.ProjectsModuleApplication;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

@ContextConfiguration(classes = ProjectsModuleApplication.class)
@WebAppConfiguration
public class ProjectIntegrationServiceTest {

/*    @Autowired
    AccountService accountService;

    @Autowired
    TransactionService transactionService;

    Account createAccount(Double balance) {
        return accountService.createAccount(new Account(balance));
    }

    Transaction withdraw(Account account, Double sum) {
        Transaction withdrawal = new Transaction(account.getCbu(), sum, TransactionType.WITHDRAWAL);
        return transactionService.createTransaction(withdrawal);
    }

    Transaction deposit(Account account, Double sum) {
        Transaction deposit = new Transaction(account.getCbu(), sum, TransactionType.DEPOSIT);
        return transactionService.createTransaction(deposit);
    }*/

}

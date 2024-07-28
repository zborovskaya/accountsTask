package by.zborovskaya.AccountManagement.services;

import by.zborovskaya.AccountManagement.models.Account;
import by.zborovskaya.AccountManagement.models.Person;
import by.zborovskaya.AccountManagement.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> findAll(boolean sortByNumber) {
        if (sortByNumber)
            return accountRepository.findAll(Sort.by("number"));
        else
            return accountRepository.findAll();
    }

    public Account findOne(int id) {
        Optional<Account> foundBook = accountRepository.findById(id);
        return foundBook.orElse(null);
    }

    @Transactional
    public void update(int number, Account updatedAccount) {
        Account accountToBeUpdated = accountRepository.findById(number).get();

        updatedAccount.setNumber(number);
        updatedAccount.setPerson(accountToBeUpdated.getPerson()); // чтобы не терялась связь при обновлении

        accountRepository.save(updatedAccount);
    }

    public Person getAccountOwner(int id) {
        return accountRepository.findById(id).map(Account::getPerson).orElse(null);
    }

    @Transactional
    public void changeStatus(int id) {
        accountRepository.findById(id).ifPresent(
                account -> {
                    if (account.getStatus().equals("ACTIVE")) {
                        account.setStatus("BLOCKED");
                    } else {
                        account.setStatus("ACTIVE");
                    }
                });
    }

    @Transactional
    public void withdrawMoney(int accountNumber, int withdrawSum) {
        accountRepository.findById(accountNumber).ifPresent(
                account -> {
                    int value = account.getValue();
                    if (value > withdrawSum) {
                        account.setValue(value - withdrawSum);
                    }
                });
    }

    @Transactional
    public void topUpAccount(int accountNumber, int sum) {
        accountRepository.findById(accountNumber).ifPresent(
                account -> {
                    int value = account.getValue();
                    account.setValue(value + sum);
                });
    }

    public List<Account> findByPerson(Person person) {
        return accountRepository.findByPerson(person);
    }
}

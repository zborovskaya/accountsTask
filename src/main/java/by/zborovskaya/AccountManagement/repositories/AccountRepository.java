package by.zborovskaya.AccountManagement.repositories;

import by.zborovskaya.AccountManagement.models.Account;
import by.zborovskaya.AccountManagement.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    List<Account> findByPerson(Person person);
}

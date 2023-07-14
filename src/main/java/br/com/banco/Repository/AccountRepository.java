package br.com.banco.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import br.com.banco.Entity.Account;

@Repository
@Component
public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByAccountId(Long accountId);
}

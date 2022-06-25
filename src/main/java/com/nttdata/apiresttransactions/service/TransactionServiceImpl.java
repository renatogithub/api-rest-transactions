/**
 * Implementation Interface Service Transaction
 *
 * @author Renato Ponce
 * @version 1.0
 * @since 2022-06-24
 */

package com.nttdata.apiresttransactions.service;

import com.nttdata.apiresttransactions.dto.AccountDTO;
import com.nttdata.apiresttransactions.model.Transaction;
import com.nttdata.apiresttransactions.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
public class TransactionServiceImpl implements TransactionService {

    private static final Logger log = LoggerFactory.getLogger(TransactionServiceImpl.class);

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private WebClient webClient;

    @Override
    public Mono<Transaction> getById(String id) {
        return repository.findById(id);
    }

    @Override
    public Mono<Transaction> register(Transaction transaction) {
        return validateSaveTransaction(transaction);
    }

    @Override
    public Mono<Transaction> getByAccountId(String idAccount) {
        return repository.findByAccount_Id(idAccount);
    }

    private Mono<AccountDTO> findByAccount(String id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        return webClient.get().uri("/{id}", params).accept(APPLICATION_JSON)
                .exchangeToMono(response -> response.bodyToMono(AccountDTO.class));
    }

    private Mono<AccountDTO> updateAccount(AccountDTO account, String id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        return webClient.put()
                .uri("/{id}", params)
                .body(Mono.just(account), AccountDTO.class)
                .retrieve()
                .bodyToMono(AccountDTO.class);
    }

    private Mono<Transaction> validateSaveTransaction(Transaction transaction) {
        Mono<AccountDTO> monoAccount = findByAccount(transaction.getAccount().getId());

        return monoAccount
                .flatMap(acc -> {
                    transaction.setAccount(acc);
                    if (transaction.getTypeOperation().getCode().equalsIgnoreCase("W")) {
                        log.info("The type of operation is withdrawal");
                        if (acc.getAmount() > transaction.getAmount()) {
                            acc.setAmount(acc.getAmount() - transaction.getAmount());
                            return updateAccount(acc, transaction.getAccount().getId()).flatMap(ac -> {
                                return repository.save(transaction);
                            });
                        } else {
                            log.info("The amount is insufficient");
                        }

                    } else if (transaction.getTypeOperation().getCode().equalsIgnoreCase("D")) {
                        log.info("The type of operation is deposit");
                        acc.setAmount(acc.getAmount() + transaction.getAmount());
                        return updateAccount(acc, transaction.getAccount().getId()).flatMap(ac -> {
                            return repository.save(transaction);
                        });
                    } else if (transaction.getTypeOperation().getCode().equalsIgnoreCase("P")) {
                        log.info("The type of operation is payment");
                        acc.setAmount(acc.getAmount() + transaction.getAmount());
                    }
                    return Mono.just(transaction);
                });

    }
}

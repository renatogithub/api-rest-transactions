/**
 * Interface Service Transaction
 *
 * @author Renato Ponce
 * @version 1.0
 * @since 2022-06-24
 */

package com.nttdata.apiresttransactions.service;

import com.nttdata.apiresttransactions.model.Transaction;
import reactor.core.publisher.Mono;

public interface TransactionService {

    Mono<Transaction> getById(String id);
    Mono<Transaction> register(Transaction transaction);

    Mono<Transaction> getByAccountId(String idAccount);
}

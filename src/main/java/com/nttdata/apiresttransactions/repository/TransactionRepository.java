/**
 * Repository that stores Transaction information
 *
 * @author Renato Ponce
 * @version 1.0
 * @since 2022-06-24
 */

package com.nttdata.apiresttransactions.repository;

import com.nttdata.apiresttransactions.model.Transaction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface TransactionRepository extends ReactiveMongoRepository<Transaction, String> {

    Mono<Transaction> findByAccount_Id(String idAccount);
}

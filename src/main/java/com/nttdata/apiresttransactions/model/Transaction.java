/**
 * Bean Stores Transaction Information
 *
 * @author Renato Ponce
 * @version 1.0
 * @since 2022-06-24
 */

package com.nttdata.apiresttransactions.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nttdata.apiresttransactions.dto.AccountDTO;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@Document(collection = "transactions")
public class Transaction {

    @Id
    private String id;

    private TypeOperation typeOperation;

    private AccountDTO account;

    private double amount;

    private String details;

    @Field(name = "dateOperation")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateOperation = LocalDateTime.now();
}

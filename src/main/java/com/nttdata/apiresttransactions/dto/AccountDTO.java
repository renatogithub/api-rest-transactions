/**
 * Bean Stores Account Information
 *
 * @author Renato Ponce
 * @version 1.0
 * @since 2022-06-24
 */

package com.nttdata.apiresttransactions.dto;

import lombok.Data;

import java.util.List;

@Data
public class AccountDTO {
    private String id;
    private AccountTypeDTO accountType;
    private String accountNumber;
    private String currency;
    private double amount;
    private CustomerDTO customer;
    private String state;
    private int maxLimitMovementPerMonth;
    private List<HeadLineDTO> headlines;
    private List<AuthorizedSignerDTO> authorizedSigners;
}

/**
 * Bean Stores AccountType Information
 *
 * @author Renato Ponce
 * @version 1.0
 * @since 2022-06-24
 */

package com.nttdata.apiresttransactions.dto;

import lombok.Data;

@Data
public class AccountTypeDTO {
    private String code;
    private String name;
    private String description;
}

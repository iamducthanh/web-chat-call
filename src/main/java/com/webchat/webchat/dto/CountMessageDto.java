package com.webchat.webchat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author GMO_ThanhND
 * @version 1.0
 * @since 11/16/2021
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountMessageDto {
    private String month;
    private Integer count;
}

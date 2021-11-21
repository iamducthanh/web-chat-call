package com.webchat.webchat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author GMO_ThanhND
 * @version 1.0
 * @since 11/21/2021
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThongKeNguoiDungDto {
    private Integer quantity;
    private Integer online;
    private Integer offline;
}

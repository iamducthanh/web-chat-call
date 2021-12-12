package com.webchat.webchat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description:
 *
 * @author: GMO_ThanhND
 * @version: 1.0
 * @since 12/12/2021 10:31 AM
 * Project_name: GMO_QuanLyTaiSan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDto {
 String username;
 String password;
}

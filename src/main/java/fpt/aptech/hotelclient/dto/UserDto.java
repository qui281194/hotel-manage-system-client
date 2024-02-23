/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fpt.aptech.hotelclient.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author PC
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Integer id;
    
    @NotBlank(message = "Username must be fill!!!")
    private String username;
    
    @NotBlank(message = "Email must be fill!!!")
    @Email(message = "Email must be right format!!!")
    private String email;
    
    @NotBlank(message = "Password must be fill!!!")
    private String password;
    
    @NotBlank(message = "Address must be fill!!!")
    private String address;
    
    @NotBlank(message = "Phone number must be fill!!!")
    private String phone;
    private Boolean active;
    private Integer role_id;
    private RoleDto roleInfo;
}

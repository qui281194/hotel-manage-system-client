/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fpt.aptech.hotelclient.models;

import java.io.Serializable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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
@Entity
@Table(name = "tbl_users")

public class Users implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @NotEmpty(message = "Name is required")
    @Column(name = "username")
    private String username;
    
    @NotEmpty(message = "Email is required")
    @Column(name = "email")
    private String email;
    @NotEmpty(message = "Passowrd is required")
    @Column(name = "password")
    private String password;
//    @NotEmpty(message = "Name is required")
    @Column(name = "address")
    private String address;
    @NotEmpty(message = "Phone is required")
    @Column(name = "phone")
    private String phone;
    @Column(name = "active")
    private Boolean active;
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    @ManyToOne
    private Role roleId;

    
}

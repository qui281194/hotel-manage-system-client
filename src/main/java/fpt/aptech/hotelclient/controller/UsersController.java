/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package fpt.aptech.hotelclient.controller;

import fpt.aptech.hotelclient.dto.LoginDto;
import fpt.aptech.hotelclient.dto.UserDto;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author PC
 */
@Controller
public class UsersController {

    RestTemplate rest = new RestTemplate();
    private final String apiUrl = "http://localhost:9999/api/users/";

    @GetMapping("/login")
    public String showLogin(Model model) {
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("loginDto") LoginDto loginDto, Model model) {
        try {
            // Gọi API login
            ResponseEntity<UserDto> response = rest.postForEntity(apiUrl + "login", loginDto, UserDto.class);

//            if (response.getStatusCode() == HttpStatus.OK) {
            UserDto loggedInUser = response.getBody();
            
            if (loggedInUser.getActive() != true) {
                model.addAttribute("active", "Your account has been locked");
                return "login";

            }
            if (loggedInUser.getRole_id() == 1 || loggedInUser.getRole_id() == 2) {
                return "redirect:http://localhost:8888/client/admin/staffcontroller/all";
            } 
                return "redirect:http://localhost:8888/client/customer/homecontroller/all?userId=" + loggedInUser.getId();
            
//            }
        } catch (Exception ex) {
            // Xử lý lỗi chung
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }

    @RequestMapping("/users/register-customer")
    public String registerUser(Model model) {
        model.addAttribute("newUser", new UserDto());
        return "users/register";
    }

    @PostMapping("/users/register-customer")
    public String registerNewCustomer(@ModelAttribute UserDto newUser, Model model) {

        try {
            ResponseEntity<UserDto> responseEntity = rest.postForEntity(
                    apiUrl + "register",
                    newUser,
                    UserDto.class
            );
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                // Đăng ký thành công, thực hiện các tác vụ khác nếu cần thiết
                UserDto registeredUser = responseEntity.getBody();
                model.addAttribute("user", registeredUser);
//                model.addAttribute("successregister", "You have successfully registered. Please login here!");
                return "/login"; // Trả về view cho đăng ký thành công
            }
        } catch (Exception ex) {
            model.addAttribute("error", "Email already exists !");
//            model.addAttribute("active");
        }
        model.addAttribute("newUser", new UserDto());
        return "users/register";
    }

}

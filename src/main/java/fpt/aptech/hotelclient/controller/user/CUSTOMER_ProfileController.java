/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package fpt.aptech.hotelclient.controller.user;

import fpt.aptech.hotelclient.dto.BookingDto;
import fpt.aptech.hotelclient.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author TuanNguyen
 */
@Controller
@RequestMapping("/client/customer/profilecontroller")
public class CUSTOMER_ProfileController {

    String booking_api_url = "http://localhost:9999/api/bookingcontroller";
    String user_api_url = "http://localhost:9999/api/users";

    RestTemplate _restTemplate = new RestTemplate();

    @RequestMapping("/all")
    public String page(Model model, @RequestParam("userId") int userId) {
        UserDto userProfile = _restTemplate.getForObject(user_api_url + "/findbyid/" + userId, UserDto.class);
        List<BookingDto> allBookingByCustomer = _restTemplate.getForObject(booking_api_url + "/allbookingbycustomer/" + userId, List.class);
        model.addAttribute("userId", userId);
        model.addAttribute("userProfile", userProfile);
        model.addAttribute("bookingList", allBookingByCustomer);
        return "users/profile";
    }

    @PostMapping("/{userId}/changepassword")
    public String changePassword(@PathVariable("userId") int userId,
            @RequestParam("currentPassword") String currentPassword,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmPassword") String confirmPassword,
            Model model
    ) {

        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("userId", userId);
            model.addAttribute("passwordChangeError", "New password and confirm password do not match.");
            return "users/profile";
        }

        ResponseEntity<String> responseEntity = _restTemplate.postForEntity(user_api_url + "/" + userId + "/change-password?currentPassword=" + currentPassword + "&newPassword=" + newPassword, null, String.class);

        if (responseEntity.getBody().equals("Password changed successfully.")) {
            UserDto userProfile = _restTemplate.getForObject(user_api_url + "/findbyid/" + userId, UserDto.class);
            model.addAttribute("userProfile", userProfile);
            model.addAttribute("userId", userId);
            model.addAttribute("successPassword", "Password changed successfully.");
            return "users/profile";
        }else{
            UserDto userProfile = _restTemplate.getForObject(user_api_url + "/findbyid/" + userId, UserDto.class);
        model.addAttribute("userProfile", userProfile);

        model.addAttribute("userId", userId);
        model.addAttribute("error", "Failed to change password. Please check your current password.");
        return "users/profile";
        }
//        (responseEntity.getStatusCode().equals(HttpStatus.BAD_REQUEST))
    }

}

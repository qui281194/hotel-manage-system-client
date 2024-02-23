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
            model.addAttribute("passwordChangeError", "New password and confirm password do not match.");
            return "redirect:/client/customer/profilecontroller/all?userId=" + userId;
        }

        ResponseEntity<String> responseEntity = _restTemplate.postForEntity(user_api_url + "/" + userId + "/change-password?currentPassword=" + currentPassword + "&newPassword=" + newPassword, null, String.class);

        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            model.addAttribute("success", "Password changed successfully.");
            return "redirect:/client/customer/profilecontroller/all?userId=" + userId;
        }
        model.addAttribute("error", responseEntity.getBody());
        return "redirect:/client/customer/profilecontroller/all?userId=" + userId;
    }

//    @PostMapping("/{userId}/changepassword")
//    public String changePassword(@RequestParam("currentPassword") String currentPassword,
//            @RequestParam("newPassword") String newPassword, @RequestParam("confirmPassword") String confirmPassword, @RequestParam("userId") int userId,
//            Model model) {
//        if (!newPassword.equals(confirmPassword)) {
//            model.addAttribute("passwordChangeError", "New password and confirm password do not match.");
//            return "redirect:/client/customer/profilecontroller/all?userId=" + userId;
//        }
//        // Gọi API để thay đổi mật khẩu của người dùng
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        Map<String, String> requestParams = new HashMap<>();
//        requestParams.put("currentPassword", currentPassword);
//        requestParams.put("newPassword", newPassword);
//        requestParams.put("confirmPassword", confirmPassword);
//
//        HttpEntity<Map<String, String>> request = new HttpEntity<>(requestParams, headers);
//        ResponseEntity<String> response = _restTemplate.exchange(user_api_url + "/" + userId + "/change-password",
//                HttpMethod.POST,
//                request,
//                String.class);
//
//        if (response.getStatusCode() == HttpStatus.OK) {
//            model.addAttribute("success", "Password changed successfully");
//        } else {
//            model.addAttribute("error", "Failed to change password");
//        }
//
//        return "users/profile";
//    }
}

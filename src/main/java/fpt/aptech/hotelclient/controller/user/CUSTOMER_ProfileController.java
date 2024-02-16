/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package fpt.aptech.hotelclient.controller.user;

import fpt.aptech.hotelclient.dto.BookingDto;
import fpt.aptech.hotelclient.dto.UserDto;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

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
    public String page(Model model , @RequestParam("userId") int userId) {
        UserDto userProfile = _restTemplate.getForObject(user_api_url+"/findbyid/"+userId, UserDto.class);
        List<BookingDto> allBookingByCustomer = _restTemplate.getForObject(booking_api_url+"/allbookingbycustomer/"+userId, List.class);
        model.addAttribute("userId", userId);
        model.addAttribute("userProfile", userProfile);
        model.addAttribute("bookingList", allBookingByCustomer);
        return "users/profile";
    }
    
}

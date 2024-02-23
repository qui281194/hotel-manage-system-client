/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package fpt.aptech.hotelclient.controller.admin;

import fpt.aptech.hotelclient.dto.BookingDto;
import fpt.aptech.hotelclient.dto.UserDto;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author TuanNguyen
 */
@Controller
@RequestMapping("/client/admin/customercontroller")
public class ADMIN_CustomerController {
    String user_api_url = "http://localhost:9999/api/users";
    String booking_api_url = "http://localhost:9999/api/bookingcontroller";
    
    RestTemplate _restTemplate = new RestTemplate();
    
    @GetMapping("/all")
    public String page(Model model) {
        List<UserDto> allCustomerList = _restTemplate.getForObject(user_api_url+"/allcustomer", List.class);
        model.addAttribute("allCustomer", allCustomerList);
        return "admin/account/customer_index";
    }
    
    @GetMapping("/gotocreate")
    public String goToCreatePage(Model model) {
        UserDto newCustomer = new UserDto();
        newCustomer.setRole_id(3);
        model.addAttribute("newCustomer", newCustomer);
        return "admin/account/customer_create";
    }
    
    @GetMapping("/bookinghistory/{customerId}")
    public String goToCustomerBookingHistory(Model model , @PathVariable("customerId") int customerId) {
        List<BookingDto> bookingByCustomer = _restTemplate.getForObject(booking_api_url+"/allbookingbycustomer/"+customerId, List.class);
        model.addAttribute("bookingList", bookingByCustomer);
        return "admin/account/customer_booking_history";
    }
    
    @PostMapping("/create")
    public String createNewCustomer(Model model , @ModelAttribute("newCustomer") @Valid UserDto newCustomer , BindingResult bdResult) {
        if(bdResult.hasErrors()) {
            return "admin/account/customer_create";
        }
        
        UserDto checkEmailExist = _restTemplate.postForObject(user_api_url+"/createnewcustomer", newCustomer, UserDto.class);
        if(checkEmailExist == null) {
            model.addAttribute("emailErrMessage", "This email already Exist!!!");
            return "admin/account/customer_create";
        }
        else {
            return "redirect:http://localhost:8888/client/admin/customercontroller/all";
        }
    }
    
    @GetMapping("/activeuser/{id}")
    public String activeUser(Model model , @PathVariable("id") int id) {
        UserDto userDtoInfo = _restTemplate.getForObject(user_api_url + "/findbyid/" + id , UserDto.class);
        userDtoInfo.setActive(true);
        
        _restTemplate.put(user_api_url + "/update", userDtoInfo);
        
        return "redirect:http://localhost:8888/client/admin/customercontroller/all";
    }
    
    @GetMapping("/disableuser/{id}")
    public String disableUser(Model model , @PathVariable("id") int id) {
        UserDto userDtoInfo = _restTemplate.getForObject(user_api_url+"/findbyid/"+id , UserDto.class);
        userDtoInfo.setActive(false);
        
        _restTemplate.put(user_api_url+"/update", userDtoInfo);
        
        return "redirect:http://localhost:8888/client/admin/customercontroller/all";
    }
}

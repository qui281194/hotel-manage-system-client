/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package fpt.aptech.hotelclient.controller.user;

import fpt.aptech.hotelclient.dto.BookingDto;
import fpt.aptech.hotelclient.dto.PaymentDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author PC
 */
@Controller
@RequestMapping("/client/customer/paymentcontroller")

public class CUSTOMER_PaymentController {
    
    String room_api_url = "http://localhost:9999/api/roomcontroller";
    String booking_api_url = "http://localhost:9999/api/bookingcontroller";
    String payment_api_url = "http://localhost:9999/api/paymentcontroller";
    
    RestTemplate rest = new RestTemplate();
    
    @GetMapping("/showinvoke")
    public String page(Model model, @RequestParam("userId") int userId, @RequestParam("bookingId") int bookingId) {
        BookingDto bookingDto = rest.getForObject(payment_api_url + "/showbookinginfo/" + bookingId, BookingDto.class);
        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setBooking_id(bookingId);
        
        model.addAttribute("userId",userId);
        model.addAttribute("paymentDto", paymentDto);
        model.addAttribute("bookingList", bookingDto);
        return "users/payment_check";
    }
    
    @PostMapping("/createPayment")
    public String Createpaymentpage(Model model, @RequestParam("userId") int userId, @ModelAttribute("paymentDto") PaymentDto paymentDto){
      PaymentDto response = rest.postForObject(payment_api_url + "/create", paymentDto, PaymentDto.class);
      return "redirect:http://localhost:8888/client/customer/profilecontroller/all?userId=" + userId; 
    }
    
}

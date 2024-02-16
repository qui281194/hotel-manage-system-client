/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package fpt.aptech.hotelclient.controller.user;

import fpt.aptech.hotelclient.models.Feedback;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author TuanNguyen
 */
@Controller
@RequestMapping("/client/customer/contactcontroller")
public class CUSTOMER_ContactController {
    
    @RequestMapping("/all")
    public String page(Model model , @RequestParam("userId") int userId) {
        model.addAttribute("userId", userId);
        model.addAttribute("feedback", new Feedback());
        return "users/contact";
    }
    
}

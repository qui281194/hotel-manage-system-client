/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package fpt.aptech.hotelclient.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author TuanNguyen
 */
@Controller
@RequestMapping("/client/customer/homecontroller")
public class CUSTOMER_HomeController {
    
    @RequestMapping("/all")
    public String page(Model model , @RequestParam(name = "userId" , required = false) String userId) {
        if(userId == null) {
            int userIdToInt = 0;
            model.addAttribute("userId", userIdToInt);
            return "users/home";
        }
        else {
            int userIdToInt = Integer.parseInt(userId);
            model.addAttribute("userId", userIdToInt);
            return "users/home";    
        }
        
    }
    
}

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
@RequestMapping("/client/customer/blogcontroller")
public class CUSTOMER_BlogController {
    
    @RequestMapping("/all")
    public String page(Model model , @RequestParam("userId") int userId) {
        model.addAttribute("userId", userId);
        return "users/blog";
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package fpt.aptech.hotelclient.controller.user;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author ASUS
 */
@Controller
@RequestMapping("/user/room")
public class RoomController {
    
    @RequestMapping("/all")
    public String page() {
        
        return "/users/rooms";
    }
    @RequestMapping("/details")
    public String details() {
        
        return "/users/room-details";
    }
    
}

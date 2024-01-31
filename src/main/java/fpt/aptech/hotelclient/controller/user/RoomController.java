/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package fpt.aptech.hotelclient.controller.user;

import fpt.aptech.hotelclient.dto.RoomDto;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author ASUS
 */
@Controller
@RequestMapping("/user/room")
public class RoomController {

    String room_api_url = "http://localhost:9999/api/roomcontroller";
    RestTemplate _restTemplate = new RestTemplate();

    @GetMapping("/all")
    public String page(Model model) {
        List<RoomDto> allRoomList = _restTemplate.getForObject(room_api_url + "/allroomsortedbyactive", List.class);
        model.addAttribute("allRoomList", allRoomList);
        return "/users/rooms";
    }

    @GetMapping("/{roomId}/details")
    public String details(@PathVariable("roomId") int roomId, Model model) {
        RoomDto room = _restTemplate.getForObject(room_api_url + "/find/" + roomId, RoomDto.class);
        model.addAttribute("room", room);
        return "/users/room-details";
    }

}

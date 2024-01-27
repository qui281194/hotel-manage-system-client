/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package fpt.aptech.hotelclient.controller.admin;

import fpt.aptech.hotelclient.dto.BookingStatusDto;
import fpt.aptech.hotelclient.dto.RoomDto;
import fpt.aptech.hotelclient.dto.RoomTypeDto;
import jakarta.validation.Valid;
import java.util.Comparator;
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
@RequestMapping("/client/admin/roomcontroller")
public class ADMIN_RoomController {
    
    String room_api_url = "http://localhost:9999/api/roomcontroller";
    String roomType_api_url = "http://localhost:9999/api/roomtypecontroller";
    String bookingStatus_api_url = "http://localhost:9999/api/bookingstatuscontroller";
    
    RestTemplate _restTemplate = new RestTemplate();
    
    @GetMapping("/all")
    public String allRoomPage(Model model) {
        List<RoomDto> allRoomList = _restTemplate.getForObject(room_api_url+"/allroomsortedbyactive", List.class);
        model.addAttribute("allRoomList", allRoomList);
        return "admin/room/index";
    }
    
    @GetMapping("/gotocreate")
    public String createRoomPage(Model model) {
        List<RoomTypeDto> allRoomTypeList = _restTemplate.getForObject(roomType_api_url+"/all", List.class);
        RoomDto newRoomDto = new RoomDto();
        newRoomDto.setBooking_status_id(1);
        model.addAttribute("allRoomTypeList", allRoomTypeList);
        model.addAttribute("newRoomDto", newRoomDto);
        return "admin/room/create";
    }
    
    @PostMapping("/create")
    public String createNewRoom(Model model , @ModelAttribute("newRoomDto") @Valid RoomDto newRoomDto , BindingResult bdResult) {
        if(bdResult.hasErrors()) {
            List<RoomTypeDto> allRoomTypeList = _restTemplate.getForObject(roomType_api_url+"/all", List.class);
            model.addAttribute("allRoomTypeList", allRoomTypeList);
            return "admin/room/create";
        }
        
        newRoomDto.setIs_active(true);
        _restTemplate.postForObject(room_api_url+"/create", newRoomDto ,RoomDto.class);
        return "redirect:http://localhost:8888/client/admin/roomcontroller/all";
    }
    
    @GetMapping("/gotoupdate/{roomId}")
    public String updateRoomPage(Model model , @PathVariable("roomId") int roomId) {
        RoomDto roomDtoInfo = _restTemplate.getForObject(room_api_url+"/find/"+roomId , RoomDto.class);
        List<RoomTypeDto> allRoomTypeList = _restTemplate.getForObject(roomType_api_url+"/all", List.class);
        List<BookingStatusDto> allBookingStatusList = _restTemplate.getForObject(bookingStatus_api_url+"/all", List.class);
        model.addAttribute("allRoomTypeList", allRoomTypeList);
        model.addAttribute("allBookingStatusList", allBookingStatusList);
        model.addAttribute("updateRoomDto", roomDtoInfo);
        return "admin/room/update";
    }
    
    @PostMapping("/update")
    public String updateRoom(Model model , @ModelAttribute("updateRoomDto") @Valid RoomDto updateRoomDto , BindingResult bdResult) {
        if(bdResult.hasErrors()) {
            List<RoomTypeDto> allRoomTypeList = _restTemplate.getForObject(roomType_api_url+"/all", List.class);
            model.addAttribute("allRoomTypeList", allRoomTypeList);
            return "admin/room/update";
        }
        
        _restTemplate.put(room_api_url+"/update", updateRoomDto);
        
        return "redirect:http://localhost:8888/client/admin/roomcontroller/all";
    }
    
    @GetMapping("/activeroom/{roomId}")
    public String activeRoom(Model model , @PathVariable("roomId") int roomId) {
        RoomDto roomDtoInfo = _restTemplate.getForObject(room_api_url+"/find/"+roomId , RoomDto.class);
        roomDtoInfo.setIs_active(true);
        
        _restTemplate.put(room_api_url+"/update", roomDtoInfo);
        
        return "redirect:http://localhost:8888/client/admin/roomcontroller/all";
    }
    
    @GetMapping("/disableroom/{roomId}")
    public String disableRoom(Model model , @PathVariable("roomId") int roomId) {
        RoomDto roomDtoInfo = _restTemplate.getForObject(room_api_url+"/find/"+roomId , RoomDto.class);
        roomDtoInfo.setIs_active(false);
        
        _restTemplate.put(room_api_url+"/update", roomDtoInfo);
        
        return "redirect:http://localhost:8888/client/admin/roomcontroller/all";
    }
}

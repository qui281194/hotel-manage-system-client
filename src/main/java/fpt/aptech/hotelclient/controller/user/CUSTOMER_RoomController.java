/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package fpt.aptech.hotelclient.controller.user;

import fpt.aptech.hotelclient.dto.BookingDto;
import fpt.aptech.hotelclient.dto.RoomDto;
import fpt.aptech.hotelclient.dto.SearchDto;
import java.util.List;
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
 * @author TuanNguyen
 */
@Controller
@RequestMapping("/client/customer/roomcontroller")
public class CUSTOMER_RoomController {
    
    String room_api_url = "http://localhost:9999/api/roomcontroller";
    String booking_api_url = "http://localhost:9999/api/bookingcontroller";
    
    RestTemplate _restTemplate = new RestTemplate();
    
    @GetMapping("/all")
    public String page(Model model , @RequestParam("userId") int userId) {
        List<RoomDto> allRoomList = _restTemplate.getForObject(room_api_url+"/all", List.class);
        model.addAttribute("userId", userId);
        model.addAttribute("searchDto", new SearchDto());
        model.addAttribute("allRoomList", allRoomList);
        return "users/rooms";
    }
    
    @GetMapping("/details")
    public String roomDetailsPage(Model model , @RequestParam("userId") int userId , @RequestParam("roomId") int roomId) {
        System.out.println(userId);
        System.out.println(roomId);
        
        RoomDto roomDetail = _restTemplate.getForObject(room_api_url+"/find/"+roomId, RoomDto.class);
        model.addAttribute("userId", userId);
        model.addAttribute("roomDetail", roomDetail);
        return "users/room-details";
    }
    
    @PostMapping("/searchRoom")
    public String searchRoomPage(Model model , @RequestParam("userId") int userId , @ModelAttribute("searchDto") SearchDto searchDto) {
        System.out.println(searchDto);
        List<RoomDto> allRoomAvailable = _restTemplate.postForObject(booking_api_url+"/availableroomforbooking", searchDto , List.class);
        model.addAttribute("userId", userId);
        model.addAttribute("searchDto", new SearchDto());
        model.addAttribute("allRoomList", allRoomAvailable);
        return "users/rooms";
    }
    
    @GetMapping("/gotobooking")
    public String customerGoToBooking(Model model , @RequestParam("userId") int userId , @RequestParam("roomId") int roomId) {
        BookingDto newBookingDto = new BookingDto();
        newBookingDto.setRoom_id(roomId);
        newBookingDto.setCustomer_id(userId);
        
        model.addAttribute("userId", userId);
        model.addAttribute("newBookingDto", newBookingDto);
        return "users/room_booking";
    }
    
    @PostMapping("/confirmbookingdetail")
    public String customerConfirmBookingDetail(Model model, @RequestParam("userId") int userId , @ModelAttribute("newBookingDto") BookingDto newBookingDto) {
        newBookingDto.setIs_active(true);
        BookingDto bookingDetail = _restTemplate.postForObject(booking_api_url+"/confirmbookingdetail", newBookingDto, BookingDto.class);
        
        if(bookingDetail == null) {
            model.addAttribute("userId", userId);
            model.addAttribute("bookingErrMessage", "This room is unvalid for this current time you choose!!!");
            return "users/room_booking";
        }
        else {
            if(bookingDetail.getRoom_info().getRoom_capacity() < newBookingDto.getNumber_of_member()){
                model.addAttribute("userId", userId);
                model.addAttribute("bookingErrMessage", "This room capacity is only: " + bookingDetail.getRoom_info().getRoom_capacity() + " people!!!");
                return "users/room_booking";
            }
            else {
                model.addAttribute("userId", userId);
                model.addAttribute("newBookingDto", bookingDetail);
                return "users/room_booking_detail";
            }
        }
    }
    
    @PostMapping("/customercreatebooking")
    public String customerCreateNewBooking(Model model , @RequestParam("userId") int userId , @ModelAttribute("newBookingDto") BookingDto newBookingDto) {
        newBookingDto.setIs_active(true);
        _restTemplate.postForObject(booking_api_url+"/createbookingbycustomer", newBookingDto, BookingDto.class);
        return "redirect:http://localhost:8888/client/customer/roomcontroller/all?userId="+userId;
    }
    
}

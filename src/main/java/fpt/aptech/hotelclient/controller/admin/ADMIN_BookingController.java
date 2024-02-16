/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package fpt.aptech.hotelclient.controller.admin;

import fpt.aptech.hotelclient.dto.BookingDto;
import fpt.aptech.hotelclient.dto.RoomDto;
import fpt.aptech.hotelclient.dto.SearchDto;
import fpt.aptech.hotelclient.dto.UserDto;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
@RequestMapping("/client/admin/bookingcontroller")
public class ADMIN_BookingController {
    String booking_api_url = "http://localhost:9999/api/bookingcontroller";
    
    String room_api_url = "http://localhost:9999/api/roomcontroller";
    String roomType_api_url = "http://localhost:9999/api/roomtypecontroller";
    String bookingStatus_api_url = "http://localhost:9999/api/bookingstatuscontroller";
    String user_api_url = "http://localhost:9999/api/users";
    
    RestTemplate _restTemplate = new RestTemplate();
    
    @GetMapping("/all")
    public String allBookingPage(Model model) {
        List<BookingDto> allBookingList = _restTemplate.getForObject(booking_api_url+"/all", List.class);
        model.addAttribute("bookingList", allBookingList);
        return "admin/booking/index";
    }
    
    @GetMapping("/gotoroomtobooking")
    public String goToRoomToBooking(Model model) {
        List<RoomDto> allRoomActiveAndVacancy = _restTemplate.getForObject(room_api_url+"/all", List.class);
        model.addAttribute("searchDto", new SearchDto());
        model.addAttribute("allRoomActiveAndVacancy", allRoomActiveAndVacancy);
        return "admin/booking/roomtobooking";
    }
    
    @PostMapping("/availableroomtobooking") 
    public String availableRoomToBooking(Model model , @ModelAttribute("searchDto") SearchDto searchDto) {
        List<RoomDto> allRoomAvailable = _restTemplate.postForObject(booking_api_url+"/availableroomforbooking", searchDto , List.class);
        model.addAttribute("searchDto", searchDto);
        model.addAttribute("allRoomActiveAndVacancy", allRoomAvailable);
        return "admin/booking/roomtobooking";
    }
    
    @GetMapping("/gotobookingforguest/{roomId}")
    public String goToBookingForGuest(Model model , @PathVariable("roomId") int roomId) {
//        model.addAttribute("roomId", roomId);
        BookingDto newBookingDto = new BookingDto();
        newBookingDto.setRoom_id(roomId);
        model.addAttribute("newBookingDto", newBookingDto);
        return "admin/booking/createforguest";
    }
    
    @PostMapping("/createbookingforguest")
    public String createBookingForGuest(Model model , @ModelAttribute("newBookingDto") BookingDto newBookingDto) {
        UserDto newGuestDto = new UserDto();
        newGuestDto.setPhone(newBookingDto.getCustomer_info().getPhone());
        newGuestDto.setPassword("Guest");
        
        UserDto guestResponse = _restTemplate.postForObject(user_api_url+"/createnewguest", newGuestDto , UserDto.class);
        
        newBookingDto.setCustomer_id(guestResponse.getId());
        newBookingDto.setIs_active(true);
        
        BookingDto response = _restTemplate.postForObject(booking_api_url+"/create", newBookingDto, BookingDto.class);
        
        System.out.println(response);
        
        return "redirect:http://localhost:8888/client/admin/bookingcontroller/all";
    }
    
    @GetMapping("/confirmbooking/{bookingId}")
    public String confirmBooking(Model model , @PathVariable("bookingId") int bookingId) {
        _restTemplate.getForObject(booking_api_url+"/confirm/"+bookingId, BookingDto.class);
        return "redirect:http://localhost:8888/client/admin/bookingcontroller/all";
    }
    
    @GetMapping("/checkinbooking/{bookingId}")
    public String checkinBooking(Model model , @PathVariable("bookingId") int bookingId) {
        _restTemplate.getForObject(booking_api_url+"/checkin/"+bookingId, BookingDto.class);
        
        return "redirect:http://localhost:8888/client/admin/bookingcontroller/all";
    }
    
    @GetMapping("/checkoutbooking/{bookingId}")
    public String checkoutBooking(Model model , @PathVariable("bookingId") int bookingId) {
        _restTemplate.getForEntity(booking_api_url+"/checkout/"+bookingId, BookingDto.class);
        
        return "redirect:http://localhost:8888/client/admin/bookingcontroller/all";
    }
    
    @GetMapping("/cancelbooking/{bookingId}")
    public String cancelBooking(Model model , @PathVariable("bookingId") int bookingId) {
        _restTemplate.getForObject(booking_api_url+"/cancel/"+bookingId, BookingDto.class);
        
        return "redirect:http://localhost:8888/client/admin/bookingcontroller/all";
    }
            
 }

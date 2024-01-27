/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fpt.aptech.hotelclient.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author TuanNguyen
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {
    private int id;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate booking_from;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate booking_to;
    
    private int total_day;
    private double total_price;
    private int number_of_member;
    
    private Boolean is_active;
    
    private int booking_current_id;
    private BookingCurrentDto booking_current_info;
    
    private int customer_id;
    private UserDto customer_info;
    
    private int room_id;
    private RoomDto room_info;
}

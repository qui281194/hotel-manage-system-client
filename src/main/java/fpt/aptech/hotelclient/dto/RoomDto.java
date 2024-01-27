/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fpt.aptech.hotelclient.dto;

import jakarta.validation.constraints.*;
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
public class RoomDto {
    private int id;
    
    @NotBlank(message = "Room No must be fill!!!")
    private String room_no;
    
    @Min(value = 1 , message = "Room Price value must be greater than 0!!!")
    private double room_price;
    
    @NotBlank(message = "Room Image must be choose!!!")
    private String room_image;
    
    @Min(value = 1 , message = "Room Capacity value must be greater than 0!!!")
    private int room_capacity;
    
    @NotBlank(message = "Room Description must be fill!!!")
    private String room_description;
    
    private Boolean is_active;
    
    @Min(value = 1 , message = "Booking Status must be choose!!!")
    private int booking_status_id;
    private BookingStatusDto booking_status_info;
    
    @Min(value = 1 , message = "Room Type must be choose!!!")
    private int room_type_id;
    private RoomTypeDto room_type_info;
}

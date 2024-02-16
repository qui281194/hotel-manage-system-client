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
public class SearchDto {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate booking_from;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate booking_to;
    
    private double from_price;
    private double to_price;
    
    private int room_type_id;
}

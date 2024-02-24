/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fpt.aptech.hotelclient.dto;

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
public class PaymentDto {
    private int id;
    private double final_amount;
    private String payment_type;
    private String payment_status;
    
    private Boolean is_active;
    
    private LocalDate created_at;
    
    private int booking_id;
    private BookingDto booking_info;
}

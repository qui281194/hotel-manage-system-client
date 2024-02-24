/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fpt.aptech.hotelclient.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
@Entity
@Table(name = "tbl_booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDate booking_from;
    private LocalDate booking_to;
    private int total_day;
    private double total_price;
    private int number_of_member;
    private Boolean is_active;
    
    @JoinColumn(name = "booking_current_id" , referencedColumnName = "id") 
    @ManyToOne(fetch = FetchType.LAZY , targetEntity = BookingCurrent.class)
    private BookingCurrent booking_current_id;
    
    @JoinColumn(name = "customer_id" , referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY , targetEntity = Users.class)
    private Users customer_id;
    
    @JoinColumn(name = "room_id" , referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY , targetEntity = Room.class)
    private Room room_id;
    
    @OneToOne(mappedBy = "booking")
    private Payment payment;
}

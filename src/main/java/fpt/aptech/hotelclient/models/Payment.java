
package fpt.aptech.hotelclient.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

/**
 *
 * @author TuanNguyen
 */

@Data
@NoArgsConstructor
@Entity
@Table(name = "tbl_payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double final_amount;
    private String payment_type;
    private String payment_status;
    private Boolean is_active;
    
    @CreationTimestamp
    private LocalDate created_at;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "booking_id" , referencedColumnName = "id")
    private Booking booking;
}

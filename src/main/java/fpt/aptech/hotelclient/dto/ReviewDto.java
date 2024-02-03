
package fpt.aptech.hotelclient.dto;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author PC
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {
    private int id;
    private String reviewtext;
    private Date datesubmited;
    private int rating;
    private boolean status;
    
    private int user_id;
    private UserDto user;
    private String username;
    
    private int room_id;
    private RoomDto room;
    private String room_no;
}

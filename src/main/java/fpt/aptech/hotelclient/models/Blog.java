/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fpt.aptech.hotelclient.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author ASUS
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_blog")
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Tiêu đề của bài đăng Blog về khách sạn
    private String title;

    // Nội dung chi tiết về thông tin, sự kiện, hoặc đánh giá về khách sạn
    private String content;

    // Ngày tạo bài đăng Blog
    private Date createdAt;

    // Tác giả của bài đăng
    private String author;

//    // Liên kết với một phòng cụ thể trong khách sạn, nếu có
//    @ManyToOne
//    @JoinColumn(name = "room_id")
//    private Room relatedRoom;

    // Hình ảnh minh họa cho bài đăng (URL hoặc đường dẫn đến hình ảnh)
    private String imageUrl;

    // Số lượt xem của bài đăng
    private int views;

    // Số lượt thích (like) của bài đăng
    private int likes;
}

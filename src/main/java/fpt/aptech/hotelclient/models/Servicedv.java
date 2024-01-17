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
@Table(name = "tbl_servicedv")
public class Servicedv {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Tên của dịch vụ
    private String name;

    // Giá của dịch vụ
    private double price;

    // Mô tả ngắn về dịch vụ
    private String description;

    // Loại dịch vụ (liên kết với ServiceCategory thông qua category_id)
    @ManyToOne
    @JoinColumn(name = "category_id")
    private ServiceCategory category;

    // Đường dẫn đến hình ảnh đại diện cho dịch vụ
    private String image;

    // Số lượng của dịch vụ (ví dụ: số lượng phòng, số lượng người tham gia)
    private Integer quantity;

    // Thời gian cung cấp dịch vụ (nếu có)
    private Integer serviceDuration;

    // Trạng thái của dịch vụ (ví dụ: "đang hoạt động", "tạm dừng", "hết hạn")
    private boolean status;

    

}

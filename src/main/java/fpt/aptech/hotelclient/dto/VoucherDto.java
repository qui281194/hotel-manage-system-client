/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fpt.aptech.hotelclient.dto;

import io.micrometer.common.lang.Nullable;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
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
public class VoucherDto {

    private Integer id;
    private String code;
    private String type;
    private String title;
    private double cashAmountDiscount;
    private double percentageDiscount;
    private LocalDate expirationDate;
    private boolean isActive;

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

}

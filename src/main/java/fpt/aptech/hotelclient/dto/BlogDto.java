/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fpt.aptech.hotelclient.dto;

import fpt.aptech.hotelclient.models.Room;
import java.util.Date;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author ASUS
 */
public class BlogDto {
    private Integer id;
    private String title;
    private String content;
    private Date createdAt;
    private String author;
//    private Room roomid;
    private MultipartFile imageUrl;
    private int views;
    private int likes;

    public BlogDto() {
    }

    public BlogDto(Integer id, String title, String content, Date createdAt, String author, MultipartFile imageUrl, int views, int likes) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.author = author;
       
        this.imageUrl = imageUrl;
        this.views = views;
        this.likes = likes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    

    public MultipartFile getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(MultipartFile imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
    
    
}

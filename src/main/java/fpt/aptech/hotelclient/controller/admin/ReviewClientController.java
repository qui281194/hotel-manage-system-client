/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package fpt.aptech.hotelclient.controller.admin;

import fpt.aptech.hotelclient.dto.ReviewDto;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author PC
 */
@Controller
@RequestMapping("/controller/review")
public class ReviewClientController {

    RestTemplate restTemplate = new RestTemplate();
    private final String apiUrl = "http://localhost:9999/api/reviewcontroller";

    @GetMapping("/")
    public String index(Model model) {
        ResponseEntity<ReviewDto[]> responseEntity = restTemplate.getForEntity(apiUrl + "/all", ReviewDto[].class);
        List<ReviewDto> reviewDtoList = Arrays.asList(responseEntity.getBody());
        model.addAttribute("reviewList", reviewDtoList);
        return "admin/review/Rindex";
    }
    
    @GetMapping("/search")
    public String searchReviewByText(Model model, @RequestParam String reviewtext){
        ResponseEntity<ReviewDto[]> response = restTemplate.getForEntity(apiUrl + "/search?reviewtext=" + reviewtext,ReviewDto[].class);
        List<ReviewDto> review = Arrays.asList(response.getBody());
        model.addAttribute("rlist", review);
        return "admin/review/Rindex";
    }

}

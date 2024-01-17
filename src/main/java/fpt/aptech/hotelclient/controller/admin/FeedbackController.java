/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package fpt.aptech.hotelclient.controller.admin;

import fpt.aptech.hotelclient.models.Feedback;
import java.util.Arrays;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author ASUS
 */
@Controller
@RequestMapping("/admin/feedbacks")
public class FeedbackController {
    
     private final String apiUrl = "http://localhost:9999/api/feedbackcontroller/";
      RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/all")
    public String getAllFeedbacks(Model model) {
        // Make a GET request to retrieve all feedbacks from the API
        ResponseEntity<Feedback[]> responseEntity = restTemplate.getForEntity(apiUrl + "all", Feedback[].class);
        Feedback[] feedbacks = responseEntity.getBody();

        // Convert the array to a List for easier handling in the view
        List<Feedback> feedbackList = Arrays.asList(feedbacks);

        // Add the feedback list to the model for rendering in the view
        model.addAttribute("feedbacks", feedbackList);

        // Return the name of the view to be rendered
        return "admin/contact/index";
    }
    @GetMapping("/delete/{id}")
    public String deleteFeedback(@PathVariable Integer id) {
        // Make a DELETE request to delete the feedback with the specified ID
        restTemplate.delete(apiUrl + "delete/" + id);

        // Redirect to the feedback list page after deletion
        return "redirect:/admin/feedbacks/all";
    }
    @PostMapping("/search")
    public String searchFeedback(@RequestParam("sender") String sender, Model model) {
        // Gửi yêu cầu tìm kiếm đến server và nhận danh sách kết quả
        ResponseEntity<Feedback[]> responseEntity = restTemplate.getForEntity(apiUrl + "search?sender=" + sender, Feedback[].class);
        Feedback[] searchedFeedbacks = responseEntity.getBody();

        // Convert array to List for easier handling in the view
        List<Feedback> searchedFeedbackList = Arrays.asList(searchedFeedbacks);

        // Add the searched feedback list to the model for rendering in the view
        model.addAttribute("feedbacks", searchedFeedbackList);

        // Return the name of the view to be rendered
        return "admin/contact/index";
    }
    
}

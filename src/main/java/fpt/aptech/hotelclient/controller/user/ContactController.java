/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package fpt.aptech.hotelclient.controller.user;

import fpt.aptech.hotelclient.models.Feedback;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author ASUS
 */
@Controller
@RequestMapping("/user/contact")
public class ContactController {

    private final String apiUrl = "http://localhost:9999/api/feedbackcontroller/";
    RestTemplate restTemplate = new RestTemplate();

    @RequestMapping("/all")
    public String page(Model model) {
        
        return "users/contact";
    }

    @GetMapping("/all")
    public String showCreateFeedbackForm(Model model) {
        // Create an empty Feedback object to bind to the form
        model.addAttribute("feedback", new Feedback());

        // Return the name of the view for creating a new feedback
        return "users/contact";  // Use the same view name as in the saveFeedback method
    }

    @PostMapping("/save")
    public String saveFeedback( Feedback feedback) {
        // Set the appropriate headers for the request
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create an HttpEntity with the Feedback object and headers
        HttpEntity<Feedback> requestEntity = new HttpEntity<>(feedback, headers);

        // Make a POST request to save the feedback
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(apiUrl + "save", requestEntity, String.class);

        // Redirect to the feedback list page
        return "redirect:/user/contact/all";
    }

}

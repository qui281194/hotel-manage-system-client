package fpt.aptech.hotelclient.controller.admin;

import fpt.aptech.hotelclient.dto.ServiceCategoryDto;
import fpt.aptech.hotelclient.dto.ServicedvDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/admin/serviceCategories")
public class ServiceCategoryController {

    private final String apiUrl = "http://localhost:9999/api/servicecategorycontroller/";

    RestTemplate restTemplate = new RestTemplate();

    // Get all service categories
    @GetMapping("/all")
    public String getAllServiceCategories(Model model) {
        ResponseEntity<ServiceCategoryDto[]> response = restTemplate.getForEntity(apiUrl + "all", ServiceCategoryDto[].class);
        List<ServiceCategoryDto> categories = Arrays.asList(response.getBody());
        model.addAttribute("categories", categories);
        return "admin/servicedv/serviceCategoryList"; // Adjust the view name as needed
    }

    // Create a new service category
    @PostMapping("/create")
    public String createServiceCategory(@ModelAttribute ServiceCategoryDto categoryDto) {
        ResponseEntity<ServiceCategoryDto> response = restTemplate.postForEntity(apiUrl + "create", categoryDto, ServiceCategoryDto.class);
        return "redirect:/admin/serviceCategories/all"; // Redirect to the list after creating
    }

    // Delete a service category by ID
    @GetMapping("/delete/{categoryId}")
    public String deleteServiceCategory(@PathVariable Integer categoryId) {
        restTemplate.delete(apiUrl + "delete/{categoryId}", categoryId);
        return "redirect:/admin/serviceCategories/all"; // Redirect to the list after deleting
    }

    // Update a service category by ID
    @GetMapping("/edit/{categoryId}")
    public String editServiceCategory(@PathVariable Integer categoryId, Model model) {
        ResponseEntity<ServiceCategoryDto> response = restTemplate.getForEntity(apiUrl + "edit/{categoryId}", ServiceCategoryDto.class, categoryId);
        model.addAttribute("category", response.getBody());
        return "editServiceCategory"; // Adjust the view name as needed
    }

    @PostMapping("/edit/{categoryId}")
    public String updateServiceCategory(@PathVariable Integer categoryId, @ModelAttribute ServiceCategoryDto updatedCategoryDto) {
        restTemplate.put(apiUrl + "edit/{categoryId}", updatedCategoryDto, categoryId);
        return "redirect:/admin/serviceCategories/all"; // Redirect to the list after updating
    }
    // Search service categories by name
    @GetMapping("/search")
    public String searchServiceCategoriesByName(@RequestParam String name, Model model) {
        ResponseEntity<ServiceCategoryDto[]> response = restTemplate.getForEntity(apiUrl + "search?name={name}", ServiceCategoryDto[].class, name);
        List<ServiceCategoryDto> categories = Arrays.asList(response.getBody());
        model.addAttribute("categories", categories);
        return "admin/servicedv/serviceCategoryList"; // Điều chỉnh tên view nếu cần thiết
    }
    

}

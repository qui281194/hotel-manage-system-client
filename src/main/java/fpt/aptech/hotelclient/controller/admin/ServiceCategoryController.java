package fpt.aptech.hotelclient.controller.admin;

import fpt.aptech.hotelclient.dto.ServiceCategoryDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
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
    
//    @GetMapping("/allByRoom")
//    public String getAllServiceByRoom(Model model) {
//        ResponseEntity<ServiceCategoryDto[]> response = restTemplate.getForEntity(apiUrl + "all", ServiceCategoryDto[].class);
//        List<ServiceCategoryDto> categories = Arrays.asList(response.getBody());
//        model.addAttribute("categories", categories);
//        return "user/room-details"; // Adjust the view name as needed
//    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("categoryDto", new ServiceCategoryDto());
        return "admin/servicedv/create"; // Adjust the view name based on your project structure
    }

    @PostMapping("/create")
    public String createServiceCategory(@ModelAttribute @Valid ServiceCategoryDto categoryDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            // Nếu có lỗi xác nhận, quay lại mẫu tạo với thông báo lỗi
            model.addAttribute("categoryDto", categoryDto);
            return "admin/servicedv/create";
        }

        ResponseEntity<ServiceCategoryDto> response = restTemplate.postForEntity(apiUrl + "create", categoryDto, ServiceCategoryDto.class);

        if (response.getStatusCode() == HttpStatus.CREATED) {
            // Nếu tạo thành công, chuyển hướng đến danh sách
            return "redirect:/admin/serviceCategories/all";
        } else {
            // Nếu có vấn đề với API, xử lý tương ứng (ví dụ: hiển thị thông báo lỗi)
            model.addAttribute("error", "Error creating service category. Please try again.");
            return "admin/servicedv/create";
        }
    }

    // Search service categories by name
    @GetMapping("/search")
    public String searchServiceCategoriesByName(@RequestParam String name, Model model) {
        ResponseEntity<ServiceCategoryDto[]> response = restTemplate.getForEntity(apiUrl + "search?name={name}", ServiceCategoryDto[].class, name);
        List<ServiceCategoryDto> categories = Arrays.asList(response.getBody());
        model.addAttribute("categories", categories);
        return "admin/servicedv/serviceCategoryList"; // Điều chỉnh tên view nếu cần thiết
    }

    // Delete a service category by ID
    @GetMapping("/delete/{id}")
    public String deleteServiceCategory(@PathVariable Integer id) {
        restTemplate.delete(apiUrl + "delete/{id}", id);
        return "redirect:/admin/serviceCategories/all"; // Redirect to the list after deleting
    }

    // Delete multiple service categories by IDs
    @PostMapping("/deleteMulti")
    public String deleteAll(@RequestParam(value = "txtId", required = false) List<Integer> categoryIds) {
        restTemplate.postForObject(apiUrl + "deleteMulti", categoryIds, String.class);
        return "redirect:/admin/serviceCategories/all"; // Redirect to the list after deleting
    }

    // Hiển thị form sửa thông tin một loại dịch vụ
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        ResponseEntity<ServiceCategoryDto> response = restTemplate.getForEntity(apiUrl + "edit/{id}", ServiceCategoryDto.class, id);
        ServiceCategoryDto categoryDto = response.getBody();
        model.addAttribute("categoryDto", categoryDto);
        return "admin/servicedv/edit"; // Điều chỉnh tên view nếu cần thiết
    }

    // Xử lý sự kiện sửa thông tin một loại dịch vụ
    @PostMapping("/edit/{id}")
    public String editServiceCategory(@PathVariable Integer id, @ModelAttribute @Valid ServiceCategoryDto updatedCategoryDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            // Handle validation errors
            model.addAttribute("categoryDto", updatedCategoryDto);
            return "admin/servicedv/edit";
        }

        ResponseEntity<ServiceCategoryDto> response = restTemplate.exchange(
                apiUrl + "/edit/{id}", HttpMethod.PUT, new HttpEntity<>(updatedCategoryDto), ServiceCategoryDto.class, id);

        if (response.getStatusCode().is2xxSuccessful()) {
            return "redirect:/admin/serviceCategories/all";
        } else {
            model.addAttribute("error", "Error updating service category. Please try again.");
            return "admin/servicedv/edit";
        }
    }

}

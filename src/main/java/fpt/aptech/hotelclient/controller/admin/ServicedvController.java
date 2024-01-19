/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package fpt.aptech.hotelclient.controller.admin;

import fpt.aptech.hotelclient.dto.ServiceCategoryDto;
import fpt.aptech.hotelclient.dto.ServicedvDto;
import fpt.aptech.hotelclient.models.Servicedv;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author ASUS
 */
@Controller
@RequestMapping("/admin/servicedv")
public class ServicedvController {

    @Value("${upload.path}")
    private String filterUpload;
    private final String apiUrl = "http://localhost:9999/api/servicedvcontroller/";

    RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/all")
    public String all(Model model) {
        // Gọi API để lấy danh sách tất cả các dịch vụ
//        Servicedv servicesArray = restTemplate.getForObject(apiUrl + "all", Servicedv.class);
//        
//        // Chuyển đổi mảng thành List để sử dụng trong template
//        List<Servicedv> services = Arrays.asList(servicesArray);
//        
//        // Đưa danh sách dịch vụ vào model để hiển thị trên view
//        model.addAttribute("services", services);
        model.addAttribute("services", restTemplate.getForObject(apiUrl + "all", List.class));
        // Trả về tên của view (được giả định là "all.html" trong thư mục "admin/servicedv")
        return "admin/homeservice/index";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        // Gọi API để lấy danh sách tất cả các danh mục
        ServiceCategoryDto[] categoriesArray = restTemplate.getForObject(apiUrl + "categories", ServiceCategoryDto[].class);

        // Chuyển đổi mảng thành List để sử dụng trong template
        List<ServiceCategoryDto> categories = Arrays.asList(categoriesArray);

        // Hiển thị form tạo mới dịch vụ và truyền danh sách danh mục vào model
        model.addAttribute("servicedv", new Servicedv());
        model.addAttribute("categories", categories);

        // Trả về tên của view (được giả định là "create.html" trong thư mục "admin/servicedv")
        return "admin/homeservice/create";
    }

    @PostMapping("/create")
    public String Createproduct(@ModelAttribute("servicedv") ServicedvDto newproduct,
            Model model, RedirectAttributes redirectAttributes) throws IOException {
        MultipartFile file = newproduct.getImage();
        if (file.isEmpty()) {
            return "Please select a file to upload";
        }

        try {
            String fileName = file.getOriginalFilename();
            FileCopyUtils.copy(newproduct.getImage().getBytes(), new File(filterUpload, fileName));

            Servicedv product = new Servicedv();

            product.setName(newproduct.getName());
            product.setPrice(newproduct.getPrice());

            product.setDescription(newproduct.getDescription());
            product.setQuantity(newproduct.getQuantity());
            product.setServiceDuration(newproduct.getServiceDuration());

            product.setStatus(newproduct.isStatus());
            product.setImage(fileName);
            product.setCategory(newproduct.getCategory());

            // ... xử lý các thông tin khác và lưu vào cơ sở dữ liệu
            String createUrl = apiUrl + "/create"; // Endpoint của RESTful API để tạo đối tượng mới
            restTemplate.postForEntity(createUrl, product, Servicedv.class);
            return "redirect:/admin/servicedv/all";
        } catch (IOException e) {
            return "Failed to upload file: " + e.getMessage();
        }

    }

    @GetMapping("/delete/{id}")
    public String deleteService(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            String deleteUrl = apiUrl + "delete/" + id; // Endpoint của RESTful API để xóa đối tượng theo ID
            restTemplate.delete(deleteUrl);
            return "redirect:/admin/servicedv/all";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Xóa dịch vụ thất bại");
            return "redirect:/admin/servicedv/all";
        }
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Integer id, Model model) {
        Servicedv serviceDetails = restTemplate.getForObject(apiUrl + "edit/" + id, Servicedv.class);
        List<ServiceCategoryDto> categories = Arrays.asList(restTemplate.getForObject(apiUrl + "categories", ServiceCategoryDto[].class));
        model.addAttribute("servicedv", serviceDetails);
        model.addAttribute("categories", categories);
        return "admin/homeservice/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable("id") Integer id, @ModelAttribute ServicedvDto product,
            Model model, RedirectAttributes redirectAttributes) throws IOException {
        try {
            String detailsUrl = apiUrl + "edit/" + id;
            Servicedv originalService = restTemplate.getForObject(detailsUrl, Servicedv.class);

            MultipartFile multipartFile = product.getImage();

            if (multipartFile != null) {
                String filename = multipartFile.getOriginalFilename();

                if (!filename.isEmpty()) {
                    FileCopyUtils.copy(multipartFile.getBytes(), new File(filterUpload, filename));
                    originalService.setImage(filename);
                }
            }

            originalService.setName(product.getName());
            originalService.setPrice(product.getPrice());
            originalService.setDescription(product.getDescription());
            originalService.setQuantity(product.getQuantity());
            originalService.setServiceDuration(product.getServiceDuration());
            originalService.setStatus(product.isStatus());
            originalService.setCategory(product.getCategory());

            restTemplate.put(apiUrl + "edit/" + id, originalService);

            return "redirect:/admin/servicedv/all";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to update service");
            return "redirect:/admin/servicedv/all";
        }
    }

    @GetMapping("/search")
    public String searchServices(@RequestParam("name") String name, Model model) {
        try {
            ResponseEntity<Servicedv[]> responseEntity = restTemplate.getForEntity(apiUrl + "search?name=" + name, Servicedv[].class);
            List<Servicedv> searchedServices = Arrays.asList(responseEntity.getBody());
            model.addAttribute("services", searchedServices);
        } catch (Exception e) {
            model.addAttribute("error", "Failed to search services");
        }
        return "admin/homeservice/index";
    }

}

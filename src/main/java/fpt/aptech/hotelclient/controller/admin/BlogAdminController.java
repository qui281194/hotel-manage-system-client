/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package fpt.aptech.hotelclient.controller.admin;

import fpt.aptech.hotelclient.dto.BlogDto;
import fpt.aptech.hotelclient.dto.ServiceCategoryDto;
import fpt.aptech.hotelclient.models.Blog;
import fpt.aptech.hotelclient.models.Room;
import jakarta.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
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
@RequestMapping("/admin/blogs")
public class BlogAdminController {
     @Value("${upload.path}")
    private String filterUpload;
    private final String apiUrl = "http://localhost:9999/api/blogcontroller/";

    RestTemplate restTemplate = new RestTemplate();
    
    @GetMapping("/all")
    public String getAllBlogs(Model model) {
        String getAllBlogsUrl = apiUrl + "all";

        // Make a GET request to the API endpoint to get all blogs
        Blog[] blogs = restTemplate.getForObject(getAllBlogsUrl, Blog[].class);
        List<Blog> blogList = Arrays.asList(blogs);

        model.addAttribute("blogs", blogList);
        return "admin/blog/index"; // Assuming you have a Thymeleaf template named "index.html" in the "blog" folder
    }
    @GetMapping("/create")
    public String createForm(Model model) {
//        // Gọi API để lấy danh sách tất cả các danh mục
//        Room[] roomArray = restTemplate.getForObject(apiUrl + "rooms", Room[].class);
//
//        // Chuyển đổi mảng thành List để sử dụng trong template
//        List<Room> categories = Arrays.asList(roomArray);

        // Hiển thị form tạo mới dịch vụ và truyền danh sách danh mục vào model
        model.addAttribute("blogs", new Blog());
//        model.addAttribute("rooms", categories);

        // Trả về tên của view (được giả định là "create.html" trong thư mục "admin/servicedv")
        return "admin/blog/create";
    }
    @PostMapping("/create")
    public String Createproduct(@ModelAttribute("blogs") BlogDto newproduct,
            Model model, RedirectAttributes redirectAttributes) throws IOException {
        MultipartFile file = newproduct.getImageUrl();
        if (file.isEmpty()) {
            return "Please select a file to upload";
        }

        try {
            String fileName = file.getOriginalFilename();
            FileCopyUtils.copy(newproduct.getImageUrl().getBytes(), new File(filterUpload, fileName));

            Blog product = new Blog();
           
            product.setTitle(newproduct.getTitle());
            product.setContent(newproduct.getContent());
            
            product.setCreatedAt(new Date());
            product.setAuthor(newproduct.getAuthor());
            
             product.setImageUrl(fileName);
           

            // ... xử lý các thông tin khác và lưu vào cơ sở dữ liệu
            String createUrl = apiUrl + "/blogs"; // Endpoint của RESTful API để tạo đối tượng mới
            restTemplate.postForEntity(createUrl, product, Blog.class);
            return "redirect:/admin/blogs/all";
        } catch (IOException e) {
            return "Failed to upload file: " + e.getMessage();
        }

    }
    @GetMapping("/delete/{id}")
    public String deleteService(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            String deleteUrl = apiUrl + "delete/" + id; // Endpoint của RESTful API để xóa đối tượng theo ID
            restTemplate.delete(deleteUrl);
            return "redirect:/admin/blogs/all";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Xóa dịch vụ thất bại");
            return "redirect:/admin/blogs/all";
        }
    }
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Integer id, Model model) {
        Blog serviceDetails = restTemplate.getForObject(apiUrl + "edit/" + id, Blog.class);
        
        model.addAttribute("blogs", serviceDetails);
        
        return "admin/blog/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable("id") Integer id, @Valid @ModelAttribute("blogs") BlogDto product,
                     BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        try {
            String detailsUrl = apiUrl + "edit/" + id;
            Blog originalService = restTemplate.getForObject(detailsUrl, Blog.class);

            MultipartFile multipartFile = product.getImageUrl();

            if (multipartFile != null) {
                String filename = multipartFile.getOriginalFilename();

                if (!filename.isEmpty()) {
                    FileCopyUtils.copy(multipartFile.getBytes(), new File(filterUpload, filename));
                    originalService.setImageUrl(filename);
                }
            }

            originalService.setTitle(product.getTitle());
            originalService.setContent(product.getContent());
            originalService.setCreatedAt(new Date());
            originalService.setAuthor(product.getAuthor());
            

            restTemplate.put(apiUrl + "edit/" + id, originalService);

            return "redirect:/admin/blogs/all";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to update service");
            return "redirect:/admin/blogs/all";
        }
    }
    @GetMapping("/search")
    public String searchServices(@RequestParam("title") String title, Model model) {
        try {
            ResponseEntity<Blog[]> responseEntity = restTemplate.getForEntity(apiUrl + "search?title=" + title, Blog[].class);
            List<Blog> searchedServices = Arrays.asList(responseEntity.getBody());
            model.addAttribute("blogs", searchedServices);
        } catch (Exception e) {
            model.addAttribute("error", "Failed to search services");
        }
        return "admin/blog/index";
    }
    
}

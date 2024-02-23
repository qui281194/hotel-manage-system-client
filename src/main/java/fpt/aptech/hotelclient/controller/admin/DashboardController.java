/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package fpt.aptech.hotelclient.controller.admin;

import fpt.aptech.hotelclient.dto.RoomDto;
import fpt.aptech.hotelclient.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author PC
 */
@Controller
public class DashboardController {

    RestTemplate rest = new RestTemplate();
    private final String apiUrl = "http://localhost:9999/api/users/";

    @GetMapping("/admin/all-staff")
    public String showAdminDashboard(Model model) {
        ResponseEntity<UserDto[]> response = rest.getForEntity(apiUrl + "all", UserDto[].class);
        List<UserDto> allUsers = Arrays.asList(response.getBody());
        model.addAttribute("users", allUsers);
        return "admin/account/staff_index";
    }

    @GetMapping("/admin/user/{id}")
    public String getUserById(@PathVariable("id") Integer id, Model model) {
        UserDto userDto = rest.getForObject(apiUrl + "{id}"+ id, UserDto.class);
        model.addAttribute("user", userDto);
        return "profile"; // Trả về tên template để hiển thị thông tin người dùng
    }

//    @GetMapping("/users/dashboard")
//    public String showUserDashboard(Model model) {
//        UserDto currentUser = getCurrentUser();
//
//        if (currentUser != null) {
//            model.addAttribute("user", currentUser);
//            return "admin/dashboard";
//        } else {
//            model.addAttribute("error", "User not found");
//            return "error";
//        }
//    }
    private UserDto getCurrentUser() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        UserDto currentUser = (UserDto) request.getSession().getAttribute("currentUser");

        // Kiểm tra xem người dùng hiện tại có tồn tại hay không
        if (currentUser != null) {
            // Nếu tồn tại, trả về thông tin người dùng
            return currentUser;
        } else {
            return null;
        }
    }

    

    @RequestMapping("/admin/create-user")
    public String createUser(Model model) {
        model.addAttribute("newUser", new UserDto());
        return "admin/account/create-user";
    }

    @PostMapping("/admin/create-user")
    public String createNewUser(@ModelAttribute("newUser") @Valid UserDto newUser, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            // Xử lý khi có lỗi xảy ra trong dữ liệu đầu vào
            return "admin/account/create-user";
        }

        // Gọi API để tạo người dùng mới
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<UserDto> request = new HttpEntity<>(newUser, headers);
        ResponseEntity<String> response = rest.exchange(apiUrl + "create",
                HttpMethod.POST,
                request,
                String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
//            redirectAttributes.addFlashAttribute("message", "User created successfully");
            return "redirect:/admin/all-staff";
        } else if (response.getStatusCode() == HttpStatus.CONFLICT) {
            redirectAttributes.addFlashAttribute("error", "Email already exists");
            return "redirect:/admin/create-user";
        } else {
            redirectAttributes.addFlashAttribute("error", "Failed to create user");
            return "redirect:/admin/create-user";
        }
    }

    @GetMapping("/admin/user/{id}/edit")
    public String editUserForm(@PathVariable("id") Integer id, Model model) {
        UserDto userDto = rest.getForObject(apiUrl + id, UserDto.class);
        model.addAttribute("user", userDto);
        return "admin/account/edit-user";
    }

    @PostMapping("/admin/user/{id}/edit")
    public String editUser(@PathVariable("id") Integer id, @ModelAttribute("user") @Valid UserDto updatedUserDto,
            BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            // Xử lý khi có lỗi xảy ra trong dữ liệu đầu vào
            return "admin/account/edit-user";
        }

        // Gọi API để cập nhật thông tin người dùng
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<UserDto> request = new HttpEntity<>(updatedUserDto, headers);
        ResponseEntity<String> response = rest.exchange(apiUrl + id,
                HttpMethod.PUT,
                request,
                String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            redirectAttributes.addFlashAttribute("message", "User updated successfully");
            return "redirect:/admin/all-staff";
        } else {
            redirectAttributes.addFlashAttribute("error", "Failed to update user");
            return "redirect:/admin/user/{id}/edit";
        }
    }
    
//    @RequestMapping("/profile")
//    public String profile(Model model , @RequestParam("userId") int userId) {
//        UserDto userProfile = rest.getForObject(apiUrl+"/findbyid/"+userId, UserDto.class);
//        model.addAttribute("userId", userId);
//        model.addAttribute("userProfile", userProfile);
//        return "admin/staff_profile";
//    }

    

}

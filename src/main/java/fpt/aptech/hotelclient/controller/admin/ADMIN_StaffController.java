 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package fpt.aptech.hotelclient.controller.admin;

import fpt.aptech.hotelclient.dto.UserDto;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author TuanNguyen
 */
@Controller
@RequestMapping("/client/admin/staffcontroller")
public class ADMIN_StaffController {
    String user_api_url = "http://localhost:9999/api/users";
    
    RestTemplate _restTemplate = new RestTemplate();
    
    @GetMapping("/all")
    public String allStaffPage(Model model) {
        List<UserDto> allStaff = _restTemplate.getForObject(user_api_url+"/allstaff", List.class);
        model.addAttribute("allStaff", allStaff);
        return "admin/account/staff_index";
    }
    
    @GetMapping("/gotocreate")
    public String createStaffPage(Model model) {
        UserDto newStaff = new UserDto();
        newStaff.setRole_id(2);
        model.addAttribute("newStaff", newStaff);
        return "admin/account/staff_create";
    }
    
    @PostMapping("/create")
    public String createStaff(Model model , @ModelAttribute("newStaff") @Valid UserDto newStaff , BindingResult bdResult) {
        if(bdResult.hasErrors()) {
            return "admin/account/staff_create";
        }
        
        UserDto checkEmailExist = _restTemplate.postForObject(user_api_url+"/createnewstaff", newStaff , UserDto.class);
        
        if(checkEmailExist == null) {
            model.addAttribute("emailErrMessage", "This email already Exist!!!");
            return "admin/account/staff_create";
        }
        else {
            return "redirect:http://localhost:8888/client/admin/staffcontroller/all";
        }
    }
    
    @GetMapping("/gotoupdate/{id}")
    public String updateStaffPage(Model model , @PathVariable("id") int id) {
        UserDto updateStaffInfo = _restTemplate.getForObject(user_api_url+"/findbyid/"+id, UserDto.class);
        model.addAttribute("updateStaff", updateStaffInfo);
        return "admin/account/staff_update";
    }
    
    @PostMapping("/update")
    public String updateStaffInformation(Model model , @ModelAttribute("updateStaff") @Valid UserDto updateStaff , BindingResult bdResult) {
        _restTemplate.put(user_api_url+"/update/" + updateStaff.getId(), updateStaff);
        
        return "redirect:http://localhost:8888/client/admin/staffcontroller/all";
    }
     
    @GetMapping("/activeuser/{id}")
    public String activeUser(Model model , @PathVariable("id") int id) {
        UserDto userDtoInfo = _restTemplate.getForObject(user_api_url + "/findbyid/" + id , UserDto.class);
        userDtoInfo.setActive(true);
        
        _restTemplate.put(user_api_url + "/update", userDtoInfo);
        
        return "redirect:http://localhost:8888/client/admin/staffcontroller/all";
    }
    
    @GetMapping("/disableuser/{id}")
    public String disableUser(Model model , @PathVariable("id") int id) {
        UserDto userDtoInfo = _restTemplate.getForObject(user_api_url+"/findbyid/"+id , UserDto.class);
        userDtoInfo.setActive(false);
        
        _restTemplate.put(user_api_url+"/update", userDtoInfo);
        
        return "redirect:http://localhost:8888/client/admin/staffcontroller/all";
    }
}

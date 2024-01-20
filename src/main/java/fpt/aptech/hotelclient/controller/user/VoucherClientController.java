package fpt.aptech.hotelclient.controller.user;

import fpt.aptech.hotelclient.dto.VoucherDto;
import java.time.LocalDate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class VoucherClientController {

    RestTemplate rest = new RestTemplate();
    private final String apiUrl = "http://localhost:9999/api/vouchercontroller";

    @GetMapping("/controller/voucher")
    public String index(Model model) {
        ResponseEntity<VoucherDto[]> response = rest.getForEntity(apiUrl + "/all", VoucherDto[].class);
        List<VoucherDto> voucher = Arrays.asList(response.getBody());
        model.addAttribute("vlist", voucher);
        return "/admin/voucher/Vindex";
    }

    @GetMapping("/controller/voucherCreate")
    public String create(Model model) {
        model.addAttribute("newVoucher", new VoucherDto());
        return "admin/voucher/voucherCreate";
    }

    @PostMapping("/controller/voucher/create")
public String createVoucher(@ModelAttribute("newVoucher") VoucherDto voucherDto) {
    LocalDate currentDate = LocalDate.now();
    if (voucherDto.getExpirationDate().isBefore(currentDate)) {
        return "redirect:/admin/voucher/voucherCreate?error=pastDate";
    }
    ResponseEntity<VoucherDto> response = rest.postForEntity(apiUrl + "/create", voucherDto, VoucherDto.class);
    return "redirect:/controller/voucher";
}


    @GetMapping("/controller/voucher/delete/{id}")
    public String deleteVoucher(Model model, @PathVariable int id) {
        rest.delete(apiUrl + "/delete/{id}", id);
        return "redirect:/controller/voucher";
    }

    @GetMapping("/controller/voucher/search")
    public String searchVoucherByTitle(Model model, @RequestParam String title) {
        ResponseEntity<VoucherDto[]> response = rest.getForEntity(apiUrl + "/search?title=" + title, VoucherDto[].class);
        List<VoucherDto> voucher = Arrays.asList(response.getBody());
        model.addAttribute("vlist", voucher);
        return "/admin/voucher/Vindex";
    }

}

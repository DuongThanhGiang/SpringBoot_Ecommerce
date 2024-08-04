package com.springboot.bootstrap.controller.phieugiamgiacontroller;

import com.springboot.bootstrap.entity.PhieuGiamGia;
import com.springboot.bootstrap.repository.PhieuGiamGiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.sql.Date;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/voucher")
public class PhieuGiamGiaController {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    PhieuGiamGiaRepository phieuGiamGiaRepository;


    @GetMapping()
    public String getAll(@RequestParam("p") Optional<Integer> p, Model model){
        Page<PhieuGiamGia> list=phieuGiamGiaRepository.findAll(PageRequest.of(p.orElse(0),5));
        model.addAttribute("list", list);
        return "/pages/voucher";
    }

    @GetMapping("/search")
    public String search(@RequestParam("p") Optional<Integer> page,
                         @RequestParam(value = "keyword", required = false) String keyword,
                         @RequestParam(value = "trangThaiSearch", required = false) Integer trangThai, Model model) {
        int currentPage = page.orElse(0);
        int pageSize = 5;
        if (currentPage < 0) {
            return "redirect:/voucher?p=0";
        }
        Page<PhieuGiamGia> list = phieuGiamGiaRepository.findAll(PageRequest.of(currentPage, pageSize));

        if (keyword != null && !keyword.isEmpty()) {
            list = phieuGiamGiaRepository.searchCodeOrName(keyword, PageRequest.of(currentPage, pageSize));
        }
        if (trangThai != null) {
            list = phieuGiamGiaRepository.searchTrangThai(trangThai, PageRequest.of(currentPage, pageSize));
        }
        if (keyword != null && !keyword.isEmpty() && trangThai != null){
            list = phieuGiamGiaRepository.searchCodeOrNameAndTrangThai(keyword, trangThai, PageRequest.of(currentPage, pageSize));
        }
        model.addAttribute("list", list);
        return "/pages/voucher";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("voucher", new PhieuGiamGia());
        return "/pages/voucher-add";
    }

    @PostMapping("/add")
    public String addVoucher(@ModelAttribute PhieuGiamGia phieuGiamGia, BindingResult bindingResult) {
        Date date=new Date(System.currentTimeMillis());
        phieuGiamGia.setTrangThai(1);
        phieuGiamGia.setTaoLuc(date);
        if (phieuGiamGia.getGiaTriGiamToiDa()==null){
            phieuGiamGia.setGiaTriGiamToiDa((double) 0);
        }
        phieuGiamGiaRepository.save(phieuGiamGia);
        return "redirect:/voucher";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") UUID id, Model model) {
        PhieuGiamGia phieuGiamGia = phieuGiamGiaRepository.findById(id).orElse(null);
        model.addAttribute("phieuGiamGia", phieuGiamGia);
        return "/pages/voucher-detail";
    }

    @PostMapping("/edit/{id}")
    public String editPhieuGiamGia(@PathVariable("id") UUID id, @ModelAttribute PhieuGiamGia phieuGiamGia) {
        Date date = new Date(System.currentTimeMillis());
//        if(phieuGiamGia.getTrangThai()!=4){
//            if (phieuGiamGia.getNgayKetThuc().before(date)) {
//                phieuGiamGia.setTrangThai(2);
//            } else if (phieuGiamGia.getSoLuong() == 0) {
//                phieuGiamGia.setTrangThai(3);
//            }
//        }
        phieuGiamGia.setSuaLuc(date);

        phieuGiamGiaRepository.save(phieuGiamGia);
        return "redirect:/voucher/edit/" + id;
    }

    @PostMapping("/delete/{id}")
    public String softDetete(@PathVariable("id") UUID id) {
        PhieuGiamGia phieuGiamGia = phieuGiamGiaRepository.findById(id).orElse(null);
        if (phieuGiamGia != null) {
            phieuGiamGia.setTrangThai(phieuGiamGia.getTrangThai() == 4 ? 1 : 4);
            phieuGiamGiaRepository.save(phieuGiamGia);
        }
        return "redirect:/voucher";
    }
}

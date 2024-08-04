package com.springboot.bootstrap.controller.sanphamcontroller;

import com.springboot.bootstrap.entity.KichThuoc;
import com.springboot.bootstrap.service.KichThuocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Controller
@RequestMapping("/kich_thuoc")
public class KichThuocController {
    @Autowired
    private KichThuocService kichThuocService;

    @GetMapping("")
    public String getAll(@RequestParam("p") Optional<Integer> p, Model model) {
        Page<KichThuoc> listKT = kichThuocService.getAll(PageRequest.of(p.orElse(0), 5));
        model.addAttribute("listKT", listKT);
        return "/pages/kich_thuoc";
    }

    @GetMapping("/viewOne/")
    @ResponseBody
    public KichThuoc viewUpdate(String id) {

        return kichThuocService.getOne(id);
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("kta") KichThuoc kichThuoc,
                      @RequestParam("ten") String ten,
                      @RequestParam("trangThai") String trangThai,
                      @RequestParam("p") Optional<Integer> p, Model model) {
        kichThuoc = KichThuoc.builder().ma(kichThuocService.generateMaKT()).ten(ten).trangThai(Integer.parseInt(trangThai)).build();
        kichThuocService.add(kichThuoc);
        Page<KichThuoc> listKT = kichThuocService.getAll(PageRequest.of(p.orElse(0), 5));
        model.addAttribute("listKT", listKT);
        return "redirect:/kich_thuoc";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("ktu") KichThuoc kichThuoc,
                         @RequestParam("id") String id,
                         @RequestParam("ma") String ma,
                         @RequestParam("ten") String ten,
                         @RequestParam("trangThai") String trangThai,
                         @RequestParam("p") Optional<Integer> p, Model model) {
        kichThuoc = KichThuoc.builder().ma(ma).ten(ten).trangThai(Integer.parseInt(trangThai)).build();
        kichThuocService.update(kichThuoc,id);
        Page<KichThuoc> listKT = kichThuocService.getAll(PageRequest.of(p.orElse(0), 5));
        model.addAttribute("listKT", listKT);
        return "redirect:/kich_thuoc";
    }
    @GetMapping("/search")
    public String search(@RequestParam("p") Optional<Integer> page,
                         @RequestParam(value = "keyword", required = false) String keyword,
                         @RequestParam(value = "trangThaiSearch", required = false) String trangThai,

                         Model model) {
        int currentPage = page.orElse(0);
        int pageSize = 5;
        if (currentPage < 0) {
            return "redirect:/kich_thuoc?p=0";
        }
        Page<KichThuoc> listKT = null;



        if (keyword != null && !keyword.isEmpty()) {
            listKT = kichThuocService.searchCodeOrName(keyword, PageRequest.of(currentPage, pageSize));
        } else if (trangThai != null && !trangThai.isEmpty()) {

            listKT = kichThuocService.searchTrangThai(Integer.parseInt(trangThai), PageRequest.of(currentPage, pageSize));

        }  else {
            listKT = kichThuocService.getAll(PageRequest.of(currentPage, pageSize));
        }


        model.addAttribute("listKT", listKT);




        return "/pages/kich_thuoc";
    }
}

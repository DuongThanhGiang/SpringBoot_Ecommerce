package com.springboot.bootstrap.controller.sanphamcontroller;

import com.springboot.bootstrap.entity.DanhMuc;
import com.springboot.bootstrap.service.DanhMucService;
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

@RequestMapping("/danh_muc")
@Controller
public class DanhMucController {
    @Autowired
    private DanhMucService danhMucService;

    @GetMapping("")
    public String getAll(@RequestParam("p") Optional<Integer> p, Model model) {
        Page<DanhMuc> listDM = danhMucService.getAll(PageRequest.of(p.orElse(0), 5));
        model.addAttribute("listDM", listDM);
        return "/pages/danh_muc";
    }

    @GetMapping("/viewOne/")
    @ResponseBody
    public DanhMuc viewUpdate(String id) {

        return danhMucService.getOne(id);
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("dma") DanhMuc danhMuc,
                      @RequestParam("ten") String ten,
                      @RequestParam("trangThai") String trangThai,
                      @RequestParam("p") Optional<Integer> p, Model model) {
        danhMuc = DanhMuc.builder().ten(ten).trangThai(Integer.parseInt(trangThai)).build();
        danhMucService.add(danhMuc);
        Page<DanhMuc> listDM = danhMucService.getAll(PageRequest.of(p.orElse(0), 5));
        model.addAttribute("listDM", listDM);
        return "redirect:/danh_muc";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("dmu") DanhMuc danhMuc,
                         @RequestParam("id") String id,
                         @RequestParam("ma") String ma,
                         @RequestParam("ten") String ten,
                         @RequestParam("trangThai") String trangThai,
                         @RequestParam("p") Optional<Integer> p, Model model) {
        danhMuc = DanhMuc.builder().ma(ma).ten(ten).trangThai(Integer.parseInt(trangThai)).build();
        danhMucService.update(danhMuc, id);
        Page<DanhMuc> listDM = danhMucService.getAll(PageRequest.of(p.orElse(0), 5));
        model.addAttribute("listDM", listDM);
        return "redirect:/danh_muc";
    }


    @GetMapping("/search")
    public String search(@RequestParam("p") Optional<Integer> page,
                         @RequestParam(value = "keyword", required = false) String keyword,
                         @RequestParam(value = "trangThaiSearch", required = false) String trangThai,

                         Model model) {
        int currentPage = page.orElse(0);
        int pageSize = 5;
        if (currentPage < 0) {
            return "redirect:/mau_sac?p=0";
        }
        Page<DanhMuc> listDM = null;



        if (keyword != null && !keyword.isEmpty()) {
            listDM = danhMucService.searchCodeOrName(keyword, PageRequest.of(currentPage, pageSize));
        } else if (trangThai != null && !trangThai.isEmpty()) {

            listDM = danhMucService.searchTrangThai(Integer.parseInt(trangThai), PageRequest.of(currentPage, pageSize));

        }  else {
            listDM = danhMucService.getAll(PageRequest.of(currentPage, pageSize));
        }


        model.addAttribute("listDM", listDM);




        return "/pages/danh_muc";
    }

}

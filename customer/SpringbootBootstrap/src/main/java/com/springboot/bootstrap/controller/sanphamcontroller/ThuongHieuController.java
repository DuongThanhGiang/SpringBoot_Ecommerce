package com.springboot.bootstrap.controller.sanphamcontroller;

import com.springboot.bootstrap.entity.ThuongHieu;
import com.springboot.bootstrap.service.ThuongHieuService;
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
@RequestMapping("/thuong_hieu")
@Controller
public class ThuongHieuController {
    @Autowired
    private ThuongHieuService thuongHieuService;

    @GetMapping("")
    public String getAll(@RequestParam("p") Optional<Integer> p, Model model){
        Page<ThuongHieu> listTH=thuongHieuService.getAll(PageRequest.of(p.orElse(0), 5));
        model.addAttribute("listTH",listTH);
        return "/pages/thuong_hieu";
    }
    @GetMapping("/viewOne/")
    @ResponseBody
    public ThuongHieu viewUpdate(String id) {
        return thuongHieuService.getOne(id);
    }
    @PostMapping("/add")
    public String add(@ModelAttribute("tha") ThuongHieu thuongHieu,
                      @RequestParam("ten") String ten,
                      @RequestParam("trangThai") String trangThai,
                      @RequestParam("p") Optional<Integer> p, Model model) {
        thuongHieu = ThuongHieu.builder().ma(thuongHieuService.generateMaTH()).ten(ten).trangThai(Integer.parseInt(trangThai)).build();
        thuongHieuService.add(thuongHieu);
        Page<ThuongHieu> listTH = thuongHieuService.getAll(PageRequest.of(p.orElse(0), 5));
        model.addAttribute("listTH", listTH);
        return "redirect:/thuong_hieu";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("thu") ThuongHieu thuongHieu,
                         @RequestParam("id") String id,
                         @RequestParam("ma") String ma,
                         @RequestParam("ten") String ten,
                         @RequestParam("trangThai") String trangThai,
                         @RequestParam("p") Optional<Integer> p, Model model) {
        thuongHieu = ThuongHieu.builder().ma(ma).ten(ten).trangThai(Integer.parseInt(trangThai)).build();
        thuongHieuService.update(thuongHieu, id);
        Page<ThuongHieu> listTH = thuongHieuService.getAll(PageRequest.of(p.orElse(0), 5));
        model.addAttribute("listTH", listTH);
        return "redirect:/thuong_hieu";
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
        Page<ThuongHieu> listTH = null;



        if (keyword != null && !keyword.isEmpty()) {
            listTH = thuongHieuService.searchCodeOrName(keyword, PageRequest.of(currentPage, pageSize));
        } else if (trangThai != null && !trangThai.isEmpty()) {

            listTH = thuongHieuService.searchTrangThai(Integer.parseInt(trangThai), PageRequest.of(currentPage, pageSize));

        }  else {
            listTH = thuongHieuService.getAll(PageRequest.of(currentPage, pageSize));
        }


        model.addAttribute("listTH", listTH);




        return "/pages/thuong_hieu";
    }
}

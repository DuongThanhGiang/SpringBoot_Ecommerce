package com.springboot.bootstrap.controller.sanphamcontroller;


import com.springboot.bootstrap.entity.MauSac;
import com.springboot.bootstrap.service.MauSacService;
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

@RequestMapping("/mau_sac")
@Controller
public class MauSacController {
    @Autowired
    private MauSacService mauSacService;

    @GetMapping("")
    public String getAll(@RequestParam("p") Optional<Integer> p, Model model) {
        Page<MauSac> listMS = mauSacService.getAll(PageRequest.of(p.orElse(0), 5));
        model.addAttribute("listMS", listMS);
        return "/pages/mau_sac";
    }
    @GetMapping("/viewOne/")
    @ResponseBody
    public MauSac viewUpdate(String id) {

        return mauSacService.getOne(id);
    }
    @PostMapping("/add")
    public String add(@ModelAttribute("msa") MauSac mauSac,
                      @RequestParam("ten") String ten,
                      @RequestParam("trangThai") String trangThai,
                      @RequestParam("p") Optional<Integer> p, Model model) {
        mauSac = MauSac.builder().ma(mauSacService.generateMaMS()).ten(ten).trangThai(Integer.parseInt(trangThai)).build();
        mauSacService.add(mauSac);
        Page<MauSac> listMS = mauSacService.getAll(PageRequest.of(p.orElse(0), 5));
        model.addAttribute("listMS", listMS);
        return "redirect:/mau_sac";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("msu") MauSac mauSac,
                         @RequestParam("id") String id,
                         @RequestParam("ma") String ma,
                         @RequestParam("ten") String ten,
                         @RequestParam("trangThai") String trangThai,
                         @RequestParam("p") Optional<Integer> p, Model model) {
        mauSac = MauSac.builder().ma(ma).ten(ten).trangThai(Integer.parseInt(trangThai)).build();
        mauSacService.update(mauSac, id);
        Page<MauSac> listMS = mauSacService.getAll(PageRequest.of(p.orElse(0), 5));
        model.addAttribute("listMS", listMS);
        return "redirect:/mau_sac";
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
        Page<MauSac> listMS = null;



        if (keyword != null && !keyword.isEmpty()) {
            listMS = mauSacService.searchCodeOrName(keyword, PageRequest.of(currentPage, pageSize));
        } else if (trangThai != null && !trangThai.isEmpty()) {

            listMS = mauSacService.searchTrangThai(Integer.parseInt(trangThai), PageRequest.of(currentPage, pageSize));

        }  else {
            listMS = mauSacService.getAll(PageRequest.of(currentPage, pageSize));
        }


        model.addAttribute("listMS", listMS);




        return "/pages/mau_sac";
    }

}

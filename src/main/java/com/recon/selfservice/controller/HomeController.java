package com.recon.selfservice.controller;

import com.recon.selfservice.service.ReconService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ReconService reconService;

    @GetMapping("/")
    public String index(@RequestParam(value = "query", required = false) String query, Model model) {
        model.addAttribute("recons", reconService.search(query));
        model.addAttribute("query", query);
        return "index";
    }
}

package com.recon.selfservice.controller;

import com.recon.selfservice.model.entity.Action;
import com.recon.selfservice.model.entity.Recon;
import com.recon.selfservice.model.enums.ActionType;
import com.recon.selfservice.service.ReconService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ReconService reconService;

    @GetMapping("/recons")
    public String listRecons(Model model) {
        model.addAttribute("recons", reconService.findAll());
        return "admin/recons";
    }

    @GetMapping("/recons/new")
    public String newRecon(Model model) {
        Recon recon = new Recon();
        recon.setAction(new Action());
        model.addAttribute("recon", recon);
        model.addAttribute("actionTypes", ActionType.values());
        return "admin/recon-form";
    }

    @GetMapping("/recons/edit/{id}")
    public String editRecon(@PathVariable Long id, Model model) {
        model.addAttribute("recon", reconService.findById(id));
        model.addAttribute("actionTypes", ActionType.values());
        return "admin/recon-form";
    }

    @PostMapping("/recons/save")
    public String saveRecon(@ModelAttribute Recon recon, RedirectAttributes redirectAttributes) {
        reconService.save(recon);
        redirectAttributes.addFlashAttribute("successMessage", "Recon saved successfully!");
        return "redirect:/admin/recons";
    }

    @GetMapping("/recons/delete/{id}")
    public String deleteRecon(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        reconService.delete(id);
        redirectAttributes.addFlashAttribute("successMessage", "Recon deleted successfully!");
        return "redirect:/admin/recons";
    }
}

package com.recon.selfservice.controller;

import com.recon.selfservice.model.dto.ExecutionRequestDTO;
import com.recon.selfservice.model.entity.Execution;
import com.recon.selfservice.model.entity.Recon;
import com.recon.selfservice.service.ReconService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/recons")
@RequiredArgsConstructor
public class ReconController {

    private final ReconService reconService;

    @GetMapping("/{id}/execute")
    public String showExecutionPage(@PathVariable Long id, Model model) {
        Recon recon = reconService.findById(id);
        model.addAttribute("recon", recon);
        
        ExecutionRequestDTO request = new ExecutionRequestDTO();
        request.setReconId(id);
        request.setExecutionDate(LocalDate.now());
        model.addAttribute("executionRequest", request);
        
        return "recon/execute";
    }

    @PostMapping("/execute")
    public String executeRecon(@ModelAttribute ExecutionRequestDTO request, RedirectAttributes redirectAttributes) {
        try {
            Execution execution = reconService.executeRecon(request.getReconId(), request.getExecutionDate());
            redirectAttributes.addFlashAttribute("successMessage", "Recon executed successfully!");
            return "redirect:/executions/" + execution.getId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Execution failed: " + e.getMessage());
            return "redirect:/recons/" + request.getReconId() + "/execute";
        }
    }
}

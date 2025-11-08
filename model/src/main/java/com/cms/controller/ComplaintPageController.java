package com.cms.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cms.model.Complaint;
import com.cms.service.ComplaintService;

import jakarta.validation.Valid;
@Controller
@RequestMapping("/complaints")
public class ComplaintPageController {
  private final ComplaintService service;
  public ComplaintPageController(ComplaintService service){ this.service = service; }

  @GetMapping
  public String list(@RequestParam(required=false) String status,
                     @RequestParam(required=false) String category,
                     @RequestParam(required=false) String q,
                     Model model) {
    var items = service.listAll(); // keep it simple; or filter in service
    // simple in-memory filters for demo:
    if (status != null && !status.isBlank()) items = items.stream().filter(c -> status.equals(c.getStatus())).toList();
    if (category != null && !category.isBlank()) items = items.stream().filter(c -> category.equalsIgnoreCase(c.getCategory())).toList();
    if (q != null && !q.isBlank()) items = items.stream().filter(c -> c.getTitle().toLowerCase().contains(q.toLowerCase())).toList();

    model.addAttribute("complaints", items);
    model.addAttribute("pageTitle", "Complaints");
    model.addAttribute("content", "complaints :: body");
    return "complaints";
  }

  @GetMapping("/new")
  public String form(Model model){
    model.addAttribute("complaint", new Complaint());
    model.addAttribute("pageTitle", "New Complaint");
    model.addAttribute("content", "complaint-form :: body");
    return "complaint-form";
  }

  @PostMapping
  public String create(@Valid @ModelAttribute Complaint c, BindingResult br, RedirectAttributes ra){
    if (br.hasErrors()) return "complaint-form";
    service.create(c);
    ra.addFlashAttribute("flash", "Complaint created!");
    return "redirect:/complaints";
  }

  @GetMapping("/{id}")
  public String detail(@PathVariable Long id, Model model){
    model.addAttribute("complaint", service.get(id));
    model.addAttribute("pageTitle", "Complaint #" + id);
    model.addAttribute("content", "complaint-detail :: body");
    return "complaint-detail";
  }

  @PostMapping("/{id}/status")
  public String transition(@PathVariable Long id, @RequestParam String status,
                           @RequestParam(required=false) String notes, RedirectAttributes ra){
    service.transition(id, status, notes);
    ra.addFlashAttribute("flash", "Status updated.");
    return "redirect:/complaints/{id}";
  }

  @PostMapping("/{id}/delete")
  public String delete(@PathVariable Long id, RedirectAttributes ra){
    service.delete(id);
    ra.addFlashAttribute("flash", "Complaint deleted.");
    return "redirect:/complaints";
  }
}

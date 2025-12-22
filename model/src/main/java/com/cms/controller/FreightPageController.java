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

import com.cms.model.Freight;
import com.cms.service.FreightService;

import jakarta.validation.Valid;
@Controller
@RequestMapping("/freights")
public class FreightPageController {
  private final FreightService service;
  public FreightPageController(FreightService service){ this.service = service; }

  @GetMapping
  public String list(@RequestParam(required=false) String status,
                     @RequestParam(required=false) String q,
                     Model model) {
    var items = service.listAllEntities();
    // simple in-memory filters for demo:
    if (status != null && !status.isBlank()) items = items.stream().filter(c -> status.equals(c.getStatus())).toList();
    if (q != null && !q.isBlank()) items = items.stream().filter(c -> c.getCustomer().toLowerCase().contains(q.toLowerCase())).toList();

    model.addAttribute("freights", items);
    model.addAttribute("pageTitle", "Freights");
    return "freights";
  }

  @GetMapping("/new")
  public String form(Model model){
    model.addAttribute("freight", new Freight());
    model.addAttribute("pageTitle", "New Freight");
    return "freight-form";
  }

  @PostMapping
  public String create(@Valid @ModelAttribute Freight c, BindingResult br, RedirectAttributes ra){
    if (br.hasErrors()) return "freight-form";
    service.create(c);
    ra.addFlashAttribute("flash", "Freight created!");
    return "redirect:/freights";
  }

  @GetMapping("/{id}")
  public String detail(@PathVariable Long id, Model model){
    model.addAttribute("freight", service.getEntity(id));
    model.addAttribute("pageTitle", "Freight #" + id);
    return "freight-detail";
  }

  @PostMapping("/{id}/status")
  public String transition(@PathVariable Long id, @RequestParam String status,
                           @RequestParam(required=false) String notes, RedirectAttributes ra){
    service.transition(id, status, notes);
    ra.addFlashAttribute("flash", "Status updated.");
    return "redirect:/freights/{id}";
  }

  @PostMapping("/{id}/delete")
  public String delete(@PathVariable Long id, RedirectAttributes ra){
    service.delete(id);
    ra.addFlashAttribute("flash", "Freight deleted.");
    return "redirect:/freights";
  }
}

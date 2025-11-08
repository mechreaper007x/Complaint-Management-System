package com.cms.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cms.model.Complaint;
import com.cms.repository.ComplaintRepository;

@Controller
public class DashboardController {
  private final ComplaintRepository repo;
  public DashboardController(ComplaintRepository repo){ this.repo = repo; }

  @GetMapping("/")
  public String home(){ return "redirect:/dashboard"; }

  @GetMapping("/dashboard")
  public String dashboard(Model m){
    var all = repo.findAll();
    var statusCounts = all.stream().collect(Collectors.groupingBy(Complaint::getStatus, Collectors.counting()));
    var categoryCounts = all.stream().collect(Collectors.groupingBy(Complaint::getCategory, Collectors.counting()));
    List<Map<String,Object>> kpis = List.of(
      Map.of("label","Open", "value", statusCounts.getOrDefault("OPEN",0L)),
      Map.of("label","In Progress", "value", statusCounts.getOrDefault("IN_PROGRESS",0L)),
      Map.of("label","Resolved", "value", statusCounts.getOrDefault("RESOLVED",0L)),
      Map.of("label","Rejected", "value", statusCounts.getOrDefault("REJECTED",0L))
    );
    m.addAttribute("kpis", kpis);
    m.addAttribute("statusCounts", statusCounts);
    m.addAttribute("categoryCounts", categoryCounts);
    m.addAttribute("pageTitle", "Dashboard");
    m.addAttribute("content", "dashboard :: body");
    return "dashboard";
  }
}

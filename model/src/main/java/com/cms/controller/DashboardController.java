package com.cms.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cms.model.Freight;
import com.cms.repository.FreightRepository;

@Controller
public class DashboardController {
  private final FreightRepository repo;
  public DashboardController(FreightRepository repo){ this.repo = repo; }

  @GetMapping("/")
  public String home(){ return "redirect:/dashboard"; }

  @GetMapping("/dashboard")
  public String dashboard(Model m){
    var all = repo.findAll();
    var statusCounts = all.stream().collect(Collectors.groupingBy(Freight::getStatus, Collectors.counting()));
    var customerCounts = all.stream().collect(Collectors.groupingBy(Freight::getCustomer, Collectors.counting()));
    List<Map<String,Object>> kpis = List.of(
      Map.of("label","In Transit", "value", statusCounts.getOrDefault("IN_TRANSIT",0L)),
      Map.of("label","Delivered", "value", statusCounts.getOrDefault("DELIVERED",0L))
    );
    m.addAttribute("kpis", kpis);
    m.addAttribute("statusCounts", statusCounts);
    m.addAttribute("customerCounts", customerCounts);
    m.addAttribute("pageTitle", "Dashboard");
    return "dashboard";
  }
}


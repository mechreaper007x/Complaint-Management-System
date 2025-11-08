// package com.cms.controller;

// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.validation.BindingResult;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.ModelAttribute;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;

// import com.cms.model.Complaint;
// import com.cms.service.ComplaintService;

// import jakarta.validation.Valid;
// import lombok.RequiredArgsConstructor;

// @Controller
// @RequestMapping("/complaints")
// @RequiredArgsConstructor
// public class ComplaintController {
//   private final ComplaintService service = null;

//   @GetMapping
//   public String list(@RequestParam(required=false) String status, Model model){
//     model.addAttribute("complaints", status==null ? service.listAll() : service.listByStatus(status));
//     return "complaints";
//   }

//   @GetMapping("/new")
//   public String form(Model model){
//     model.addAttribute("complaint", new Complaint());
//     return "complaint-form";
//   }

//   @PostMapping
//   public String create(@Valid @ModelAttribute Complaint c, BindingResult br){
//     if (br.hasErrors()) return "complaint-form";
//     service.create(c);
//     return "redirect:/complaints";
//   }

//   @GetMapping("/{id}")
//   public String detail(@PathVariable Long id, Model model){
//     model.addAttribute("complaint", service.get(id));
//     return "complaint-detail";
//   }

//   @PostMapping("/{id}/status")
//   public String transition(@PathVariable Long id,
//                            @RequestParam String status,
//                            @RequestParam(required=false) String notes){
//     service.transition(id, status, notes);
//     return "redirect:/complaints/{id}";
//   }

//   @PostMapping("/{id}/delete")
//   public String delete(@PathVariable Long id){
//     service.delete(id);
//     return "redirect:/complaints";
//   }
// }

package ru.job4j.accidents.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.*;

import java.util.NoSuchElementException;
import java.util.function.Consumer;

@Controller
public class AccidentController {

    private final AccidentDataService service;
    private final AccidentTypeDataService typeService;
    private final RuleDataService ruleService;

    public AccidentController(AccidentDataService service,
                              AccidentTypeDataService typeService,
                              RuleDataService ruleService) {
        this.service = service;
        this.typeService = typeService;
        this.ruleService = ruleService;
    }

    @GetMapping("/index")
    public String index(Model model, @CurrentSecurityContext(expression = "authentication")
            Authentication authentication) {
        model.addAttribute("user", authentication.getPrincipal());
        model.addAttribute("accidents", service.getAccidents());
        return "index";
    }

    @GetMapping("/createAccidentForm")
    public String createAccident(Model model) {
        model.addAttribute(new Accident());
        fillModel(model);
        return "createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident, Model model) {
        takeAction(accident, act -> service.create(accident));
        model.addAttribute("user",
                SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return "redirect:/index";
    }

    @GetMapping("/updateAccidentForm")
    public String updateAccident(Model model,
                                 @RequestParam("id") int id) {
        model.addAttribute("accident",
                service.get(id).orElseThrow(NoSuchElementException::new));
        fillModel(model);
        return "editAccident";
    }

    @PostMapping("/updateAccident")
    public String update(@ModelAttribute Accident accident, Model model) {
        model.addAttribute("user",
                SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        takeAction(accident, act -> service.update(accident));
        return "redirect:/index";
    }

    private void takeAction(Accident accident, Consumer<Accident> action) {
        accident.setType(typeService.get(accident.getType().getId())
                .orElseThrow(NoSuchElementException::new));
        ruleService.setRules(accident);
        action.accept(accident);
    }

    private void fillModel(Model model) {
        model.addAttribute("user",
                SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        model.addAttribute("types", typeService.getTypes());
        model.addAttribute("rules", ruleService.getRules());
    }
}
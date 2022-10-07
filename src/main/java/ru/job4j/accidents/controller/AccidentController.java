package ru.job4j.accidents.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.AccidentService;
import ru.job4j.accidents.service.AccidentTypeService;
import ru.job4j.accidents.service.RuleService;

import java.util.NoSuchElementException;
import java.util.function.Consumer;

@Controller
public class AccidentController {

    private final AccidentService service;
    private final AccidentTypeService typeService;
    private final RuleService ruleService;

    public AccidentController(AccidentService service,
                              AccidentTypeService typeService, RuleService ruleService) {
        this.service = service;
        this.typeService = typeService;
        this.ruleService = ruleService;
    }

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("user", "Daniil");
        model.addAttribute("accidents", service.getAccidents());
        return "index";
    }

    @GetMapping("/createAccidentForm")
    public String createAccident(Model model) {
        model.addAttribute(new Accident());
        model.addAttribute("types", typeService.getTypes());
        model.addAttribute("rules", ruleService.getRules());
        return "createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident) {
        takeAction(accident, act -> service.put(accident));
        return "redirect:/index";
    }

    @GetMapping("/updateAccidentForm")
    public String updateAccident(Model model,
                                 @RequestParam("id") int id) {
        model.addAttribute("accident",
                service.get(id).orElseThrow(NoSuchElementException::new));
        model.addAttribute("types", typeService.getTypes());
        model.addAttribute("rules", ruleService.getRules());
        return "updateAccident";
    }

    @PostMapping("/updateAccident")
    public String update(@ModelAttribute Accident accident) {
        takeAction(accident, act -> service.update(accident));
        return "redirect:/index";
    }

    private void takeAction(Accident accident, Consumer<Accident> action) {
        accident.setType(typeService.get(accident.getType().getId())
                .orElseThrow(NoSuchElementException::new));
        ruleService.setRules(accident);
        action.accept(accident);
    }
}
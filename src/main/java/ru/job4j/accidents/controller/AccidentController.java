package ru.job4j.accidents.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.AccidentService;

@Controller
public class AccidentController {

    private final AccidentService service;

    public AccidentController(AccidentService service) {
        this.service = service;
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
        return "createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident) {
        service.put(accident);
        return "redirect:/index";
    }

    @GetMapping("/updateAccidentForm/{id}")
    public String updateAccident(Model model,
                                 @PathVariable("id") int id) {
        model.addAttribute("accident",
                service.get(id).orElseGet(Accident::new));
        return "updateAccident";
    }

    @PostMapping("/updateAccident")
    public String update(@ModelAttribute Accident accident) {
        service.update(accident);
        return "redirect:/index";
    }
}

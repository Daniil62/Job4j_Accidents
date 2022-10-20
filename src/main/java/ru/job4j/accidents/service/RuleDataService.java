package ru.job4j.accidents.service;

import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleDataStore;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RuleDataService {

    private final RuleDataStore store;

    public RuleDataService(RuleDataStore store) {
        this.store = store;
    }

    public List<Rule> getRules() {
        List<Rule> result = new ArrayList<>();
        for (Rule rule : store.findAll()) {
            result.add(rule);
        }
        return result;
    }

    public Optional<Rule> get(int id) {
        return store.findById(id);
    }

    public void setRules(Accident accident) {
        if (accident != null) {
            List<Integer> ids = accident.getRules().stream().map(Rule::getId).toList();
            List<Rule> rules = new ArrayList<>();
            for (Rule rule : store.findAllById(ids)) {
                rules.add(rule);
            }
            accident.setRules(rules);
        }
    }
}

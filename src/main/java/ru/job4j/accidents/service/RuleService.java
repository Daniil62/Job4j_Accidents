package ru.job4j.accidents.service;

import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleMemStore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class RuleService {

    private final RuleMemStore store;

    public RuleService(RuleMemStore store) {
        this.store = store;
    }

    public Optional<Rule> get(int id) {
        return store.get(id);
    }

    public Rule put(Rule rule) {
        return store.put(rule);
    }

    public Collection<Rule> getRules() {
        return store.getRules();
    }

    public boolean update(Rule rule) {
        return store.update(rule);
    }

    public void setRules(Accident accident) {
        if (accident != null && !accident.getRules().isEmpty()) {
            final List<Integer> ids = new ArrayList<>();
            for (Rule rule : accident.getRules()) {
                ids.add(rule.getId());
            }
            accident.setRules(store.getByIds(ids));
        }
    }
}

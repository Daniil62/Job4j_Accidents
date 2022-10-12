package ru.job4j.accidents.service;

import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleHibernateStore;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class RuleHibernateService {

    private final RuleHibernateStore store;

    public RuleHibernateService(RuleHibernateStore store) {
        this.store = store;
    }

    public List<Rule> getRules() {
        return store.getRules();
    }

    public Optional<Rule> get(int id) {
        return store.get(id);
    }

    public void setRules(Accident item) {
        if (item != null) {
            Set<Rule> rules = item.getRules();
            if (!rules.isEmpty()) {
                final List<Integer> ids = new ArrayList<>();
                for (Rule category : rules) {
                    ids.add(category.getId());
                }
                item.setRules(store.findByIds(ids));
            }
        }
    }
}

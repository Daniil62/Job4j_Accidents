package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class RuleMemStore {

    private final Map<Integer, Rule> rules = new HashMap<>();
    private final AtomicInteger size = new AtomicInteger(rules.size());

    public RuleMemStore() {
        rules.put(1, new Rule(1, "Статья 1"));
        rules.put(2, new Rule(2, "Статья 2"));
        rules.put(3, new Rule(3, "Статья 3"));
    }

    public Optional<Rule> get(int id) {
        return Optional.of(rules.get(id));
    }

    public List<Rule> getByIds(List<Integer> ids) {
        return ids.stream().map(rules::get).collect(Collectors.toList());
    }

    public Rule put(Rule rule) {
        int id = size.incrementAndGet();
        rule.setId(id);
        rules.put(id, rule);
        return rule;
    }

    public Collection<Rule> getRules() {
        return rules.values();
    }

    public boolean update(Rule rule) {
        return rules.replace(rule.getId(), rule) != null;
    }
}
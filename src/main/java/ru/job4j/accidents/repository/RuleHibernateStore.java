package ru.job4j.accidents.repository;

import org.hibernate.SessionFactory;
import ru.job4j.accidents.model.Rule;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class RuleHibernateStore {

    private final Performer performer;

    public RuleHibernateStore(SessionFactory factory) {
        this.performer = new QueryPerformer(factory);
    }

    public List<Rule> getRules() {
        return performer.perform(session -> session.createQuery("FROM Rule").list());
    }

    public Optional<Rule> get(int id) {
        Rule rule = performer.perform(session -> session.get(Rule.class, id));
        return rule != null ? Optional.of(rule) : Optional.empty();
    }

    public List<Rule> findByIds(final List<Integer> ids) {
        return performer.perform(session -> ids.stream()
                .map(id -> session.get(Rule.class, id))
                .filter(Objects::nonNull)
                .collect(Collectors.toList()));
    }
}

package ru.job4j.accidents.repository;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.List;
import java.util.Optional;

@Repository
public class AccidentTypeHibernateStore {

    private final Performer performer;

    public AccidentTypeHibernateStore(SessionFactory factory) {
        this.performer = new QueryPerformer(factory);
    }

    public List<AccidentType> getTypes() {
        return performer.perform(session -> session.createQuery("FROM AccidentType").list());
    }

    public Optional<AccidentType> get(int id) {
        AccidentType type = performer.perform(session -> session.get(AccidentType.class, id));
        return type != null ? Optional.of(type) : Optional.empty();
    }
}

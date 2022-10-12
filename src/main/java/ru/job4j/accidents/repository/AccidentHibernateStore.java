package ru.job4j.accidents.repository;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.List;
import java.util.Optional;

@Repository
public class AccidentHibernateStore {

    private final Performer performer;

    public AccidentHibernateStore(SessionFactory factory) {
        this.performer = new QueryPerformer(factory);
    }

    public Accident save(Accident accident) {
        return performer.perform(session -> {
            session.persist(accident);
            return accident;
        });
    }

    public List<Accident> getAccidents() {
        return performer.perform(session ->
                session.createQuery("from Accident", Accident.class).list());
    }

    public Optional<Accident> get(int id) {
        Accident accident = performer.perform(session ->
                session.get(Accident.class, id));
        return accident != null ? Optional.of(accident) : Optional.empty();
    }

    public void update(Accident accident) {
        performer.perform(session -> {
            session.update(accident);
            return accident;
        });
    }

    public boolean delete(int id) {
        return performer.perform(session ->
                session.createQuery("DELETE FROM Accident WHERE id = :id")
                .setParameter("id", id).executeUpdate() > 0);
    }
}
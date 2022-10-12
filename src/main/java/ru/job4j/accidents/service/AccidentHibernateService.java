package ru.job4j.accidents.service;

import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentHibernateStore;

import java.util.List;
import java.util.Optional;

@Service
public class AccidentHibernateService {

    private final AccidentHibernateStore store;

    public AccidentHibernateService(AccidentHibernateStore store) {
        this.store = store;
    }

    public void create(Accident accident) {
        store.save(accident);
    }

    public List<Accident> getAccidents() {
        return store.getAccidents();
    }

    public Optional<Accident> get(int id) {
        return store.get(id);
    }

    public void update(Accident accident) {
        store.update(accident);
    }

    public boolean delete(int id) {
        return store.delete(id);
    }
}

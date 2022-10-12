package ru.job4j.accidents.service;

import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentTypeHibernateStore;

import java.util.List;
import java.util.Optional;

@Service
public class AccidentTypeHibernateService {

    private final AccidentTypeHibernateStore store;

    public AccidentTypeHibernateService(AccidentTypeHibernateStore store) {
        this.store = store;
    }

    public List<AccidentType> getTypes() {
        return store.getTypes();
    }

    public Optional<AccidentType> get(int id) {
        return store.get(id);
    }
}

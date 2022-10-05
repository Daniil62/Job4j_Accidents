package ru.job4j.accidents.service;

import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentTypeMemStore;

import java.util.Collection;
import java.util.Optional;

@Service
public class AccidentTypeService {

    private final AccidentTypeMemStore store;

    public AccidentTypeService(AccidentTypeMemStore store) {
        this.store = store;
    }

    public Optional<AccidentType> get(int id) {
        return store.get(id);
    }

    public AccidentType put(AccidentType type) {
        return store.put(type);
    }

    public Collection<AccidentType> getTypes() {
        return store.getTypes();
    }

    public boolean update(AccidentType type) {
        return store.update(type);
    }
}
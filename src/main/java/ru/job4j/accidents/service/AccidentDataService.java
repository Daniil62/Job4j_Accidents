package ru.job4j.accidents.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentDataStore;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccidentDataService {

    private final AccidentDataStore store;

    public AccidentDataService(AccidentDataStore store) {
        this.store = store;
    }

    public void create(Accident accident) {
        store.save(accident);
    }

    public List<Accident> getAccidents() {
        var result = new ArrayList<Accident>();
        for (var accident : store.findAll()) {
            result.add(accident);
        }
        return result;
    }

    public Optional<Accident> get(int id) {
        return store.findById(id);
    }

    public void update(Accident accident) {
        store.save(accident);
    }

    public void delete(int id) {
        store.deleteById(id);
    }
}
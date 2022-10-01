package ru.job4j.accidents.service;

import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentMemStore;

import java.util.List;
import java.util.Optional;

@Service
public class AccidentService {

    private final AccidentMemStore store;

    public AccidentService(AccidentMemStore store) {
        this.store = store;
    }

    public Optional<Accident> get(int id) {
        return store.get(id);
    }

    public Accident put(Accident accident) {
        return store.put(accident);
    }

    public List<Accident> getAccidents() {
        return store.getAccidents().stream().toList();
    }
}

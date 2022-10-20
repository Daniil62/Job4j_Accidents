package ru.job4j.accidents.service;

import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentTypeDataStore;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccidentTypeDataService {

    private final AccidentTypeDataStore store;

    public AccidentTypeDataService(AccidentTypeDataStore store) {
        this.store = store;
    }

    public List<AccidentType> getTypes() {
        List<AccidentType> result = new ArrayList<>();
        for (AccidentType type : store.findAll()) {
            result.add(type);
        }
        return result;
    }

    public Optional<AccidentType> get(int id) {
        return store.findById(id);
    }
}

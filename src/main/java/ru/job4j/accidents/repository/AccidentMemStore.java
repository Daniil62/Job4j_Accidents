package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMemStore {

    private final Map<Integer, Accident> accidents = new HashMap<>();
    private final AtomicInteger size = new AtomicInteger(accidents.size());

    public AccidentMemStore() {
        accidents.put(1, new Accident(1, "A", "A text", "A address"));
        accidents.put(2, new Accident(2, "B", "B text", "B address"));
        accidents.put(3, new Accident(3, "C", "C text", "C address"));
    }

    public Optional<Accident> get(int id) {
        return Optional.of(accidents.get(id));
    }

    public Accident put(Accident accident) {
        int id = size.incrementAndGet();
        accident.setId(id);
        accidents.put(id, accident);
        return accident;
    }

    public Collection<Accident> getAccidents() {
        return accidents.values();
    }

    public boolean update(Accident accident) {
        return accidents.replace(accident.getId(), accident) != null;
    }
}

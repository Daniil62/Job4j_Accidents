package ru.job4j.accidents.service;

import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentJdbcTemplate;

import java.util.Collection;
import java.util.Optional;

@Service
public class AccidentDBService {

    private final AccidentJdbcTemplate accidentsRepository;

    public AccidentDBService(AccidentJdbcTemplate accidentsRepository) {
        this.accidentsRepository = accidentsRepository;
    }

    public Accident put(Accident accident) {
        return accidentsRepository.put(accident);
    }

    public Collection<Accident> getAccidents() {
        return accidentsRepository.getAccidents();
    }

    public Optional<Accident> get(int id) {
        return accidentsRepository.get(id);
    }

    public void update(Accident accident) {
        accidentsRepository.update(accident);
    }
}

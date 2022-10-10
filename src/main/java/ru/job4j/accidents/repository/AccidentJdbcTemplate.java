package ru.job4j.accidents.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class AccidentJdbcTemplate {

    private final JdbcTemplate jdbc;

    public AccidentJdbcTemplate(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public Accident put(Accident accident) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(
                    "insert into accident (name, text, address, type_id)"
                    + " values (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, accident.getName());
            statement.setString(2, accident.getText());
            statement.setString(3, accident.getAddress());
            statement.setInt(4, accident.getType().getId());
            return statement;
        }, keyHolder);
        int id = keyHolder.getKey().intValue();
        accident.setId(id);
        saveRule(accident.getRules(), id);
        return accident;
    }

    public Collection<Accident> getAccidents() {
        return jdbc.query("select * from accidents",
                (rs, row) -> {
            int id = rs.getInt("id");
            Accident accident = new Accident(
                            id,
                            rs.getString("name"),
                            rs.getString("text"),
                            rs.getString("address"),
                            getType(rs.getInt("type_id")));
                    accident.setRules(getRules(id));
                    return accident;
        });
    }

    public Optional<Accident> get(int id) {
        Accident result = jdbc.queryForObject("select * from accidents where id = ?",
                (resultSet, rowNum) -> new Accident(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("text"),
                        resultSet.getString("address"),
                        getType(resultSet.getInt("type_id"))), id);
        boolean isNotNull = result != null;
        if (isNotNull) {
            result.setRules(getRules(id));
        }
        return isNotNull ? Optional.of(result) : Optional.empty();
    }

    public void update(Accident accident) {
        int id = accident.getId();
        jdbc.update("update accidents set "
                + "name = ?, text = ?, address = ?, type_id = ? where id = ?",
                accident.getName(), accident.getText(), accident.getAddress(),
                accident.getType().getId(), id);
        updateType(accident.getType());
        updateAccidentRules(accident.getRules(), id);
    }

    private void saveRule(Collection<Rule> rules, int accidentId) {
        rules.stream().map(Rule::getId).forEach(i -> jdbc.update(
                "insert into accident_rule (rule_id, accident_id) values (?, ?)",
                i, accidentId));
    }

    private AccidentType getType(int typeId) {
        return jdbc.queryForObject("select * from accident_type where id = ?",
                (resultSet, rowNum) -> new AccidentType(
                        resultSet.getInt("id"),
                        resultSet.getString("name")), typeId);
    }

    private List<Integer> getRuleIds(int accidentId) {
        return jdbc.query(
                "select rule_id from accident_rule where accident_id = ?",
                (resultSet, rowNum) -> resultSet.getInt("id"), accidentId);
    }

    private List<Rule> getRules(int accidentId) {
        return getRuleIds(accidentId)
                .stream()
                .map(i -> jdbc.queryForObject("select * from rule where id = ?",
                (resultSet, rowNum) -> new Rule(resultSet.getInt("id"),
                        resultSet.getString("name")), i))
                .collect(Collectors.toList());
    }

    private void updateType(AccidentType type) {
        jdbc.update("update rule set name = ? where id = ?",
                type.getName(), type.getId());
    }

    private void updateAccidentRules(Collection<Rule> rules, int accidentId) {
        clearAccidentRules(accidentId);
        saveRule(rules, accidentId);
    }

    private void clearAccidentRules(int accidentId) {
        jdbc.update("delete from accident_rule where accident_id = ?", accidentId);
    }
}
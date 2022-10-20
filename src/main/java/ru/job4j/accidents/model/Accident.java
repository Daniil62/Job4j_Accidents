package ru.job4j.accidents.model;

import javax.persistence.*;
import java.util.*;

@Entity
public class Accident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String text;
    private String address;
    @OneToOne
    @JoinColumn(name = "type_id")
    private AccidentType type;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "accident_rule",
            joinColumns = @JoinColumn(name = "accident_id", nullable = false, referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "rule_id", nullable = false, referencedColumnName = "id"))
    private final Set<Rule> rules = new HashSet<>();

    public Accident() {
    }

    public Accident(int id, String name, String text, String address, AccidentType type) {
        this.id = id;
        this.name = name;
        this.text = text;
        this.address = address;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public AccidentType getType() {
        return type;
    }

    public void setType(AccidentType type) {
        this.type = type;
    }

    public Set<Rule> getRules() {
        return Set.copyOf(rules);
    }

    public void setRules(Collection<Rule> collection) {
        rules.clear();
        rules.addAll(collection);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Accident)) {
            return false;
        }
        Accident accident = (Accident) o;
        return id == accident.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
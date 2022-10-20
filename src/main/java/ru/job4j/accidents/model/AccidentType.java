package ru.job4j.accidents.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "accident_type")
public class AccidentType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name = "";

    public AccidentType() {
    }

    public AccidentType(int id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AccidentType)) {
            return false;
        }
        AccidentType that = (AccidentType) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
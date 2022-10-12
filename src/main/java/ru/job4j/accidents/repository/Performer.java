package ru.job4j.accidents.repository;

import org.hibernate.Session;

import java.util.function.Function;

public interface Performer {

    <T> T perform(final Function<Session, T> command);
}
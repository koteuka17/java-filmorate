package ru.yandex.practicum.filmorate.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ValidationTest {
    private Validator validator;
    private ValidatorFactory factory;

    @BeforeEach
    public void setUp() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterEach
    public void close() {
        factory.close();
    }

    @Test
    public void testUser() {
        User user = new User();
        user.setId(1);
        user.setBirthday(LocalDate.of(2001, 12, 12));
        user.setLogin("luke");
        user.setEmail("luke@ya.ru");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty());

        user.setEmail("lukeya.ru@");
        violations = validator.validate(user);
        assertFalse(violations.isEmpty());

        violations.clear();
        user.setEmail(null);
        violations = validator.validate(user);
        assertFalse(violations.isEmpty());

        violations.clear();
        user.setEmail("luke@ya.ru");
        user.setLogin(null);
        violations = validator.validate(user);
        assertFalse(violations.isEmpty());

        violations.clear();
        user.setLogin("luke");
        user.setBirthday(null);
        violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testFilm() {
        Film film = new Film();
        film.setId(1);
        film.setName("Cars");
        film.setDescription("Мультфильм о живых машинках");
        film.setReleaseDate(LocalDate.of(2006, 06, 15));
        film.setDuration(117);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertTrue(violations.isEmpty());

        film.setName(null);
        violations = validator.validate(film);
        assertFalse(violations.isEmpty());

        violations.clear();
        film.setName("");
        violations = validator.validate(film);
        assertFalse(violations.isEmpty());

        violations.clear();
        film.setDescription(null);
        violations = validator.validate(film);
        assertFalse(violations.isEmpty());

        violations.clear();
        film.setName("Cars");
        film.setDescription("12345678901234567890123456789012345678901234567890" +
                "12345678901234567890123456789012345678901234567890" +
                "12345678901234567890123456789012345678901234567890" +
                "12345678901234567890123456789012345678901234567890");
        violations = validator.validate(film);
        assertTrue(violations.isEmpty());

        film.setDescription("12345678901234567890123456789012345678901234567890" +
                "12345678901234567890123456789012345678901234567890" +
                "12345678901234567890123456789012345678901234567890" +
                "123456789012345678901234567890123456789012345678901");
        violations = validator.validate(film);
        assertFalse(violations.isEmpty());

        violations.clear();
        film.setDescription("123");
        film.setReleaseDate(LocalDate.of(1895, 12, 28));
        violations = validator.validate(film);
        assertTrue(violations.isEmpty());

        film.setReleaseDate(LocalDate.of(1895, 12, 27));
        violations = validator.validate(film);
        assertFalse(violations.isEmpty());

        violations.clear();
        film.setReleaseDate(LocalDate.of(2006, 06, 15));
        film.setDuration(0);
        violations = validator.validate(film);
        assertFalse(violations.isEmpty());

        violations.clear();
        film.setDuration(-5);
        violations = validator.validate(film);
        assertFalse(violations.isEmpty());
    }
}
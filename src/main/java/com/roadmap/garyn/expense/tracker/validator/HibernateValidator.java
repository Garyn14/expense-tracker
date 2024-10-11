package com.roadmap.garyn.expense.tracker.validator;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class HibernateValidator {

    public String getFirstConstraint(ConstraintViolationException ex){
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();

        return "ERROR -> " + violations.stream()
                .map(v -> v.getPropertyPath()
                        .toString()
                        .substring(v.getPropertyPath()
                                .toString()
                                .lastIndexOf('.') + 1
                        ) + ": " + v.getMessage()
                )
                .sorted()
                .findFirst()
                .orElse("Unknown error");
    }
}

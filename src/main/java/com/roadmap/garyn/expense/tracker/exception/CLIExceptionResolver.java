package com.roadmap.garyn.expense.tracker.exception;

import com.roadmap.garyn.expense.tracker.validator.HibernateValidator;
import jakarta.validation.ConstraintViolationException;
import org.springframework.shell.command.CommandExceptionResolver;
import org.springframework.shell.command.CommandHandlingResult;
import org.springframework.transaction.TransactionSystemException;

public class CLIExceptionResolver implements CommandExceptionResolver {
    private final HibernateValidator  hibernateValidator = new HibernateValidator();
    @Override
    public CommandHandlingResult resolve(Exception ex) {
        if (ex instanceof ConstraintViolationException constraintViolationException) {
            return CommandHandlingResult.of(hibernateValidator.getFirstConstraint(constraintViolationException) + "\n");
        }

        if (ex instanceof TransactionSystemException){
            return CommandHandlingResult.of("Fields do not meet restrictions" + "\n");
        }

        return CommandHandlingResult.of(ex.getMessage() + "\n");
    }
}

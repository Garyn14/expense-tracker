package com.roadmap.garyn.expense.tracker.command;

import com.roadmap.garyn.expense.tracker.enums.ExpenseType;
import com.roadmap.garyn.expense.tracker.model.Expense;
import com.roadmap.garyn.expense.tracker.service.ExpenseService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import java.math.BigDecimal;
import java.util.Set;

@ShellComponent
public class ExpenseCommands {

    private final ExpenseService expenseService;

    @Autowired
    public ExpenseCommands(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @ShellMethod(key = "expense-tracker add", value = "Save an expense")
    public String saveExpense(
            @ShellOption(defaultValue = "") String description,
            @ShellOption(defaultValue = "") String  amount,
            @ShellOption(defaultValue = "OTHER") String type
    ) {

        ExpenseType expenseType;
        BigDecimal parsedAmount;

        try {
            expenseType = ExpenseType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            return "ERROR: Invalid expense type. Please use 'food', 'transportation', " +
                    "'utilities' or 'entertainment'.\nThe default value of 'type' is other";
        } catch (NullPointerException e) {
            return "ERROR: Expense type is required";
        }

        try {
            parsedAmount = new BigDecimal(amount);
        } catch (NumberFormatException e) {
            return "ERROR: Invalid amount. Please enter a valid number";
        } catch (NullPointerException e) {
            return "ERROR: Amount is required";
        }

        Expense expense = Expense.builder()
                .description(description)
                .amount(parsedAmount)
                .expenseType(expenseType)
                .build();

        try {
            return expenseService.saveExpense(expense);
        } catch (ConstraintViolationException ex) {

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


    @ShellMethod(key = "expense-tracker list", value = "Get all expenses")
    public String getExpenses() {
        return expenseService.getExpenses();
    }

    @ShellMethod(key = "expense-tracker delete", value = "Delete an expense by id")
    public String deleteExpense(@ShellOption(defaultValue = "") String id) {

        long idLong;
        if (id == null || id.isEmpty()) return "ERROR: Id is required";

        try {
            idLong = Long.parseLong(id);
        } catch (NumberFormatException e) {
            return "ERROR: Invalid id";
        }

        if (idLong <= 0) return "ERROR: Invalid id";
        return expenseService.deleteExpense(idLong);
    }
}

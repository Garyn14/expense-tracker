package com.roadmap.garyn.expense.tracker.command;

import com.roadmap.garyn.expense.tracker.converter.CastTypes;
import com.roadmap.garyn.expense.tracker.enums.ExpenseType;
import com.roadmap.garyn.expense.tracker.exception.InvalidTypeException;
import com.roadmap.garyn.expense.tracker.exception.NullArgumentException;
import com.roadmap.garyn.expense.tracker.model.Expense;
import com.roadmap.garyn.expense.tracker.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import java.math.BigDecimal;

@ShellComponent
public class ExpenseCommands {
    private final ExpenseService expenseService;
    private final CastTypes castTypes;
    private static final String DEFAULT_TYPE = "OTHER";

    @Autowired
    public ExpenseCommands(ExpenseService expenseService, CastTypes  castTypes) {
        this.expenseService = expenseService;
        this.castTypes = castTypes;
    }

    @ShellMethod(key = "expense-tracker add", value = "Save an expense")
    public String createExpense(
            @ShellOption(defaultValue = "") String description,
            @ShellOption(defaultValue = "") String amount,
            @ShellOption(defaultValue = DEFAULT_TYPE) String type
    ) {
        ExpenseType expenseType;
        BigDecimal parsedAmount;

        try {
            parsedAmount = castTypes.castToBigDecimal(amount);
            expenseType = castTypes.castToExpenseType(type);
        } catch (NullPointerException e) {
            throw new NullArgumentException("ERROR: fields are required");
        } catch (IllegalArgumentException e) {
            throw new InvalidTypeException("ERROR: Invalid expense type. Please use 'food', 'transportation', " +
                    "'utilities' or 'entertainment' ");
        }

        Expense expense = Expense.builder()
                .description(description)
                .amount(parsedAmount)
                .expenseType(expenseType)
                .build();

        Expense expenseCreated = expenseService.createExpense(expense);
        return "Expense added successfully (ID: " + expenseCreated.getId() + ")";
    }

    @ShellMethod(key = "expense-tracker list", value = "Get all expenses")
    public String getExpenses() {
        return expenseService.getExpenses();
    }

    @ShellMethod(key = "expense-tracker update", value = "Update an expense")
    public String updateExpense(
            @ShellOption(defaultValue = "") String id,
            @ShellOption(defaultValue = "") String description,
            @ShellOption(defaultValue = "") String amount,
            @ShellOption(defaultValue = "") String type
    ) {
        Long idLong;
        BigDecimal parsedAmount = null;
        ExpenseType expenseType = null;

        try{
            if (id == null || id.isEmpty())
                throw new NullArgumentException("ERROR: Id is required");

            idLong = castTypes.castToLong(id);

            if (amount != null && !amount.isEmpty())
                parsedAmount = castTypes.castToBigDecimal(amount);

            if  (type != null && !type.isEmpty())
                expenseType = castTypes.castToExpenseType(type);

            if (description.isEmpty())
                description = null;

        } catch (NullPointerException e) {
            throw new NullArgumentException("ERROR: Fields are required");
        } catch (NumberFormatException e) {
            throw new NumberFormatException("ERROR: enter a validator number");
        } catch (IllegalArgumentException e) {
            throw new InvalidTypeException("ERROR: Invalid expense type. Please use 'food', 'transportation', " +
                    "'utilities' or 'entertainment' ");
        }

        Expense expenseUpdated = expenseService.updateExpense(idLong, description, parsedAmount, expenseType);
        return "Expense with ID:" + expenseUpdated.getId() + " updated successfully";
    }

    @ShellMethod(key = "expense-tracker delete", value = "Delete an expense by id")
    public String deleteExpense(@ShellOption(defaultValue = "") String id) {

        long idLong;
        if (id == null || id.isEmpty()) throw new NullArgumentException("ERROR: Id is required");

        try {
            idLong = Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("ERROR: enter a validator number");
        }

        if (idLong <= 0) return "ERROR: Invalid id";
        return expenseService.deleteExpense(idLong);
    }

    @ShellMethod(key = "expense-tracker total", value = "Get total expenses")
    public String getTotalExpenses() {
        return "Total expenses: " + expenseService.getExpenseTotal();
    }

    @ShellMethod(key = "expense-tracker find-by-type", value = "Get expenses by type")
    public String getExpensesByType(@ShellOption String type) {
        ExpenseType expenseType;
        try {
            expenseType = castTypes.castToExpenseType(type);
        } catch (IllegalArgumentException e) {
            throw new InvalidTypeException("ERROR: Invalid expense type. Please use 'food', 'transportation', " +
                    "'utilities' or 'entertainment' ");
        } catch (NullPointerException e) {
            throw new NullArgumentException("ERROR: Type is required");
        }
        return expenseService.getExpensesByType(expenseType);
    }

    @ShellMethod(key = "expense-tracker export-csv", value = "Export expenses to CSV file")
    public String getExpensesCsv(){
        return expenseService.getExpensesCsv();
    }
}

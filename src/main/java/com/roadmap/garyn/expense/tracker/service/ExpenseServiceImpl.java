package com.roadmap.garyn.expense.tracker.service;

import com.opencsv.CSVWriter;
import com.roadmap.garyn.expense.tracker.enums.ExpenseType;
import com.roadmap.garyn.expense.tracker.exception.ExpenseNotFoundException;
import com.roadmap.garyn.expense.tracker.exception.NullArgumentException;
import com.roadmap.garyn.expense.tracker.model.Expense;
import com.roadmap.garyn.expense.tracker.repository.ExpenseRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Service
@Validated
public class ExpenseServiceImpl implements ExpenseService{

    private final ExpenseRepository expenseRepository;

    @Autowired
    public ExpenseServiceImpl(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    @Transactional
    public Expense createExpense(@Valid Expense expense) {
        return expenseRepository.save(expense);
    }

    @Override
    @Transactional(readOnly = true)
    public String getExpenses() {
        List<Expense> expenses = expenseRepository.findAll();
        if (expenses.isEmpty()) return "No expenses found";
        return formatExpense(expenses);
    }

    @Override
    @Transactional
    public Expense updateExpense(Long id, String description, BigDecimal amount, ExpenseType expenseType) {

        boolean validUpdate = false;
        Expense actualExpense = expenseRepository.findById(id)
                .orElseThrow(() -> new ExpenseNotFoundException(id));

        if (description != null){
            validUpdate = true;
            actualExpense.setDescription(description);
        }

        if (amount != null){
            validUpdate = true;
            actualExpense.setAmount(amount);
        }

        if (expenseType != null){
            validUpdate = true;
            actualExpense.setExpenseType(expenseType);
        }

        if (!validUpdate)
            throw new NullArgumentException("No validator fields provided for update");
        return expenseRepository.save(actualExpense);
    }

    @Override
    @Transactional
    public String deleteExpense(Long id) {
        try {

            Expense  expense = expenseRepository.findById(id)
                    .orElseThrow(() -> new ExpenseNotFoundException(id));
            expenseRepository.delete(expense);
            return "Expense deleted successfully";

        } catch (ExpenseNotFoundException e) {
            return e.getMessage();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal getExpenseTotal() {
        return expenseRepository.sumAmount();
    }

    @Override
    @Transactional(readOnly = true)
    public String getExpensesByType(ExpenseType type) {
        List<Expense > expenses = expenseRepository.findByExpenseType(type);
        if (expenses.isEmpty()) return "No expenses found for type: " + type.getExpense();
        return formatExpense(expenses);
    }

    @Override
    public String getExpensesCsv() {
        List<Expense> expenses = expenseRepository.findAll();
        try(CSVWriter writer = new CSVWriter(new FileWriter("expenses.csv"))) {
            writer.writeNext(new String[]{"ID", "Description", "Amount", "Type"});
            for (Expense expense : expenses) {
                writer.writeNext(new String[]{
                        String.valueOf(expense.getId()),
                        expense.getDescription(),
                        String.valueOf(expense.getAmount()),
                        expense.getExpenseType().getExpense()
                });
            }

            return "CSV file generated successfully";
        } catch (IOException e) {
            return "Error generating CSV file: " + e.getMessage();
        }
    }

    private String formatExpense(List<Expense> expenses) {
        StringBuilder output = new StringBuilder();
        String header = String.format("# %-4s %-20s %-10s %-20s", "ID", "Description", "Amount", "Type");
        output.append(header).append("\n");
        output.append("-".repeat(header.length())).append("\n");

        for (Expense expense : expenses) {
            String row = String.format(
                    "# %-4d %-20s $%-10.2f %-20s",
                    expense.getId(),
                    expense.getDescription(),
                    expense.getAmount(),
                    expense.getExpenseType().getExpense()
            );
            output.append(row).append("\n");
        }

        return output.toString();
    }

}

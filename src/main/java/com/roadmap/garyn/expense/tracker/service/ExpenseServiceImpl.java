package com.roadmap.garyn.expense.tracker.service;

import com.roadmap.garyn.expense.tracker.exception.ExpenseNotFoundException;
import com.roadmap.garyn.expense.tracker.model.Expense;
import com.roadmap.garyn.expense.tracker.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

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
    public String saveExpense(Expense expense) {
        Expense expenseCreated = expenseRepository.save(expense);
        return "Expense added successfully (ID: " + expenseCreated.getId() + ")";
    }

    @Override
    @Transactional(readOnly = true)
    public String getExpenses() {
        List<Expense> expenses = expenseRepository.findAll();

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
}

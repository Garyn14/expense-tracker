package com.roadmap.garyn.expense.tracker.service;

import com.roadmap.garyn.expense.tracker.enums.ExpenseType;
import com.roadmap.garyn.expense.tracker.model.Expense;
import jakarta.validation.Valid;

import java.math.BigDecimal;
import java.util.List;

public interface ExpenseService {
    Expense createExpense(@Valid Expense expense);
    String getExpenses();
    Expense updateExpense(Long id, String description, BigDecimal amount, ExpenseType type);
    String deleteExpense(Long id);
    BigDecimal getExpenseTotal();
    String getExpensesByType(ExpenseType type);
    String getExpensesCsv();
}

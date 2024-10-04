package com.roadmap.garyn.expense.tracker.service;

import com.roadmap.garyn.expense.tracker.model.Expense;
import jakarta.validation.Valid;

public interface ExpenseService {
    String saveExpense(@Valid Expense expense);
    String getExpenses();
}

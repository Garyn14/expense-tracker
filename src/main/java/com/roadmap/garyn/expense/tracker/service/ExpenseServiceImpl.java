package com.roadmap.garyn.expense.tracker.service;

import com.roadmap.garyn.expense.tracker.model.Expense;
import com.roadmap.garyn.expense.tracker.repository.ExpenseRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;

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


}

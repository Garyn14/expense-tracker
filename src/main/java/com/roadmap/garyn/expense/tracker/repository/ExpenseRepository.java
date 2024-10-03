package com.roadmap.garyn.expense.tracker.repository;

import com.roadmap.garyn.expense.tracker.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}

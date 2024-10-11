package com.roadmap.garyn.expense.tracker.repository;

import com.roadmap.garyn.expense.tracker.enums.ExpenseType;
import com.roadmap.garyn.expense.tracker.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    @Query("SELECT SUM(e.amount) FROM Expense e")
    BigDecimal sumAmount();

    List<Expense> findByExpenseType(ExpenseType type);
}

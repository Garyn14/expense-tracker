package com.roadmap.garyn.expense.tracker.exception;

public class ExpenseNotFoundException extends RuntimeException{
    public ExpenseNotFoundException(Long id) {
        super("ERROR: Task with id " + id + " not found");
    }
}

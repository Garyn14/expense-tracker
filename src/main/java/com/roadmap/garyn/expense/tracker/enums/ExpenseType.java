package com.roadmap.garyn.expense.tracker.enums;

import lombok.Getter;

@Getter
public enum ExpenseType {
    FOOD("food"),
    TRANSPORTATION("transportation"),
    UTILITIES("utilities"),
    ENTERTAINMENT("entertainment"),
    OTHER("other");

    private final String expense;

    ExpenseType (String expense) {
        this.expense = expense;
    }

    @Override
    public String toString() {
        return expense;
    }
}

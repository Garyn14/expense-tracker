package com.roadmap.garyn.expense.tracker.converter;

import com.roadmap.garyn.expense.tracker.enums.ExpenseType;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ExpenseTypeConverter implements AttributeConverter<ExpenseType, String>{
    @Override
    public String convertToDatabaseColumn(ExpenseType expenseType) {
        if (expenseType == null) {
            return null;
        }

        return expenseType.getExpense();
    }

    @Override
    public ExpenseType convertToEntityAttribute(String dbData) {

        if (dbData == null) {
            return null;
        }

        for (ExpenseType type : ExpenseType.values()) {
            if (type.getExpense().equalsIgnoreCase(dbData)) {
                return type;
            }
        }

        throw new IllegalArgumentException("Invalid expense type: " + dbData);
    }
}

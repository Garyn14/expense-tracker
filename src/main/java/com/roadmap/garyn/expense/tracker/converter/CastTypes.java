package com.roadmap.garyn.expense.tracker.converter;

import com.roadmap.garyn.expense.tracker.enums.ExpenseType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CastTypes {
    public BigDecimal castToBigDecimal(String value) throws NumberFormatException, NullPointerException{
        return new BigDecimal(value);
    }

    public ExpenseType castToExpenseType(String value) throws IllegalArgumentException, NullPointerException{
        return ExpenseType.valueOf(value.toUpperCase());
    }

    public Long castToLong(String value) throws NumberFormatException, NullPointerException{
        return Long.parseLong(value);
    }
}

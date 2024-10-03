package com.roadmap.garyn.expense.tracker.model;

import com.roadmap.garyn.expense.tracker.converter.ExpenseTypeConverter;
import com.roadmap.garyn.expense.tracker.enums.ExpenseType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "expenses")
@Getter @Setter
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank(message = "Description is required")
    @Size(max = 255, message = "Description must be less than 255 characters")
    @Column(name = "description", nullable = false)
    private String description;

    @Convert(converter = ExpenseTypeConverter.class)
    @Column(name = "type", nullable = false)
    private ExpenseType expenseType;

    @Column(name = "amount", nullable = false)
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    @DecimalMax(value = "99999999.99", message = "Amount must be less than 100,000,000")
    @Digits(integer = 8, fraction = 2, message = "Amount must have a maximum of 2 decimal places")
    private BigDecimal amount;
}

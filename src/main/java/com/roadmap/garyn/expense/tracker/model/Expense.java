package com.roadmap.garyn.expense.tracker.model;

import com.roadmap.garyn.expense.tracker.converter.ExpenseTypeConverter;
import com.roadmap.garyn.expense.tracker.enums.ExpenseType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "expenses")
@Getter @Setter
@Builder @AllArgsConstructor
@ToString
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank(message = "Description is require")
    @Size(min = 2, max = 255)
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "type", nullable = false)
    @Convert(converter = ExpenseTypeConverter.class)
    private ExpenseType expenseType;

    @Column(name = "amount", nullable = false)
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    @DecimalMax(value = "99999999.99", message = "Amount must be less than 100,000,000")
    @Digits(integer = 8, fraction = 2, message = "Amount must have a maximum of 2 decimal places")
    private BigDecimal amount;

    public Expense() { // NO SONAR

    }
}

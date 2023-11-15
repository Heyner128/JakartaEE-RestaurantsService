package me.heyner.jeejaxrs.dto;

import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.heyner.jeejaxrs.model.Dish;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "price"})})
public class DishDto implements Serializable {

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @Min(0)
    private Long price;
}
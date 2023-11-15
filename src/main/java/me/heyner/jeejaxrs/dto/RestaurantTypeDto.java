package me.heyner.jeejaxrs.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.heyner.jeejaxrs.model.RestaurantType;

import java.io.Serializable;

/**
 * DTO for {@link me.heyner.jeejaxrs.model.RestaurantType}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantTypeDto implements Serializable {
    @NotNull
    @Column(unique = true)
    private String name;
}
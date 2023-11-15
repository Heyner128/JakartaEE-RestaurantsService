package me.heyner.jeejaxrs.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.heyner.jeejaxrs.model.Restaurant;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDto implements Serializable {


    @NotBlank
    private String name;

    @NotBlank
    private String address;

    @NotNull
    private Boolean active;

    @NotNull
    private String phone;

    @Min(1)
    @Max(5)
    @NotNull
    private Integer score;
}

package me.heyner.jeejaxrs.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank
    String name;

    @NotBlank
    String address;

    @NotNull
    Boolean active;

    @NotBlank
    String phone;

    @Min(1)
    @Max(5)
    @NotNull
    Integer score;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="RESTAURANT_TYPE_ID")
    @ToString.Exclude
    private RestaurantType restaurantType;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @ToString.Exclude
    private Set<Dish> dishes = new HashSet<>();

    @CreationTimestamp
    @NotNull
    LocalDateTime creationDate = LocalDateTime.now();

    public void associateWith(RestaurantType restaurantType) {
        setRestaurantType(restaurantType);
        if(this.restaurantType != null) {
            this.restaurantType.getRestaurants().add(this);
        }
    }

    public void associateWith(Dish dish) {
        if(dish != null) {
            dish.getRestaurants().add(this);
            this.dishes.add(dish);
        }
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Restaurant that = (Restaurant) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}

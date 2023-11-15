package me.heyner.jeejaxrs.dao;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import me.heyner.jeejaxrs.model.Dish;
import me.heyner.jeejaxrs.model.Restaurant;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

@Stateless
public class RestaurantDao {

    @PersistenceContext
    EntityManager entityManager;

    public Restaurant merge(Restaurant restaurant) {
        return entityManager.merge(restaurant);
    }

    public Restaurant findById(Long id) {
        return entityManager.find(Restaurant.class, id);
    }

    public void deleteById(Long id) {
        Restaurant restaurantToRemove = findById(id);
        if(restaurantToRemove == null) throw new RuntimeException("The requested entity for deletion doesn't exists");
        entityManager.remove(restaurantToRemove);
    }

    public Set<Dish> findDishesByRestaurantId(Long id) {
        Restaurant restaurant = findById(id);

        return restaurant.getDishes();
    }

    public List<Restaurant> findByContainsNameInsensitive(String name) {
        TypedQuery<Restaurant> query = entityManager.createQuery(
                "select r from Restaurant r where upper(r.name) like :queryName",
                Restaurant.class
        ).setParameter("queryName", "%" + name.toUpperCase() + "%");

        return query.getResultList();
    }
}

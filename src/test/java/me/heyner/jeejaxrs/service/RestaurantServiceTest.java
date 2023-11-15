package me.heyner.jeejaxrs.service;
import jakarta.inject.Inject;
import me.heyner.jeejaxrs.model.Dish;
import me.heyner.jeejaxrs.model.Restaurant;
import me.heyner.jeejaxrs.service.RestaurantService;
import org.apache.openejb.testing.SingleApplicationComposerRunner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.Set;


@RunWith(SingleApplicationComposerRunner.class)
public class RestaurantServiceTest {

    @Inject
    RestaurantService restaurantService;

    Restaurant restaurant;

    @Before
    public void createTestInstance() {
            restaurant = new Restaurant();
            restaurant.setName("test restaurant");
            restaurant.setAddress("Fake st. 123");
            restaurant.setActive(true);
            restaurant.setPhone("123456789");
            restaurant.setScore(5);
            restaurant = restaurantService.merge(restaurant);
    }




    @Test
    public void testMergeRestaurant() {

        Assert.assertNotNull(restaurant);
    }

    @Test
    public void testFindByName() {
        List<Restaurant> searchResults = restaurantService.findByName(restaurant.getName());
        Assert.assertFalse(searchResults.isEmpty());
    }

    @Test
    public void testFindById() {

        Restaurant resultRestaurant = restaurantService.findById(restaurant.getId().toString());
        Assert.assertNotNull(resultRestaurant);
    }

    @Test
    public void testFindDishes() {
        Dish dish = new Dish();
        dish.setName("test dish");
        dish.setPrice(123456L);
        restaurant.associateWith(dish);
        restaurantService.merge(restaurant);
        Set<Dish> dishesList = restaurantService.findDishes(restaurant.getId().toString());
        Assert.assertFalse(dishesList.isEmpty());
    }
}

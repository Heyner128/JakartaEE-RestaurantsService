package me.heyner.jeejaxrs.service;


import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import me.heyner.jeejaxrs.dao.RestaurantDao;
import me.heyner.jeejaxrs.model.Dish;
import me.heyner.jeejaxrs.model.Restaurant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Set;

@Stateless
public class RestaurantService {


    @Inject
    private RestaurantDao restaurantDao;

    private static final Logger logger = LoggerFactory.getLogger(RestaurantService.class);

    public Restaurant merge(Restaurant restaurant) {
        logger.info("Saving restaurant: {}", restaurant.toString());
        return restaurantDao.merge(restaurant);
    }

    public void deleteById(Long id) {
        restaurantDao.deleteById(id);
    }

    public List<Restaurant> findByName(String name) {
        logger.info("Searching for restaurant: {}", name);
        return restaurantDao.findByContainsNameInsensitive(name);
    }

    public Set<Dish> findDishes(String id) {
        logger.info("Searching dishes for restaurant id: {}", id);

        return restaurantDao.findDishesByRestaurantId(Long.parseLong(id));
    }


    public Restaurant findById(String id) {
        logger.info("Requested restaurant id: {}", id);
        return restaurantDao.findById(Long.parseLong(id));
    }
}

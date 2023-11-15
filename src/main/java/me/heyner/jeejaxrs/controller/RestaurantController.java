package me.heyner.jeejaxrs.controller;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import me.heyner.jeejaxrs.dto.DishDto;
import me.heyner.jeejaxrs.dto.RestaurantDto;
import me.heyner.jeejaxrs.model.Restaurant;
import me.heyner.jeejaxrs.service.RestaurantService;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@Path("/restaurants")
@Produces(MediaType.APPLICATION_JSON)
public class RestaurantController {

    @Inject
    private RestaurantService restaurantService;

    private final ModelMapper modelMapper = new ModelMapper();

    @GET
    public Response getRestaurantByName(@QueryParam("name") String name) {
        List<Restaurant> restaurants = restaurantService.findByName(name);
        List<RestaurantDto> restaurantDtos = restaurants.stream()
                .map(restaurant -> modelMapper.map(restaurant, RestaurantDto.class))
                .collect(Collectors.toList());

        return Response.ok(restaurantDtos).type(MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/{id}")
    public Response getRestaurant(@PathParam("id") String id) {
        Restaurant restaurant = restaurantService.findById(id);
        if(restaurant == null) {
            return Response.noContent().type(MediaType.TEXT_PLAIN).build();
        }
        RestaurantDto restaurantDto = modelMapper.map(restaurant, RestaurantDto.class);
        return Response.ok(restaurantDto).type(MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/{id}/dishes")
    public Response getRestaurantDishes(@PathParam("id") String id) {
        Restaurant restaurant = restaurantService.findById(id);
        if(restaurant == null) {
            return Response.noContent().type(MediaType.TEXT_PLAIN).build();
        }
        List<DishDto> dishDtos = restaurantService.findDishes(id).stream()
                .map(dish -> modelMapper.map(dish, DishDto.class))
                .collect(Collectors.toList());
        return Response.ok(dishDtos).type(MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createRestaurant(@Valid RestaurantDto restaurantDto) {
        Restaurant restaurant = modelMapper.map(restaurantDto, Restaurant.class);

        restaurantService.merge(restaurant);

        return Response.ok().type(MediaType.APPLICATION_JSON).build();
    }
}

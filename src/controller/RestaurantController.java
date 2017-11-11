package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import dao.RestaurantDAO;
import entity.Restaurant;

@Controller
@RequestMapping("/restaurant")
public class RestaurantController {
	
	@Autowired
	private RestaurantDAO restaurantDAO;
	
	@GetMapping()
	public String listCustomers(Model theModel) {
		
		List<Restaurant> theRestaurants = restaurantDAO.getRestaurant();
		
		theModel.addAttribute("restaurants", theRestaurants);
		
		return "nearbyRestaurant";
	}
}

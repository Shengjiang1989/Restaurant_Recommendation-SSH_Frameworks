package controller;

import java.io.PrintWriter;
import java.util.List;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dao.RestaurantDAO;
import entity.Restaurant;
import service.RestaurantService;

@Controller
@RequestMapping("/restaurants")
public class RestaurantController {
	
	@Autowired
	private RestaurantService restaurantService;
	
	@GetMapping()
	public void listRestaurants(@RequestParam(value="user_id") String userId,
								@RequestParam(value="lat") String latStr,
								@RequestParam(value="lon") String lonStr,
								PrintWriter printWriter) {
		double lat = Double.parseDouble(latStr);
		double lon = Double.parseDouble(lonStr);
		
		JSONArray array = restaurantService.searchRestaurants(userId, lat, lon);
		printWriter.print(array);
		printWriter.flush();
		printWriter.close();
	}
	
//	@GetMapping()
//	public String listRestaurants(@RequestParam(value="user_id") String userId,
//								@RequestParam(value="lat") String latStr,
//								@RequestParam(value="lon") String lonStr,
//								Model theModel) {
//		
//		double lat = Double.parseDouble(latStr);
//		double lon = Double.parseDouble(lonStr);
//		List<Restaurant> theCustomers = restaurantDAO.getRestaurant(userId, lat, lon);
//		
//		theModel.addAttribute("restaurants", theCustomers);
//		return "nearbyRestaurant"; 
//	}
}

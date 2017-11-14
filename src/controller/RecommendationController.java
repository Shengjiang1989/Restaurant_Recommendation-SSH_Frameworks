package controller;

import java.io.PrintWriter;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dao.RestaurantDAO;

@Controller
@RequestMapping("/recommendation")
public class RecommendationController {

	@Autowired
	private RestaurantDAO restaurantDAO;
	
	@GetMapping()
	public void listRestaurant(@RequestParam(value="user_id") String userId, PrintWriter printWriter) {
		JSONArray array = null;

		if (userId != null) {
			array = restaurantDAO.recommendRestaurants(userId);
		}
		printWriter.print(array);
		printWriter.flush();
		printWriter.close();
	}
}

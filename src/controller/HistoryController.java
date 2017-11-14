package controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dao.RestaurantDAO;

@Controller
@RequestMapping("/history")
public class HistoryController {

	@Autowired
	private RestaurantDAO restaurantDAO;

	@PostMapping()
	public void addHistory(@RequestBody String jsonString, PrintWriter printWriter) {

		try {
			JSONObject input = new JSONObject(jsonString);
			if (input.has("user_id") && input.has("visited")) {
				String userId = (String) input.get("user_id");
				JSONArray array = (JSONArray) input.get("visited");
				List<String> visitedRestaurants = new ArrayList<>();
				for (int i = 0; i < array.length(); i++) {
					String businessId = (String) array.get(i);
					visitedRestaurants.add(businessId);
				}
				restaurantDAO.setVisitedRestaurants(userId, visitedRestaurants);
				printWriter.print(new JSONObject().put("status", "OK"));
			} else {
				printWriter.print(new JSONObject().put("status", "InvalidParameter"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@DeleteMapping()
	public void deleteHistory(@RequestBody String jsonString, PrintWriter printWriter) {
		try {
			JSONObject input = new JSONObject(jsonString);
			if (input.has("user_id") && input.has("visited")) {
				String userId = (String) input.get("user_id");
				JSONArray array = (JSONArray) input.get("visited");
				List<String> visitedRestaurants = new ArrayList<>();
				for (int i = 0; i < array.length(); i++) {
					String businessId = (String) array.get(i);
					visitedRestaurants.add(businessId);
				}
				restaurantDAO.unsetVisitedRestaurants(userId, visitedRestaurants);
				printWriter.print(new JSONObject().put("status", "OK"));
			} else {
				printWriter.print(new JSONObject().put("status", "InvalidParameter"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@GetMapping()
	public void getHistory(@RequestParam(value="user_id") String userId, PrintWriter printWriter) {

		try {
			JSONArray array = null;
			// allow access only if session exists
			/*
			 * if (!RpcParser.sessionValid(request, connection)) { response.setStatus(403);
			 * return; }
			 */
			if (userId != null) {
				Set<String> visited_business_id = restaurantDAO.getVisitedRestaurants(userId);
				array = new JSONArray();
				for (String id : visited_business_id) {
					array.put(restaurantDAO.getRestaurantsById(id, true));
				}
				printWriter.print(array);
				printWriter.flush();
				printWriter.close();
			} else {
				printWriter.print(new JSONObject().put("status", "InvalidParameter"));
				printWriter.flush();
				printWriter.close();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}
}

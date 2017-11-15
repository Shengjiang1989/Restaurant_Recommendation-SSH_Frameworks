package service;

import java.util.Set;

import org.json.JSONArray;

public interface RestaurantService {

	public JSONArray searchRestaurants(String userId, double lat, double lon);
	
}

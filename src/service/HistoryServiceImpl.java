package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.HistoryDAO;
import dao.RestaurantDAO;

@Service
@Transactional
public class HistoryServiceImpl implements HistoryService {

	@Autowired
	private HistoryDAO historyDAO;
	
	@Autowired
	private RestaurantDAO restaurantDAO;
	
	@Override
	public void addHistories(JSONObject input) {
		try {
			String userId = (String) input.get("user_id");
			JSONArray array = (JSONArray) input.get("visited");
			List<String> visitedRestaurants = new ArrayList<>();
			for (int i = 0; i < array.length(); i++) {
				String businessId = (String) array.get(i);
				visitedRestaurants.add(businessId);
			}	
			historyDAO.setVisitedRestaurants(userId, visitedRestaurants);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}

	@Override
	public void deleteHistories(JSONObject input) {
		try {
			String userId = (String) input.get("user_id");
			JSONArray array = (JSONArray) input.get("visited");
			List<String> visitedRestaurants = new ArrayList<>();
			for (int i = 0; i < array.length(); i++) {
				String businessId = (String) array.get(i);
				visitedRestaurants.add(businessId);
			}
			historyDAO.unsetVisitedRestaurants(userId, visitedRestaurants);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public JSONArray getHistories(String userId) {
		Set<String> visited_business_id = restaurantDAO.getVisitedRestaurants(userId);
		JSONArray array = new JSONArray();
		for (String id : visited_business_id) {
			array.put(restaurantDAO.getRestaurantsById(id, true));
		}
		return array;
	}

}

package service;

import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.RecommendationDAO;
import dao.RestaurantDAO;

@Service
@Transactional
public class RecommendationServiceImpl implements RecommendationService {
	private static final int MAX_RECOMMENDED_RESTAURANTS = 10;

	@Autowired
	RestaurantDAO restaurantDAO;

	@Autowired
	RecommendationDAO recommendationDAO;
	
	@Override
	public JSONArray recommendRestaurants(String userId) {
		try {
			Set<String> visitedRestaurants = restaurantDAO.getVisitedRestaurants(userId);// step 1
			Set<String> allCategories = new HashSet<>();// why hashSet? //step 2
			for (String restaurant : visitedRestaurants) {
				allCategories.addAll(recommendationDAO.getCategories(restaurant));
			}
			Set<String> allRestaurants = new HashSet<>();// step 3
			for (String category : allCategories) {
				Set<String> set = recommendationDAO.getBusinessId(category);
				allRestaurants.addAll(set);
			}
			Set<JSONObject> diff = new HashSet<>();// step 4
			int count = 0;
			for (String businessId : allRestaurants) {
				// Perform filtering
				if (!visitedRestaurants.contains(businessId)) {
					diff.add(restaurantDAO.getRestaurantsById(businessId, false));
					count++;
					if (count >= MAX_RECOMMENDED_RESTAURANTS) {
						break;
					}
				}
			}
			return new JSONArray(diff);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

}

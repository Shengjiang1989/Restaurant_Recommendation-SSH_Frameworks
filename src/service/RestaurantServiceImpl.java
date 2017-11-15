package service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.RestaurantDAO;
import entity.Restaurant;
import entity.User;
import yelp.YelpAPI;

@Service
@Transactional
public class RestaurantServiceImpl implements RestaurantService {

	@Autowired
	private RestaurantDAO restaurantDAO;

	@Override
	public JSONArray searchRestaurants(String userId, double lat, double lon) {
		try {
			// Connect to Yelp API
			YelpAPI api = new YelpAPI();
			JSONObject response = new JSONObject(api.searchForBusinessesByLocation(lat, lon));
			JSONArray array = (JSONArray) response.get("businesses");

			List<JSONObject> list = new ArrayList<>();
			Set<String> visited = restaurantDAO.getVisitedRestaurants(userId);

			for (int i = 0; i < array.length(); i++) {
				JSONObject inputObject = array.getJSONObject(i);
				// Clean and purify
				Restaurant restaurant = new Restaurant(inputObject);
				// return clean restaurant objects
				JSONObject outputObject = restaurant.toJSONObject();

				String businessId = restaurant.getBusinessId();
				if (visited.contains(businessId)) {
					outputObject.put("is_visited", true);
				} else {
					outputObject.put("is_visited", false);
				}

				// save restaurant into DB
				restaurantDAO.saveRestaurant(restaurant);
				
//				Session session = sessionFactory.getCurrentSession();
//				// session.beginTransaction();
//
//				session.saveOrUpdate(restaurant);

				// session.getTransaction().commit();

				list.add(outputObject);
			}
			return new JSONArray(list);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

}

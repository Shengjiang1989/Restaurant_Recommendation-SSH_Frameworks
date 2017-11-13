package dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import entity.Restaurant;
import yelp.YelpAPI;

@Repository
public class RestaurantDAOImpl implements RestaurantDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Restaurant> getRestaurant(String userId, double lat, double lon) {
		try {
			// Connect to Yelp API
			YelpAPI api = new YelpAPI();
			JSONObject response = new JSONObject(api.searchForBusinessesByLocation(lat, lon));
			JSONArray array = (JSONArray) response.get("businesses");

			List<Restaurant> list = new ArrayList<>();

			for (int i = 0; i < array.length(); i++) {
				JSONObject object = array.getJSONObject(i);
				// Clean and purify
				Restaurant restaurant = new Restaurant(object);
				// return clean restaurant objects
				list.add(restaurant);
			}
			return list;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Transactional
	public JSONArray searchRestaurants(String userId, double lat, double lon) {
		try {
			// Connect to Yelp API
			YelpAPI api = new YelpAPI();
			JSONObject response = new JSONObject(api.searchForBusinessesByLocation(lat, lon));
			JSONArray array = (JSONArray) response.get("businesses");

			List<JSONObject> list = new ArrayList<>();
			Set<String> visited = getVisitedRestaurants(userId);

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
				Session session = sessionFactory.getCurrentSession();
				//session.beginTransaction();

				session.save(restaurant);

				//session.getTransaction().commit();

				list.add(outputObject);
			}
			return new JSONArray(list);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public void setVisitedRestaurants(String userId, List<String> businessIds) {
		
		System.out.print(userId);
	}

	@Override
	public void unsetVisitedRestaurants(String userId, List<String> businessIds) {
		// TODO Auto-generated method stub

	}

	@Override
	public Set<String> getVisitedRestaurants(String userId) {
		Set<String> visitedRestaurants = new HashSet<String>();
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<Restaurant> theQuery = currentSession.createQuery("select businessId from History where user_id = " + userId, Restaurant.class);
		
		List<Restaurant> Restaurants = theQuery.getResultList();
		
		for (Restaurant s : Restaurants) {
			visitedRestaurants.add(s.getBusinessId());
		}
		
		return visitedRestaurants;

	}

	@Override
	public JSONObject getRestaurantsById(String businessId, boolean isVisited) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONArray recommendRestaurants(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> getCategories(String businessId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> getBusinessId(String category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean verifyLogin(String userId, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getFirstLastName(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

}

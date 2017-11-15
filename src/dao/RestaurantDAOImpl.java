package dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import entity.History;
import entity.Restaurant;
import entity.User;
import yelp.YelpAPI;

@Repository
@Transactional
public class RestaurantDAOImpl implements RestaurantDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	private static final int MAX_RECOMMENDED_RESTAURANTS = 10;

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
				// session.beginTransaction();

				session.saveOrUpdate(restaurant);

				// session.getTransaction().commit();

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
		Session currentSession = sessionFactory.getCurrentSession();
		User curUser = currentSession.get(User.class, userId);
		for (String businessId : businessIds) {
			Restaurant curRestaurant = currentSession.get(Restaurant.class, businessId);
			History curHistory = new History(curUser, curRestaurant);
			currentSession.save(curHistory);
		}
	}

	@Override
	public void unsetVisitedRestaurants(String userId, List<String> businessIds) {

		Session currentSession = sessionFactory.getCurrentSession();
		// User curUser = currentSession.get(User.class, userId);

		for (String businessId : businessIds) {

			Query query = currentSession
					.createQuery("DELETE FROM History as h WHERE h.user.userId = :userId"
							+ " and h.restaurant.businessId = :businessId")
					.setParameter("userId", userId).setParameter("businessId", businessId);
			query.executeUpdate();
		}
	}

	@Override
	public Set<String> getVisitedRestaurants(String userId) {
		Set<String> visitedRestaurants = new HashSet<String>();

		Session currentSession = sessionFactory.getCurrentSession();

		User curUser = currentSession.get(User.class, userId);

		// System.out.println(curUser);
		List<String> restaurants = currentSession
				.createQuery("select restaurant.businessId from History where user.userId = :userId", String.class)
				.setParameter("userId", userId).getResultList();

		// List<Restaurant> Restaurants = theQuery.getResultList();
//		List<History> histories = curUser.getHistories();
//
		for (String s : restaurants) {
			visitedRestaurants.add(s);
		}

		return visitedRestaurants;

	}

	@Override
	public JSONObject getRestaurantsById(String businessId, boolean isVisited) {
		Session currentSession = sessionFactory.getCurrentSession();

		try {
			Restaurant restaurant = currentSession.get(Restaurant.class, businessId);

			JSONObject obj = restaurant.toJSONObject();
			obj.put("is_visited", isVisited);
			return obj;
		} catch (Exception e) { /* report an error */
			System.out.println(e.getMessage());
		}
		return null;

	}

	@Override
	public JSONArray recommendRestaurants(String userId) {
		try {
			Set<String> visitedRestaurants = getVisitedRestaurants(userId);// step 1
			Set<String> allCategories = new HashSet<>();// why hashSet? //step 2
			for (String restaurant : visitedRestaurants) {
				allCategories.addAll(getCategories(restaurant));
			}
			Set<String> allRestaurants = new HashSet<>();// step 3
			for (String category : allCategories) {
				Set<String> set = getBusinessId(category);
				allRestaurants.addAll(set);
			}
			Set<JSONObject> diff = new HashSet<>();// step 4
			int count = 0;
			for (String businessId : allRestaurants) {
				// Perform filtering
				if (!visitedRestaurants.contains(businessId)) {
					diff.add(getRestaurantsById(businessId, false));
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

	@Override
	public Set<String> getCategories(String businessId) {
		
		try {
			Session currentSession = sessionFactory.getCurrentSession();

			Restaurant restaurant = currentSession.get(Restaurant.class, businessId);
			
				Set<String> set = new HashSet<>();
				String[] categories = restaurant.getCategories().split(",");
				for (String category : categories) {
					// ' Japanese ' -> 'Japanese'
					set.add(category.trim());
				}
				return set;

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return new HashSet<String>();

	}

	@Override
	public Set<String> getBusinessId(String category) {
		Set<String> set = new HashSet<>();
		try {
			// if category = Chinese, categories = Chinese, Korean, Japanese,
			// it's a match
			Session currentSession = sessionFactory.getCurrentSession();
			String hql = "SELECT businessId from Restaurant WHERE categories LIKE :category";
			List<String> list = currentSession
					.createQuery(hql, String.class)
					.setParameter("category", "%" + category + "%").getResultList();
			
			for (String s : list) {
				set.add(s);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return set;

	}

	@Override
	public Boolean verifyLogin(String userId, String password) {

		Session session = sessionFactory.getCurrentSession();

		User user = session.get(User.class, userId);
		return user.getPassword() == password ? true : false;

	}

	@Override
	public String getFirstLastName(String userId) {
		String name = "";

		Session session = sessionFactory.getCurrentSession();
		User user = session.get(User.class, userId);
		name += user.getFirstName() + " " + user.getLastName();

		return name;

	}

}

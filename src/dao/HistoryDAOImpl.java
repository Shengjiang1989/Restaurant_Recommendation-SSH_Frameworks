package dao;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import entity.History;
import entity.Restaurant;
import entity.User;

@Repository
public class HistoryDAOImpl implements HistoryDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void setVisitedRestaurants(String userId, List<String> visitedRestaurants) {
		Session currentSession = sessionFactory.getCurrentSession();
		User curUser = currentSession.get(User.class, userId);
		for (String businessId : visitedRestaurants) {
			Restaurant curRestaurant = currentSession.get(Restaurant.class, businessId);
			History curHistory = new History(curUser, curRestaurant);
			currentSession.save(curHistory);
		}
		
	}

	@Override
	public void unsetVisitedRestaurants(String userId, List<String> visitedRestaurants) {
		Session currentSession = sessionFactory.getCurrentSession();
		// User curUser = currentSession.get(User.class, userId);

		for (String businessId : visitedRestaurants) {

			Query query = currentSession
					.createQuery("DELETE FROM History as h WHERE h.user.userId = :userId"
							+ " and h.restaurant.businessId = :businessId")
					.setParameter("userId", userId).setParameter("businessId", businessId);
			query.executeUpdate();
		}
		
	}

}

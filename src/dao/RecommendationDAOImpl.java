package dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import entity.Restaurant;

@Repository
public class RecommendationDAOImpl implements RecommendationDAO {

	@Autowired
	private SessionFactory sessionFactory;

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
}

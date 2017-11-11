package dao;

import java.util.List;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import entity.Restaurant;

@Repository
public class RestaurantDAOImpl implements RestaurantDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	@Transactional
	public List<Restaurant> getRestaurant() {
		// TODO Auto-generated method stub
		Session curSession = sessionFactory.getCurrentSession();
		
		Query<Restaurant> theQuery = 
				curSession.createQuery("from Restaurant", Restaurant.class);
		
		List<Restaurant> res = theQuery.getResultList();
		
		return res;
	}

}

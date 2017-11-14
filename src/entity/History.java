package entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/*
visit_history_id bigint(20) unsigned NOT NULL AUTO_INCREMENT, "
					+ " user_id VARCHAR(255) NOT NULL , " + " business_id VARCHAR(255) NOT NULL, "
					+ " last_visited_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP, "
					+ " PRIMARY KEY (visit_history_id),"
					+ "FOREIGN KEY (business_id) REFERENCES restaurants(business_id),"
					+ "FOREIGN KEY (user_id) REFERENCES users(user_id) 
*/

@Entity
@Table(name="history")
public class History {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="visit_history_id")
	private int visitHistoryId;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name="business_id")
	private Restaurant restaurant;
	
	@Column(name="last_visited_time")
	private Timestamp lastVisitedTime;
	
	//constructor
	public History() {}
	
	public History(User user, Restaurant restaurant) {
		this.user = user;
		this.restaurant = restaurant;
		
		List<History> historyForUser = user.getHistories();
		if (historyForUser == null)
			historyForUser = new ArrayList<>();
		historyForUser.add(this);
		
		List<History> historyForRest = user.getHistories();
		if (historyForRest == null)
			historyForRest = new ArrayList<>();
		historyForRest.add(this);
	}



	//getter and setter
	public int getVisitHistoryId() {
		return visitHistoryId;
	}

	public void setVisitHistoryId(int visitHistoryId) {
		this.visitHistoryId = visitHistoryId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User userId) {
		this.user = userId;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant businessId) {
		this.restaurant = businessId;
	}

	public Timestamp getLastVisitedTime() {
		return lastVisitedTime;
	}

	public void setLastVisitedTime(Timestamp lastVisitedTime) {
		this.lastVisitedTime = lastVisitedTime;
	};
	
	
}

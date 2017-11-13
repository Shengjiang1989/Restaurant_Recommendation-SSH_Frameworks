package entity;

import java.sql.Timestamp;

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
	private User userId;
	
	@ManyToOne
	@JoinColumn(name="business_id")
	private Restaurant businessId;
	
	@Column(name="last_visited_time")
	private Timestamp lastVisitedTime;
	
	//constructor
	public History() {}
	
	//getter and setter
	public int getVisitHistoryId() {
		return visitHistoryId;
	}

	public void setVisitHistoryId(int visitHistoryId) {
		this.visitHistoryId = visitHistoryId;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	public Restaurant getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Restaurant businessId) {
		this.businessId = businessId;
	}

	public Timestamp getLastVisitedTime() {
		return lastVisitedTime;
	}

	public void setLastVisitedTime(Timestamp lastVisitedTime) {
		this.lastVisitedTime = lastVisitedTime;
	};
	
	
}

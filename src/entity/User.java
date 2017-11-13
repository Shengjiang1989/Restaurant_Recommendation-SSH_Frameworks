package entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/*
sql = "CREATE TABLE users " 
		+ "(user_id VARCHAR(255) NOT NULL, " 
		+ " password VARCHAR(255) NOT NULL, "
		+ " first_name VARCHAR(255), last_name VARCHAR(255), " 
		+ " PRIMARY KEY ( user_id ))";
*/

@Entity
@Table(name="users")
public class User {
	
	@Id
	@Column(name="user_id")
	private String userId;
	
	@Column(name="password")
	private String password;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@OneToMany(mappedBy="userId")
	private List<History> histories;
	
	//constructor
	public User() {}

	//getter and setter
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<History> getHistories() {
		return histories;
	}

	public void setHistories(List<History> histories) {
		this.histories = histories;
	};
	
	
}

package dao;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.json.JSONObject;

public interface HistoryDAO {

	void setVisitedRestaurants(String userId, List<String> visitedRestaurants);

	void unsetVisitedRestaurants(String userId, List<String> visitedRestaurants);
	
}

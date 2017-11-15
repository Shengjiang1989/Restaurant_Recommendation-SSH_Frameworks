package service;

import org.json.JSONArray;
import org.json.JSONObject;

public interface HistoryService {

	void addHistories(JSONObject input);

	void deleteHistories(JSONObject input);

	JSONArray getHistories(String userId);

}

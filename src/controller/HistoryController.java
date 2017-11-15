package controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dao.RestaurantDAO;
import service.HistoryService;

@Controller
@RequestMapping("/history")
public class HistoryController {

	@Autowired
	private HistoryService historyService;

	@PostMapping()
	public void addHistories(@RequestBody String jsonString, PrintWriter printWriter) {

		try {
			JSONObject input = new JSONObject(jsonString);
			if (input.has("user_id") && input.has("visited")) {
				historyService.addHistories(input);
				printWriter.print(new JSONObject().put("status", "OK"));
			} else {
				printWriter.print(new JSONObject().put("status", "InvalidParameter"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			printWriter.close();
		}
	}

	@DeleteMapping()
	public void deleteHistory(@RequestBody String jsonString, PrintWriter printWriter) {
		try {
			JSONObject input = new JSONObject(jsonString);
			if (input.has("user_id") && input.has("visited")) {
				historyService.deleteHistories(input);
				printWriter.print(new JSONObject().put("status", "OK"));
			} else {
				printWriter.print(new JSONObject().put("status", "InvalidParameter"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			printWriter.close();
		}
	}

	@GetMapping()
	public void getHistory(@RequestParam(value = "user_id") String userId, PrintWriter printWriter) {

		// allow access only if session exists
		/*
		 * if (!RpcParser.sessionValid(request, connection)) { response.setStatus(403);
		 * return; }
		 */
		JSONArray array = historyService.getHistories(userId);
		printWriter.print(array);
		printWriter.flush();
		printWriter.close();
	}
}

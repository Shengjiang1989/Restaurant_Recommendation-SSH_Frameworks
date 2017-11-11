package controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/restaurant")
public class RestaurantController {
	
	
	@GetMapping("/list")
	public String listCustomers(Model theModel) {
		
		return "list-restaurant";
	}
}

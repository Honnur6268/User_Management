package com.user.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.user.entity.UserAccount;
import com.user.service.UserAccountService;

@Controller
public class UserAccountController {

	Logger logger = LoggerFactory.getLogger(UserAccountController.class);

	@Autowired
	private UserAccountService service;

	private static final String INDEX = "index";

	private static final String USER_ID = "User ID ";

	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("user", new UserAccount());
		return INDEX;
	}

	@PostMapping("/save-user")
	public String handleSubmitBtn(@ModelAttribute("user") UserAccount user, Model model) {
		logger.info("handle User: " + user.getUserId());
		String msg = service.saveOrUpdateUserAcc(user);
		model.addAttribute("msg", msg);
		model.addAttribute("user", new UserAccount());
		return INDEX;
	}

	@GetMapping("/users")
	public String getUsers(Model model) {
		List<UserAccount> usersList = service.getAllUserAccounts();
		model.addAttribute("users", usersList);
		return "view-users";
	}

	@GetMapping("/edit-user")
	public String editUser(@RequestParam("userId") Integer userId, Model model) {
		UserAccount user = service.getUserAcc(userId);
		model.addAttribute("user", user);
		return INDEX;
	}

	@GetMapping("/delete")
	public String deleteUser(@RequestParam("userId") Integer userId, Model model) {
		boolean deleteUserAcc = service.deleteUserAcc(userId);
		if (deleteUserAcc)
			model.addAttribute("msg", USER_ID + userId + "  Deleted Permanently");
		else
			model.addAttribute("msg", "Error deleting User ID" + userId + "");
		return "forward:/users";
	}

	@GetMapping("/updateStatus")
	public String changeAccStatus(@RequestParam Integer userId, @RequestParam String status, Model model) {

		service.updateUserAccStatus(userId, status);
		if (status.equalsIgnoreCase("Y"))
			model.addAttribute("msg", USER_ID + userId + " Activated Successfully");
		else
			model.addAttribute("msg", USER_ID + userId + "  De-Activated Successfully");

		return "forward:/users";
	}

}

package com.user.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.entity.UserAccount;
import com.user.repo.UserAccountRepo;
import com.user.service.UserAccountService;

@Service
public class UserAccountServiceImpl implements UserAccountService {

	@Autowired
	private UserAccountRepo repo;

	@Override
	public String saveOrUpdateUserAcc(UserAccount userAcc) {
		Integer userId = userAcc.getUserId();

		// UPSERT (Insert and Update)
		repo.save(userAcc);
		if ( userId == null) {
			
			return "User Record Saved";
		} 
		else {
			
			return "User Record Updated";
		}
		
	}

	@Override
	public List<UserAccount> getAllUserAccounts() {
		return repo.findAll();
	}

	@Override
	public UserAccount getUserAcc(Integer userId) {
		Optional<UserAccount> findById = repo.findById(userId);
		if (findById.isPresent()) {
			return findById.get();
		}
		return null;
	}

	@Override
	public boolean deleteUserAcc(Integer userId) {
		boolean existsById = repo.existsById(userId);
		if (existsById) {
			repo.deleteById(userId);
			return true;
		}
		return false;
	}

	@Override
	public boolean updateUserAccStatus(Integer userId, String status) {
		try {
			repo.updateUserAccStatus(userId, status);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

}

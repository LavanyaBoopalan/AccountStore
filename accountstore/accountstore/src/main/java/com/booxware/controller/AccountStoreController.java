package com.booxware.controller;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booxware.common.AccountStoreException;
import com.booxware.dto.Account;
import com.booxware.service.AccountStoreService;

/**
 * Controller for Account Store service 
 */
@RestController
public class AccountStoreController {
	
	private static final Logger logger = Logger
			.getLogger(AccountStoreController.class.getName());

	@Autowired
	AccountStoreService acctStoreService;

	/**
	 * Method to create an account
	 * 
	 * @param userName
	 *            -Name of the user
	 * @param password
	 *            -Password of the user
	 * @param email
	 *            -email of the user
	 * @return ResponseEntity 
	 *            -returns account id in case of successful account
	 *             creation, else returns the error message.
	 * 
	 */
	@GetMapping("/create/{userName}/{password}/{email}")
	public ResponseEntity<String> createAccount(
			@PathVariable("userName") String userName,
			@PathVariable("password") String password,
			@PathVariable("email") String email) {
		if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)
				|| StringUtils.isEmpty(email)) {
			logger.info("Username, email and password fields are mandatory to create an account");
			return new ResponseEntity<String>(
					"Username, email and password fields are mandatory to create an account",
					HttpStatus.OK);
		}
		Account acct = null;
		try {
			acct = acctStoreService.createAccount(userName, email, password);
		} catch (AccountStoreException e) {
			logger.error("Exception occured while creating an account: "
					+ e.getMessage());
			return new ResponseEntity<String>(e.toString(), HttpStatus.OK);
		}
		logger.info("Account created successfully for User:" + userName
				+ ", Account ID:" + acct.getId());
		return new ResponseEntity<String>("Account ID:"
				+ String.valueOf(acct.getId()), HttpStatus.OK);
	}

	/**
	 * Method to update account
	 * 
	 * @param userName
	 *            -Name of the user
	 * @param password
	 *            -Password of the user
	 * @return ResponseEntity 
	 *            - returns the account updated time in case of
	 *            successful update, else returns the error message.
	 */
	@PutMapping("/update/{userName}/{password}")
	public ResponseEntity<String> updateAccount(
			@PathVariable("userName") String userName,
			@PathVariable("password") String password) {
		if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
			logger.info("Username and password are mandatory to update an account");
			return new ResponseEntity<String>(
					"Username and password are mandatory to update an account",
					HttpStatus.OK);
		}
		Account acct;
		try {
			acct = acctStoreService.updateAccount(userName, password);
		} catch (AccountStoreException e) {
			logger.error("Exception occured while updating an account: "
					+ e.getMessage());
			return new ResponseEntity<String>(e.toString(), HttpStatus.OK);
		}
		logger.info("Account updated successfully for User:" + userName
				+ ", Account updated time is:" + acct.getLastLogin());
		return new ResponseEntity<String>("Account updated time:"
				+ acct.getLastLogin(), HttpStatus.OK);
	}

	/**
	 * Method to delete an account
	 * 
	 * @param userName
	 *            - Name of the user
	 * @return ResponseEntity 
	 *            - returns the success message in case of
	 *            successful delete, else returns the error message
	 */
	@DeleteMapping("/delete/{userName}")
	public ResponseEntity<String> deleteAccount(String userName) {
		if (StringUtils.isEmpty(userName)) {
			logger.info("Username is mandatory to delete an account");
			return new ResponseEntity<String>(
					"Username is mandatory to delete an account", HttpStatus.OK);
		}
		try {
			acctStoreService.deleteAccount(userName);
		} catch (AccountStoreException e) {
			logger.error("Exception occured while deleting an account: "
					+ e.getMessage());
			return new ResponseEntity<String>(e.toString(), HttpStatus.OK);
		}
		logger.info("Account deleted Successfully for User:" + userName);
		return new ResponseEntity<String>("Account deleted Successfully",
				HttpStatus.OK);
	}

	/**
	 * Method to verify if the user has logged in since the given date
	 * 
	 * @param userName
	 *            - Name of the user
	 * @param date
	 *            - Date input given by user
	 * @return ResponseEntity 
	 *            - returns the user message with true if the user
	 *            has logged in , else returns false. if the mandatory fields are
	 *            not provided, returns the validation message
	 */
	@GetMapping("/logindetails/{userName}/{date}")
	public ResponseEntity<String> hasLoggedInSince(String userName, Date date) {
		if (StringUtils.isEmpty(userName) || null == date) {
			logger.info("Username and Date is mandatory to verify the last login");
			return new ResponseEntity<String>(
					"Username and Date is mandatory to verify the last login",
					HttpStatus.OK);
		}
		Boolean hasLoggedIn;
		try {
			hasLoggedIn = acctStoreService.hasLoggedInSince(userName, date);
		} catch (AccountStoreException e) {
			logger.error("Exception occured while verifying the login since: "
					+ e.getMessage());
			return new ResponseEntity<String>(e.toString(), HttpStatus.OK);
		}
		logger.info("User login status since" + date + ":" + hasLoggedIn
				+ "for user:" + userName);
		return new ResponseEntity<String>("User login status since" + date
				+ ":" + hasLoggedIn, HttpStatus.OK);
	}

}

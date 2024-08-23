package com.booxware.service;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import com.booxware.common.AccountStoreException;
import com.booxware.dao.AccountStoreDao;
import com.booxware.dto.Account;

/**
 * Implementation for AccountStoreService.This class contains business logic for the
 * following functionalities: 
 * a)create, update and delete an account.
 * b)whether the user has logged in since the given date.
 *
 */
@Service("acctStoreService")
public class AccountStoreServiceImpl implements AccountStoreService {

	@Autowired
	AccountStoreDao accountStoreDao;

	private static final Logger logger = Logger
			.getLogger(AccountStoreServiceImpl.class.getName());

	/**
	 * Create a new Account for an user.Validates whether the given userName is
	 * new and then creates an account for that user.if the userName already
	 * exist, then throws an exception.
	 * 
	 * @param userName
	 *            - Name of the user
	 * @param email
	 *            - Email address of the user
	 * @param password
	 *            - password in clear text format
	 * @return Account - New account that is created
	 * @throws AccountStoreException
	 *             - throws error if the userName already exist
	 */
	public Account createAccount(String userName, String password, String email)
			throws AccountStoreException {
		Account acct = accountStoreDao.findByName(userName);
		/* if userName already exist in the system, throw
		  AccountServiceException */
		if (null != acct) {
			logger.info("UserName already exist:" + userName);
			throw new AccountStoreException(
					"UserName already exist. Please provide different UserName");
		}
		// create the account if given userName does not exist already
		Account generatedAccount = new Account(userName,
				encryptPassword(password), email, new Date());
		Account savedAccount = accountStoreDao.save(generatedAccount);
		logger.info("Account created Successfully for:" + userName);
		return savedAccount;
	}

	/**
	 * Validates whether the given userName exist and password is correct. if
	 * the validation is successful, updates the last login date of the user. if
	 * the validation fails, then throws an exception.
	 * 
	 * @param userName
	 *            - Name of the user
	 * @param password
	 *            - password in clear text format
	 * @return Account 
	 *            - login account of the user
	 * @throws AccountStoreException
	 *            - throws error if the userName does not exist or if the
	 *             provided password does not match
	 */
	public Account updateAccount(String userName, String password)
			throws AccountStoreException {
		Account acct = findbyUserName(userName);
		if (null != acct.getEncryptedPassword()) {
			//decrypt the stored password and compare against the user given password 
			String decryptedPassword = decryptPassword(acct.getEncryptedPassword());
			if (decryptedPassword.equals(password)) {
				//if the password matches, update the login date of the user
				acct.setLastLogin(new Date());
				logger.info("Account updated successully for:"+ userName);
			} else {
				logger.info("Account update failed due to invalid password for User:"
						+ userName + "Password:" + password);
				throw new AccountStoreException("Password is invalid");
			}
		}
		return acct;
	}

	/**
	 * Delete the user's account. Verifies if the given userName exist and then
	 * deletes the account.
	 * 
	 * @param userName
	 *            - Name of the user
	 * @throws AccountStoreException
	 *            - throws error if the userName does not exist
	 */
	public void deleteAccount(String userName) throws AccountStoreException {
		//find the account based on userName and delete the user account
		Account acct = findbyUserName(userName);
		accountStoreDao.delete(acct);
		logger.info("Account deleted successully for:" + userName);
	}

	/**
	 * Validates whether the user has logged in since the given date
	 * 
	 * @param userName
	 *            - Name of the user
	 * @param date
	 *            - Date input given by user
	 * @return boolean - returns true if user has logged in since the given
	 *         date, returns false if user has logged in since the given date
	 * @throws AccountStoreException
	 *             - throws error if the userName does not exist
	 */
	public boolean hasLoggedInSince(String userName, Date date)
			throws AccountStoreException {
		Account acct = findbyUserName(userName);
		if (null != acct) {
			return acct.getLastLogin().after(date);
		}
		return true;
	}

	/**
	 * Retrieves the account by userName.This method is used internally by other
	 * implementation methods
	 * 
	 * @param userName
	 *            - Name of the user
	 * @return Account - Account of the user
	 * @throws AccountStoreException
	 *             - throws an exception,if the UserName is invalid
	 */
	protected Account findbyUserName(String userName)
			throws AccountStoreException {
		Account acct = accountStoreDao.findByName(userName);
		if (null == acct) {
			logger.info("Account is not found for :"+ userName);
			throw new AccountStoreException("UserName is invalid");
		}
		return acct;
	}

	/**
	 * Encrypts the user provided password using Base64 encoder
	 * 
	 * @param password
	 *            - user provided password
	 * @return byte[] 
	 *            - encrypted password
	 */
	public static byte[] encryptPassword(String password) {
		byte[] bytes = password.getBytes();
		return Base64Utils.encode(bytes);
	}

	/**
	 * Decrypts the stored password using Base64 decoder
	 * 
	 * @param password
	 *            - encrypted password that is stored
	 * @return String 
	 *            - return decrypted password
	 */
	public static String decryptPassword(byte[] password) {
		byte[] bytes = Base64Utils.decode(password);
		String s = new String(bytes);
		return s;
	}

}

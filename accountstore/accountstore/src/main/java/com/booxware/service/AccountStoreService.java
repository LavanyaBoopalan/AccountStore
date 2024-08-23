package com.booxware.service;

import java.util.Date;

import com.booxware.common.AccountStoreException;
import com.booxware.dto.Account;

/**
 * Service for Account Management
 *
 */
public interface AccountStoreService {

	/**
	 * Logs in the user, if the username exists and the password is correct.
	 * Updates the last login date
	 * 
	 * @param username
	 *            the User's name
	 * @param password
	 *            the clear text password
	 * @return the logged in account
	 * 
	 * @throws AccountStoreException
	 *             if any errors occur
	 */
	public Account updateAccount(String username, String password)
			throws AccountStoreException;

	/**
	 * Registers a new Account, if the username doesn't exist yet and logs in
	 * the user.
	 * 
	 * @param username
	 *            the User's name
	 * @param email
	 *            the email address of the user
	 * @param password
	 *            the clear text password
	 * @return the newly registered Account
	 * 
	 * @throws AccountStoreException
	 *             if any errors occur
	 */
	public Account createAccount(String username, String email, String password)
			throws AccountStoreException;

	/**
	 * Deletes an Account, if the user exist.
	 * 
	 * @param username
	 *            the User's name
	 * 
	 * @throws AccountStoreException
	 *             if any errors occur
	 */
	public void deleteAccount(String userName)
			throws AccountStoreException;

	/**
	 * Checks if a user has logged in since a provided timestamp.
	 * 
	 * @param date
	 * @return true if the user has logged in since the provided timestamp, else
	 *         false.
	 * @throws AccountStoreException
	 *             if any error occurs
	 */
	public boolean hasLoggedInSince(String userName, Date date)
			throws AccountStoreException;

}

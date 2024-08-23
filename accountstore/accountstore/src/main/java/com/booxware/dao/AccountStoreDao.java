package com.booxware.dao;

import com.booxware.dto.Account;

/**
 * Interface to access AccountStore data
 *
 */
public interface AccountStoreDao {

	/**
	 * Method to save an account
	 * 
	 * @param Account
	 *            - Account to be saved in repository
	 * @return Account
	 *            - Account that is saved in repository with generated account id
	 */
	public Account save(Account account);

	/**
	 * Method to find an account by username
	 * 
	 * @param name
	 *            - user name of the account 
	 * @return Account
	 *            - Account that is saved for that user
	 */
	public Account findByName(String name);
	
	/**
	 * Method to delete an account
	 * 
	 * @param Account
	 *            - Account to be deleted
	 */
	public void delete(Account account);

}
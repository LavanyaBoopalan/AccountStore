package com.booxware.dao;

import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.booxware.dto.Account;

/**
 * Implementation class for AccountStoreDao that performs CRUD functions
 */
@Repository("accountStoreDao")
public class AccountStoreDaoImpl implements AccountStoreDao {
	//Account Id that is generated for each account
	private static long accountId = 1000;

	//repository to store the accounts
	protected static final TreeMap<String, Account> accountRepository = new TreeMap<String, Account>();
	
	private static final Logger logger = Logger
			.getLogger(AccountStoreDaoImpl.class.getName());
	
	/**
	 * Method to save an account
	 * 
	 * @param Account
	 *            - Account to be saved in repository
	 * @return Account
	 *            - Account that is saved in repository with generated account id
	 */
	public Account save(Account account) {
		account.setId(generateAccountId());
		accountRepository.put(account.getUserName(), account);
		logger.info("Account created successfully for user :"
				+ account.getUserName());
		return account;
	}

	/**
	 * Method to find an account by username
	 * 
	 * @param name
	 *            - user name of the account 
	 * @return Account
	 *            - Account that is saved for that user
	 */
	public Account findByName(String name) {
		Account account = accountRepository.get(name);
		logger.info("Account found successfully for user :" + name);
		return account;
	}

	/**
	 * Method to delete an account
	 * 
	 * @param Account
	 *            - Account to be deleted
	 */
	public void delete(Account account) {
		accountRepository.remove(account.getUserName());
		logger.info("Account deleted successfully for user :" + account.getUserName());
	}

	/**
	 * Method to generate account Id for an account
	 * 
	 * @return long
	 *            - The generated Account Id
	 */
	private long generateAccountId() {
		return accountId++;
		
	}


}

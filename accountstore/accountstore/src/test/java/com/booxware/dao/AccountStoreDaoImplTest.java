package com.booxware.dao;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.booxware.dto.Account;

/**
 * Test class for AccountStoreDao implementation
 */
public class AccountStoreDaoImplTest {

	AccountStoreDaoImpl daoImpl = new AccountStoreDaoImpl();
	Account acct = null;
	
	@Before
	public void setUp(){
		byte[] encryptedPassword = new String("secretpass").getBytes();
		acct = new Account("Alex", encryptedPassword,
				"alex@gmail.com", new Date());
	}
	
	/**
	 * Test Method that internally calls other test methods
	 */
	@Test
	public void testAccountStoreDaoImpl(){
		testSave();
		testSuccessfulFindByName();
		testFindByIncorrectName();
		testDelete();
	}
	
	/**
	 * Method to test create account
	 */
	private void testSave() {
		Account account = daoImpl.save(acct);
		assertTrue("Account not created", account.getId() != 0);
	}

	/**
	 * Method to search an account by username
	 */
	private void testSuccessfulFindByName() {
		Account acct = daoImpl.findByName("Alex");
		assertNotNull("Account could not be found with username", acct);
	}
	
	/**
	 * Method to search an account by incorrect username
	 */
	private void testFindByIncorrectName() {
		Account acct = daoImpl.findByName("Alex1");
		assertNull("Account found with incorrect username", acct);
	}
	
	/**
	 * Method to delete an account
	 */
	private void testDelete() {
		daoImpl.delete(acct);
		Account account = AccountStoreDaoImpl.accountRepository.get(acct.getUserName());
		assertNull("Account deletion failed", account);
	}
	
}

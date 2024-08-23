/**
 * 
 */
package com.booxware.controller;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import com.booxware.common.AccountStoreException;
import com.booxware.dto.Account;
import com.booxware.service.AccountStoreService;

/**
 * Test for AccountStoreController
 */
@RunWith(MockitoJUnitRunner.class)
public class AccountStoreControllerTest {

	@Mock
	AccountStoreService acctStoreServiceMock;

	@InjectMocks
	AccountStoreController accountStoreController;
	
	//accountMock
	private static Account accountMock = null;
	
	@BeforeClass
	public static void prepareAccount() {
		String password = "secretpass";
		accountMock = new Account("Alex", password.getBytes(),
				"alex@gmail.com", new Date());
		accountMock.setId(1000);
	}
	

	/**
	 * Test method for successful scenario of CreateAccount
	 */
	@Test
	public final void testSuccessCreateAccount() throws AccountStoreException {
		when(
				acctStoreServiceMock.createAccount(anyString(), anyString(),
						anyString())).thenReturn(accountMock);
		ResponseEntity<String> response = accountStoreController.createAccount(
				"Alex", "secretpass", "alex@gmail.com");
		assertEquals("Account ID:" + 1000, response.getBody());
	}

	/**
	 * Test method for failure scenario of CreateAccount when user name is null
	 */
	public final void testCreateAccountWithNullUserName()
			 {
		ResponseEntity<String> response = accountStoreController.createAccount(null, "secretpass",
				"alex@gmail.com");
		assertEquals("Username, email and password fields are mandatory to create an account", response.getBody());
		
	}

	/**
	 * Test method for failure scenario of CreateAccount when user name is empty
	 */
	@Test
	public final void testCreateAccountWithEmptyUserName()
			throws AccountStoreException {
		ResponseEntity<String> response = accountStoreController
				.createAccount("", "secretpass", "alex@gmail.com");
		assertEquals(
				"Username, email and password fields are mandatory to create an account",
				response.getBody());
	}

	/**
	 * Test method for failure scenario of CreateAccount when password is null
	 */
	@Test
	public final void testCreateAccountWithNullPassword()
			throws AccountStoreException {
		ResponseEntity<String> response = accountStoreController.createAccount("Alex", null, "alex@gmail.com");
		assertEquals(
				"Username, email and password fields are mandatory to create an account",
				response.getBody());
	}

	/**
	 * Test method for failure scenario of CreateAccount when password is empty
	 */
	@Test
	public final void testCreateAccountWithEmptyPassword()
			throws AccountStoreException {
		ResponseEntity<String> response = accountStoreController.createAccount("Alex", "", "alex@gmail.com");
		assertEquals(
				"Username, email and password fields are mandatory to create an account",
				response.getBody());
	}
	
	/**
	 * Test method for failure scenario of CreateAccount when email is null
	 */
	@Test
	public final void testCreateAccountWithNullEmail()
			throws AccountStoreException {
		ResponseEntity<String> response = accountStoreController.createAccount("Alex", "secretpass", null);
		assertEquals(
				"Username, email and password fields are mandatory to create an account",
				response.getBody());
	}

	/**
	 * Test method for failure scenario of CreateAccount when email is empty
	 */
	@Test
	public final void testCreateAccountWithEmptyEmail()
			throws AccountStoreException {
		ResponseEntity<String> response = accountStoreController.createAccount("Alex", "secretpass", "");
		assertEquals(
				"Username, email and password fields are mandatory to create an account",
				response.getBody());
	}

	/**
	 * Test method for successful scenario of Update Account
	 */
	@Test
	public final void testSuccessUpdateAccount() throws AccountStoreException {
		when(acctStoreServiceMock.updateAccount(anyString(), anyString()))
				.thenReturn(accountMock);
		ResponseEntity<String> response = accountStoreController.updateAccount(
				"Alex", "secretpass");
		assertEquals("Account updated time:" + accountMock.getLastLogin(),
				response.getBody());

	}
	
	/**
	 * Test method for failure scenario of UpdateAccount when user name is null
	 */
	public final void testUpdateAccountWithNullUserName(){			 
		ResponseEntity<String> response = accountStoreController.updateAccount(null, "secretpass");
		assertEquals("Username and password are mandatory to update an account", response.getBody());
	}

	/**
	 * Test method for failure scenario of UpdateAccount when user name is empty
	 */
	@Test
	public final void testUpdateAccountWithEmptyUserName(){
		ResponseEntity<String> response = accountStoreController.updateAccount("", "secretpass");
		assertEquals("Username and password are mandatory to update an account", response.getBody());
	}

	/**
	 * Test method for failure scenario of UpdateAccount when password is null
	 */
	@Test
	public final void testUpdateAccountWithNullPassword()
			throws AccountStoreException {
		ResponseEntity<String> response = accountStoreController.updateAccount("Alex", null);
		assertEquals("Username and password are mandatory to update an account", response.getBody());
	}

	/**
	 * Test method for failure scenario of UpdateAccount when password is empty
	 */
	@Test
	public final void testUpdateAccountWithEmptyPassword()
			throws AccountStoreException {
		ResponseEntity<String> response = accountStoreController.updateAccount("Alex", "");
		assertEquals("Username and password are mandatory to update an account", response.getBody());
	}

	/**
	 * Test method for successful delete account
	 */
	@Test
	public final void testDeleteAccount() {
		ResponseEntity<String> response = accountStoreController.deleteAccount("Alex");
		assertEquals("Account deleted Successfully", response.getBody());
	}
	
	/**
	 * Test method for failure scenario of delete account when user name is null
	 */
	public final void testDeleteAccountWithNullUserName(){			 
		ResponseEntity<String> response = accountStoreController.deleteAccount(null);
		assertEquals("Username is mandatory to delete an account", response.getBody());
	}

	/**
	 * Test method for failure scenario of delete account when user name is empty
	 */
	@Test
	public final void testDeleteAccountWithEmptyUserName(){
		ResponseEntity<String> response = accountStoreController.deleteAccount("");
		assertEquals("Username is mandatory to delete an account", response.getBody());
	}

	/**
	 * Test method for successful scenario of HasLoggedInSince
	 */
	@Test
	public final void testSuccessHasLoggedInSince()
			throws AccountStoreException {
		Date date = new Date();
		when(acctStoreServiceMock.hasLoggedInSince("Alex", date))
				.thenReturn(true);
		ResponseEntity<String> response = accountStoreController
				.hasLoggedInSince("Alex", date);
		assertEquals("User login status since" + date + ":" + true,
				response.getBody());
	}
	
	/**
	 * Test method for failure scenario of HasLoggedInSince when user name is null
	 */
	public final void testHasLoggedInSinceWithNullUserName(){			 
		ResponseEntity<String> response = accountStoreController.hasLoggedInSince(null, new Date());
		assertEquals("Username and Date is mandatory to verify the last login", response.getBody());
	}

	/**
	 * Test method for failure scenario of HasLoggedInSince when user name is empty
	 */
	@Test
	public final void testHasLoggedInSinceWithEmptyUserName(){
		ResponseEntity<String> response = accountStoreController.hasLoggedInSince("", new Date());
		assertEquals("Username and Date is mandatory to verify the last login", response.getBody());
	}
	
}

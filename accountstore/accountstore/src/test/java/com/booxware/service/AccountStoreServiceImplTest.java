package com.booxware.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.booxware.common.AccountStoreException;
import com.booxware.dao.AccountStoreDao;
import com.booxware.dto.Account;

/**
 * Test class for AccountStoreServiceImpl
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class AccountStoreServiceImplTest {
	@Mock
	private AccountStoreDao accountStoreDao;

	@InjectMocks
	private AccountStoreServiceImpl acctStoreServiceImpl = new AccountStoreServiceImpl();

	// prepare a test account
	private static final Account accountMock = getMockAccount();
	
	/**
	 * Method to prepare mock account
	 */
	public static Account getMockAccount() {
		byte[] encryptedPassword = AccountStoreServiceImpl.encryptPassword("secretpass");
		Account account = new Account("Alex", encryptedPassword, "alex@gmail.com",
				new Date());
		return account;
	}

	/**
	 * To test the successful scenario of create account
	 */
	@Test
	public void testSuccessCreateAccount() throws AccountStoreException {
		when(this.accountStoreDao.findByName("Alex")).thenReturn(null);
		try {
			acctStoreServiceImpl.createAccount("Alex", "secretpass",
					"alex@gmail.com");
		} catch (AccountStoreException e) {
			assertTrue("Account creation failed", false);
		}
	}

	/**
	 * To test the failure scenario of create account when user already exist
	 */
	@Test(expected = AccountStoreException.class)
	public void testCreateAccountWithUserAlreadyExist()
			throws AccountStoreException {
		when(this.accountStoreDao.findByName("Alex")).thenReturn(accountMock);
		acctStoreServiceImpl.createAccount("Alex", "secretpass",
				"alex@gmail.com");
	}

	/**
	 * To test the successful scenario of update account
	 */
	@Test
	public void testSuccessUpdateAccount() throws AccountStoreException {
		when(this.accountStoreDao.findByName("Alex")).thenReturn(accountMock);
		Account acct = acctStoreServiceImpl.updateAccount("Alex", "secretpass");
		assertNotNull("Account update failed", acct);
	}

	/**
	 * To test the failure scenario of update account when provided user does
	 * not exist
	 */
	@Test(expected = AccountStoreException.class)
	public void testUpdateAccountwithUserNotExist()
			throws AccountStoreException {
		when(this.accountStoreDao.findByName("Alex")).thenReturn(null);
		acctStoreServiceImpl.updateAccount("Alex", "qwertz");
	}

	/**
	 * To test the failure scenario of update account when provided password is
	 * incorrect
	 */
	@Test(expected = AccountStoreException.class)
	public void testUpdateAccountwithIncorrectPassword()
			throws AccountStoreException {
		when(this.accountStoreDao.findByName("Alex")).thenReturn(accountMock);
		acctStoreServiceImpl.updateAccount("Alex", "wrongpass");
	}

	/**
	 * To test the successful scenario of delete account
	 */
	@Test
	public void testSuccessDeleteAccount() throws AccountStoreException {
		when(this.accountStoreDao.findByName("Alex")).thenReturn(accountMock);
		acctStoreServiceImpl.deleteAccount("Alex");
		verify(this.accountStoreDao).delete(accountMock);
	}

	/**
	 * To test the failure scenario of update account when provided user does
	 * not exist
	 */
	@Test(expected = AccountStoreException.class)
	public void testDeleteAccountwithUserNotExist()
			throws AccountStoreException {
		when(this.accountStoreDao.findByName("Alex")).thenReturn(null);
		acctStoreServiceImpl.deleteAccount("Alex");
	}

	/**
	 * To test the successful scenario to find if the user has logged in from
	 * the since date
	 */
	@Test
	public void testSuccessHasUserLoggedInSince() throws AccountStoreException {
		when(this.accountStoreDao.findByName("Alex")).thenReturn(accountMock);
		boolean hasloggedin = acctStoreServiceImpl.hasLoggedInSince("Alex", getDate(-1));
		assertTrue("User has logged in", hasloggedin);
	}

	/**
	 * To test the successful scenario to find if the user has logged in from
	 * the since date
	 */
	@Test
	public void testSuccessHasUserNotLoggedInSince()
			throws AccountStoreException {
		when(this.accountStoreDao.findByName("Alex")).thenReturn(accountMock);
		boolean hasloggedin = acctStoreServiceImpl.hasLoggedInSince("Alex",
				getDate(1));
		assertFalse("User has not logged in", hasloggedin);
	}

	/**
	 * To test the failure scenario to find if the user has logged in from the
	 * since date when the provided user does not exist
	 */
	@Test(expected = AccountStoreException.class)
	public void testHasLoggedInSincewithUserNotExist()
			throws AccountStoreException {
		when(this.accountStoreDao.findByName("Alex")).thenReturn(null);
		acctStoreServiceImpl.hasLoggedInSince("Alex", new Date());
	}

	/**
	 * Method to prepare date
	 */
	public static Date getDate(int days)
	{
		Date date = new Date();
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.add(Calendar.DATE, days);
	    date.setTime(cal.getTime().getTime() );
	    return date;
	}

}
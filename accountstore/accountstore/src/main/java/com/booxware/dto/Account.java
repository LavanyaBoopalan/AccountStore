package com.booxware.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * AccountStore class used for Data transfer 
 *
 */
public class Account implements Serializable {

	private static final long serialVersionUID = 3115698990725637364L;
	
	private long id;

	private String userName;

	private byte[] encryptedPassword;

	private String email;

	private Date lastLogin;

	public Account(String userName, byte[] encryptedPassword, String email, Date date) {
		this.userName = userName;
		this.encryptedPassword = encryptedPassword;
		this.email = email;
		this.lastLogin = date;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public byte[] getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(byte[] encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

}

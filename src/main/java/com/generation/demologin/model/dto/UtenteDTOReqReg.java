package com.generation.demologin.model.dto;

public class UtenteDTOReqReg
{
	private String username;
	private String passwordInChiaro;
	private String email;

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPasswordInChiaro()
	{
		return passwordInChiaro;
	}

	public void setPasswordInChiaro(String passwordInChiaro)
	{
		this.passwordInChiaro = passwordInChiaro;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}
}

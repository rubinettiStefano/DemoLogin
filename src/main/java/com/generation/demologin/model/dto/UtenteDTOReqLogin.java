package com.generation.demologin.model.dto;

/**
 * Quando facciamo login, sostanzialmente contiene username e password
 */
public class UtenteDTOReqLogin
{
	private String username;
	private String passwordInChiaro;

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
}

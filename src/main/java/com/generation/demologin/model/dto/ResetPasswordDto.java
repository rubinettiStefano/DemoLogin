package com.generation.demologin.model.dto;

public class ResetPasswordDto
{
	private String recoveryCode;
	private String newPassword;

	public String getRecoveryCode()
	{
		return recoveryCode;
	}

	public void setRecoveryCode(String recoveryCode)
	{
		this.recoveryCode = recoveryCode;
	}

	public String getNewPassword()
	{
		return newPassword;
	}

	public void setNewPassword(String newPassword)
	{
		this.newPassword = newPassword;
	}
}

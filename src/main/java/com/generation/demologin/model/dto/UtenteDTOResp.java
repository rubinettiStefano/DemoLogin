package com.generation.demologin.model.dto;

//Ciò che mando indietro quando ti logghi
public class UtenteDTOResp
{
	//IDENTIFICATORE per l'utente
	//viene salvato nel browser e allegato alle request
	//così io, SERVER, so chi mi sta parlando
	private String token;

	public String getToken()
	{
		return token;
	}

	public void setToken(String token)
	{
		this.token = token;
	}
}

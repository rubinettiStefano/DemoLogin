package com.generation.demologin.model.entities;

import jakarta.persistence.*;

@Entity
public class MailRecuperoPassword
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String recoveryCode;
	private boolean used;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private Utente utente;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getRecoveryCode()
	{
		return recoveryCode;
	}

	public void setRecoveryCode(String recoveryCode)
	{
		this.recoveryCode = recoveryCode;
	}

	public boolean isUsed()
	{
		return used;
	}

	public void setUsed(boolean used)
	{
		this.used = used;
	}

	public Utente getUtente()
	{
		return utente;
	}

	public void setUtente(Utente utente)
	{
		this.utente = utente;
	}
}

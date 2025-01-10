package com.generation.demologin.model;

import com.generation.demologin.model.dto.UtenteDTOReqReg;
import com.generation.demologin.model.dto.UtenteDTOResp;
import com.generation.demologin.model.entities.Utente;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

@Service
public class DTOConverter
{
	public UtenteDTOResp convertInDTOResp(Utente utente)
	{
		//ID_UTENTE-primaletterausername-PAPERINO
		UtenteDTOResp utenteDTOResp = new UtenteDTOResp();
		String token  = utente.getId()+"-"+utente.getUsername().charAt(0)+"-PAPERINO";
		utenteDTOResp.setToken(token);
		return utenteDTOResp;
	}

	public Utente convertInUtente(UtenteDTOReqReg dto)
	{
		Utente utente = new Utente();
		utente.setUsername(dto.getUsername());
		utente.setMail(dto.getEmail());
		String passwordHashata = DigestUtils.md5Hex(dto.getPasswordInChiaro()).toUpperCase();
		utente.setHashedPassword(passwordHashata);
		return utente;
	}
}

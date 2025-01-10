package com.generation.demologin.controller;

import com.generation.demologin.model.DTOConverter;
import com.generation.demologin.model.dto.UtenteDTOReqLogin;
import com.generation.demologin.model.dto.UtenteDTOReqReg;
import com.generation.demologin.model.dto.UtenteDTOResp;
import com.generation.demologin.model.entities.Utente;
import com.generation.demologin.repositories.UtenteRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UtenteController
{
	@Autowired
	DTOConverter converter;

	@Autowired
	UtenteRepository repo;

	@Autowired
	private JavaMailSender emailSender;


	@PostMapping("/register")
	public UtenteDTOResp register(@RequestBody UtenteDTOReqReg dto)
	{
		Utente u = converter.convertInUtente(dto);
		u = repo.save(u);


		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("jaitaprovemail@gmail.com");
		message.setTo(u.getMail());
		message.setSubject(u.getUsername()+" ,benvenuto nel nostro fantastico sito");
		message.setText("Che assolutamente non esiste");
		emailSender.send(message);

		return converter.convertInDTOResp(u);
	}

	@PostMapping("/login")
	public UtenteDTOResp login(@RequestBody UtenteDTOReqLogin dto)
	{
		String passwordHashata = DigestUtils.md5Hex(dto.getPasswordInChiaro()).toUpperCase();


		Optional<Utente> u = repo.findByUsernameAndHashedPassword(dto.getUsername(), passwordHashata);

		if(u.isPresent())
			return converter.convertInDTOResp(u.get());
		else
			throw new RuntimeException("Username or password incorrect");
	}

	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public String gestisciUtenteNonGiusto(RuntimeException e)
	{
		return e.getMessage();
	}
}

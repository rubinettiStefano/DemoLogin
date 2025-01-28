package com.generation.demologin.controller;

import com.generation.demologin.model.dto.ResetPasswordDto;
import com.generation.demologin.model.entities.MailRecuperoPassword;
import com.generation.demologin.model.entities.Utente;
import com.generation.demologin.repositories.MailRecuperoRepository;
import com.generation.demologin.repositories.UtenteRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Optional;

@RestController
@RequestMapping("/api/recover")
public class RecoveryMailController
{
	@Autowired
	private UtenteRepository uRepo;
	@Autowired
	private MailRecuperoRepository mRepo;
	@Autowired
	private JavaMailSender emailSender;

	@GetMapping("/{username}")
	public ResponseEntity<?> sendRecoverMail(@PathVariable String username) throws MessagingException
	{
		//Dal front mi arriva username e prendo utente con tale username
		//si può fare uguale per la sua mail volendo
		Optional<Utente> utente = uRepo.findByUsername(username);

		//se non lo trovo mando la response not found
		if(utente.isEmpty())
			return ResponseEntity.notFound().build();

		//altrimenti genero una entità MailRecuperoPassword legata all'utente
		//con un codice casuale
		MailRecuperoPassword mailRecuperoPassword = new MailRecuperoPassword();
		mailRecuperoPassword.setUtente(utente.get());

		//genero una stringa casuale usando una libreria che ho importato
//		<dependency>
		//    <groupId>org.apache.commons</groupId>
		//    <artifactId>commons-lang3</artifactId>
		//    <version>3.12.0</version>
//		</dependency>
		//Stringa randomica di 10 caratteri
		String randomica = RandomStringUtils.randomAlphanumeric(15);
		mailRecuperoPassword.setRecoveryCode(randomica);

		//salvo sul db
		mRepo.save(mailRecuperoPassword);

		//mando mail contente un link per resettare la password che contiene
		//la stringa randomica generata
		MimeMessage message = emailSender.createMimeMessage();

		// Usa MimeMessageHelper per configurare il messaggio
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

		helper.setFrom("jaitaprovemail@gmail.com");
		helper.setTo(utente.get().getMail());
		helper.setSubject("Recupero password nome sito");

		// Imposta il contenuto HTML
		helper.setText("bla bla bla bla clicca sul link: <a href='http://localhost:8080/pagina-recupero/"+randomica+"'>RECUPERO</a>", true); // true indica che il testo è in formato HTML

		// Invia l'email
		emailSender.send(message);

		return ResponseEntity.ok().build();
	}

	@PostMapping
	public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordDto dto)
	{
		//usiamo il codice che abbiamo salvato e inviato via mail
		//per recuperare l'entità dal db
		Optional<MailRecuperoPassword> m=mRepo.findByRecoveryCode(dto.getRecoveryCode());

		if(m.isEmpty())
			return ResponseEntity.notFound().build();

		Utente daResettare = m.get().getUtente();
		daResettare.setHashedPassword(DigestUtils.md5Hex(dto.getNewPassword()));

		uRepo.save(daResettare);
		m.get().setUsed(true);
		mRepo.save(m.get());

		return ResponseEntity.ok().build();

	}

	/*
		Classi da guardare qui dentro per il recupero
		- MailRecuperoPassword
		- ResetPasswordDto
		- MailRecuperoPasswordRepository

		guardare application.properties per settare email

		cosa fare nel front
		1) una pagina dove inserisci username/mail e vai a fare una requests GET a questo controller, mandando
		lo username/mail come path variable-> request GET a /api/recover/Stefano12 supponendo che Stefano12 sia il mio username
		2) una pagina mappata a pagina-recupero/:recoveryCode, prendete il codice, fate una form per inserire nuova password
		volendo con il controllo del conferma password, inviate una post a /api/recover con nel body un json
		{
		  recoveryCode:"quelloArrivato",
		  password:"quellaInserita"
		}
		3)finito

	 */
}

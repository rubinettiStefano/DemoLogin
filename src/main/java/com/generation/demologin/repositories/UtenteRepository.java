package com.generation.demologin.repositories;

import com.generation.demologin.model.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UtenteRepository extends JpaRepository<Utente, Long>
{
	Optional<Utente> findByUsernameAndHashedPassword(String username, String hashedPassword);
}

package com.generation.demologin.repositories;

import com.generation.demologin.model.entities.MailRecuperoPassword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MailRecuperoRepository extends JpaRepository<MailRecuperoPassword,Long>
{
	Optional<MailRecuperoPassword> findByRecoveryCode(String recoveryCode);
}

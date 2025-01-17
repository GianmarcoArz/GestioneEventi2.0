package com.example.GestionaleEvento.prenotazione;

import com.example.GestionaleEvento.auth.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {
    List<Prenotazione> findByUtente(AppUser utente);
}

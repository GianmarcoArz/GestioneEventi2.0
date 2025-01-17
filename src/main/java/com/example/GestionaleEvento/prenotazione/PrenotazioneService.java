package com.example.GestionaleEvento.prenotazione;


import com.example.GestionaleEvento.auth.AppUser;
import com.example.GestionaleEvento.auth.AppUserRepository;
import com.example.GestionaleEvento.evento.Evento;
import com.example.GestionaleEvento.evento.EventoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.security.access.AccessDeniedException;

import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PrenotazioneService {

    private final PrenotazioneRepository prenotazioneRepository;
    private final EventoRepository eventoRepository;
    private final AppUserRepository appUserRepository;


    public Prenotazione prenotaPosto(Long eventoId, String username, int numeroPosti) {
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new EntityNotFoundException("Evento non trovato"));

        if (evento.getPostiDisponibili() < numeroPosti) {
            throw new AccessDeniedException("Non ci sono abbastanza posti disponibili per questo evento");
        }

        AppUser utente = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Utente non trovato"));

        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setEvento(evento);
        prenotazione.setUtente(utente);

        evento.setPostiDisponibili(evento.getPostiDisponibili() - numeroPosti);
        eventoRepository.save(evento);

        return prenotazioneRepository.save(prenotazione);
    }

    public List<Evento> getMiePrenotazioni(String username) {
        AppUser utente = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Utente non trovato"));

        List<Prenotazione> prenotazioni = prenotazioneRepository.findByUtente(utente);
        return prenotazioni.stream().map(Prenotazione::getEvento).collect(Collectors.toList());
    }

    public void annullaPrenotazione(Long prenotazioneId, String username) {
        Prenotazione prenotazione = prenotazioneRepository.findById(prenotazioneId)
                .orElseThrow(() -> new EntityNotFoundException("Prenotazione non trovata"));

        if (!prenotazione.getUtente().getUsername().equals(username)) {
            throw new AccessDeniedException("Non sei autorizzato ad annullare questa prenotazione");
        }

        Evento evento = prenotazione.getEvento();
        evento.setPostiDisponibili(evento.getPostiDisponibili() + 1);
        eventoRepository.save(evento);

        prenotazioneRepository.delete(prenotazione);
    }
}
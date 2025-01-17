package com.example.GestionaleEvento.prenotazione;

import com.example.GestionaleEvento.auth.AppUser;
import com.example.GestionaleEvento.evento.Evento;
import com.example.GestionaleEvento.evento.EventoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PrenotazioneService {

    @Autowired
    private final PrenotazioneRepository prenotazioneRepository;

    @Autowired
    private final EventoRepository eventoRepository;

    public List<Prenotazione> getPrenotazioniByUser(AppUser user) {
        return prenotazioneRepository.findByUtente(user);
    }

    public void cancelPrenotazione(Long prenotazioneId, AppUser user) {
        Prenotazione prenotazione = prenotazioneRepository.findById(prenotazioneId)
                .orElseThrow(() -> new RuntimeException("Prenotazione non trovata con id: " + prenotazioneId));

        if (!prenotazione.getUtente().equals(user)) {
            throw new IllegalStateException("Non puoi annullare una prenotazione che non ti appartiene");
        }

        Evento evento = prenotazione.getEvento();
        evento.setPostiDisponibili(evento.getPostiDisponibili() + 1);
        eventoRepository.save(evento);
        prenotazioneRepository.delete(prenotazione);
    }
}
package com.example.GestionaleEvento.evento;

import com.example.GestionaleEvento.auth.AppUser;
import com.example.GestionaleEvento.prenotazione.Prenotazione;
import com.example.GestionaleEvento.prenotazione.PrenotazioneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventoService {

    @Autowired
    private final EventoRepository eventoRepository;

    @Autowired
    private final PrenotazioneRepository prenotazioneRepository;

    public Evento createEvento(Evento evento, AppUser organizzatore) {
        evento.setOrganizzatore(organizzatore);
        return eventoRepository.save(evento);
    }

    public Evento updateEvento(Long eventoId, Evento eventoDetails) {
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new RuntimeException("Evento non trovato con id: " + eventoId));

        evento.setTitolo(eventoDetails.getTitolo());
        evento.setDescrizione(eventoDetails.getDescrizione());
        evento.setDate(eventoDetails.getDate());
        evento.setLuogo(eventoDetails.getLuogo());
        evento.setPostiDisponibili(eventoDetails.getPostiDisponibili());

        return eventoRepository.save(evento);
    }

    public void deleteEvento(Long eventoId) {
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new RuntimeException("Evento non trovato con id: " + eventoId));
        eventoRepository.delete(evento);
    }

    public List<Evento> getAllEventi() {
        return eventoRepository.findAll();
    }

    public Evento getEventoById(Long eventoId) {
        return eventoRepository.findById(eventoId)
                .orElseThrow(() -> new RuntimeException("Evento non trovato con id: " + eventoId));
    }

    public Prenotazione listaPartecipanti(Long eventoId, AppUser user) {
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new RuntimeException("Evento non trovato con id: " + eventoId));

        if (evento.getPostiDisponibili() <= 0) {
            throw new IllegalStateException("Mi dispiace, non ci sono più posti disponibili per questo evento");
        }

        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setEvento(evento);
        prenotazione.setUtente(user);
        evento.setPostiDisponibili(evento.getPostiDisponibili() - 1);
        eventoRepository.save(evento);
        return prenotazioneRepository.save(prenotazione);
    }
}
package com.example.GestionaleEvento.prenotazione;


import com.example.GestionaleEvento.evento.Evento;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prenotazioni")
@RequiredArgsConstructor
public class PrenotazioneController {

    private final PrenotazioneService prenotazioneService;

    @PostMapping("/{eventoId}")
    public ResponseEntity<Prenotazione> prenotaPosto(@PathVariable Long eventoId, @RequestParam int numeroPosti) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Prenotazione prenotazione = prenotazioneService.prenotaPosto(eventoId, username, numeroPosti);
        return new ResponseEntity<>(prenotazione, HttpStatus.CREATED);
    }

    @GetMapping("/mie")
    public ResponseEntity<List<Evento>> getMiePrenotazioni() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Evento> eventi = prenotazioneService.getMiePrenotazioni(username);
        return new ResponseEntity<>(eventi, HttpStatus.OK);
    }

    @DeleteMapping("/{prenotazioneId}")
    public ResponseEntity<Void> annullaPrenotazione(@PathVariable Long prenotazioneId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        prenotazioneService.annullaPrenotazione(prenotazioneId, username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
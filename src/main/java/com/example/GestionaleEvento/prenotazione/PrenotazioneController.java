package com.example.GestionaleEvento.prenotazione;

import com.example.GestionaleEvento.auth.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prenotazioni")
@RequiredArgsConstructor
public class PrenotazioneController {

    @Autowired
    private final PrenotazioneService prenotazioneService;

    @GetMapping
    public ResponseEntity<List<Prenotazione>> getPrenotazioniByUser(@AuthenticationPrincipal AppUser user) {
        return ResponseEntity.ok(prenotazioneService.getPrenotazioniByUser(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelPrenotazione(@PathVariable Long id, @AuthenticationPrincipal AppUser user) {
        prenotazioneService.cancelPrenotazione(id, user);
        return ResponseEntity.noContent().build();
    }
}
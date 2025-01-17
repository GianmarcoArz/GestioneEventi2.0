package com.example.GestionaleEvento.evento;

import com.example.GestionaleEvento.auth.AppUser;
import com.example.GestionaleEvento.auth.AppUserService;
import com.example.GestionaleEvento.prenotazione.Prenotazione;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eventi")
@RequiredArgsConstructor
public class EventoController {

    @Autowired
    private final EventoService eventoService;

    @Autowired
    private final AppUserService appUserService;

    @PostMapping
    public ResponseEntity<Evento> createEvento(@RequestBody Evento evento, @AuthenticationPrincipal AppUser user) {
        return ResponseEntity.ok(eventoService.createEvento(evento, user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Evento> updateEvento(@PathVariable Long id, @RequestBody Evento eventoDetails) {
        return ResponseEntity.ok(eventoService.updateEvento(id, eventoDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvento(@PathVariable Long id) {
        eventoService.deleteEvento(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Evento>> getAllEventi() {
        return ResponseEntity.ok(eventoService.getAllEventi());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Evento> getEventoById(@PathVariable Long id) {
        return ResponseEntity.ok(eventoService.getEventoById(id));
    }

    @PostMapping("/{id}/prenotazioni")
    public ResponseEntity<Prenotazione> prenotaPosto(@PathVariable Long id, @AuthenticationPrincipal AppUser user) {
        return ResponseEntity.ok(eventoService.listaPartecipanti(id, user));
    }
}
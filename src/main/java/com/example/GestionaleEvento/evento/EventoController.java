package com.example.GestionaleEvento.evento;

import com.example.GestionaleEvento.auth.AppUser;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eventi")
@RequiredArgsConstructor
public class EventoController {

    @Autowired
    private final EventoService eventoService;


    @GetMapping
    public ResponseEntity<List<Evento>> getAllEventi() {
        List<Evento> eventi = eventoService.getAllEventi();
        return new ResponseEntity<>(eventi, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ORGANIZER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Evento> creaEvento(@RequestBody EventoDTO eventoDTO) {
        Evento evento = eventoService.creaEvento(eventoDTO);
        return new ResponseEntity<>(evento, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ORGANIZER')")
    public ResponseEntity<Evento> modificaEvento(@PathVariable Long id, @RequestBody EventoDTO eventoDTO) {
        Evento evento = eventoService.modificaEvento(id, eventoDTO);
        return new ResponseEntity<>(evento, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ORGANIZER')")
    public ResponseEntity<Void> cancellaEvento(@PathVariable Long id) {
        eventoService.cancellaEvento(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
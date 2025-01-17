package com.example.GestionaleEvento.evento;

import com.example.GestionaleEvento.auth.AppUser;
import com.example.GestionaleEvento.auth.AppUserRepository;
import com.example.GestionaleEvento.auth.AppUserService;
import com.example.GestionaleEvento.prenotazione.Prenotazione;
import com.example.GestionaleEvento.prenotazione.PrenotazioneRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventoService {


    private final   EventoRepository eventoRepository;

    private final AppUserRepository appUserRepository;

    public List<Evento> getAllEventi() {
        return eventoRepository.findAll();
    }


    private AppUser getLoggedInUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return appUserRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
    }


    public Evento creaEvento(EventoDTO eventoDTO) {
        Evento evento = new Evento();
        BeanUtils.copyProperties(eventoDTO, evento);

        AppUser organizzatore = appUserRepository.findById(eventoDTO.getOrganizzatoreId())
                .orElseThrow(() -> new EntityNotFoundException("Organizzatore non trovato"));

        evento.setOrganizzatore(organizzatore);
        evento.setDate(LocalDate.now());
        return eventoRepository.save(evento);
    }

    public Evento modificaEvento(Long id, EventoDTO eventoDTO) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento non trovato"));

        AppUser loggedInUser = getLoggedInUser();
        if (!evento.getOrganizzatore().getId().equals(loggedInUser.getId())) {
            throw new AccessDeniedException("Non sei autorizzato a modificare questo evento");
        }

        BeanUtils.copyProperties(eventoDTO, evento);
        evento.setOrganizzatore(loggedInUser);
        return eventoRepository.save(evento);
    }

    public Evento cancellaEvento(Long id) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento non trovato"));

        AppUser loggedInUser = getLoggedInUser();
        if (!evento.getOrganizzatore().getId().equals(loggedInUser.getId())) {
            throw new AccessDeniedException("Non sei autorizzato a cancellare questo evento");
        }

        eventoRepository.delete(evento);
        return evento;
    }


}
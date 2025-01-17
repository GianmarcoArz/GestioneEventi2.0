package com.example.GestionaleEvento.prenotazione;

import com.example.GestionaleEvento.auth.AppUser;
import com.example.GestionaleEvento.evento.Evento;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Prenotazione {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "evento_id", nullable = false)
    private Evento evento;

    @ManyToOne
    @JoinColumn(name = "utente_id", nullable = false)
    private AppUser utente;

}

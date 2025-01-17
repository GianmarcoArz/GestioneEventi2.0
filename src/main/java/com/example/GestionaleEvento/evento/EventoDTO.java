package com.example.GestionaleEvento.evento;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EventoDTO {

    private String titolo;

    private String descrizione;

    private LocalDate date;

    private String luogo;

    private int postiDisponibili;

    private Long organizzatoreId;
}

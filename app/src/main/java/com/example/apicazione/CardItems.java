package com.example.apicazione;

public class CardItems {


    private String titolo;
    private String descrizione;
    private String durata;


    public CardItems(String titolo,String descrizione,String durata) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.durata = durata;
    }


    public String getTitolo() {
        return titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public String getDurata() {
        return durata;
    }
}

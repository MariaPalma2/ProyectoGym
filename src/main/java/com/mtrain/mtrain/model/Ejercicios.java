package com.mtrain.mtrain.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ejercicios")
public class Ejercicios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "bloque_id")
    private Bloque bloque;

    private String ejercicio;

    private String imagen;

    private String series;

    private String repeticiones;

    private String descanso;

    @Column(name = "musculos_principales")
    private String musculosPrincipales;

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Bloque getBloque() {
        return bloque;
    }

    public void setBloque(Bloque bloque) {
        this.bloque = bloque;
    }

    public String getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(String ejercicio) {
        this.ejercicio = ejercicio;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(String repeticiones) {
        this.repeticiones = repeticiones;
    }

    public String getDescanso() {
        return descanso;
    }

    public void setDescanso(String descanso) {
        this.descanso = descanso;
    }

    public String getMusculosPrincipales() {
        return musculosPrincipales;
    }

    public void setMusculosPrincipales(String musculosPrincipales) {
        this.musculosPrincipales = musculosPrincipales;
    }
}

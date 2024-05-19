package com.tallerwebi.dominio.viaje;

import com.tallerwebi.dominio.cliente.Cliente;

import javax.persistence.*;

@Entity
public class Viaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String domicilioDeSalida;
    private String domicilioDeLlegada;
    private String latitudDeSalida;
    private String longitudDeSalida;
    private String latitudDeLlegada;
    private String longitudDeLlegada;
    private String codigoPostal;
    private String precio;
    private String medioDePago;

    @ManyToOne()
    @JoinColumn(name = "idCliente", referencedColumnName = "id")
    private Cliente cliente;

    public Viaje() {

    }

    public Viaje(String domicilioDeSalida, String domicilioDeLlegada, String latitudDeSalida, String longitudDeSalida, String latitudDeLlegada, String longitudDeLlegada, String codigoPostal, String precio, String medioDePago, Cliente cliente) {
        this.domicilioDeSalida = domicilioDeSalida;
        this.domicilioDeLlegada = domicilioDeLlegada;
        this.latitudDeSalida = latitudDeSalida;
        this.longitudDeSalida = longitudDeSalida;
        this.latitudDeLlegada = latitudDeLlegada;
        this.longitudDeLlegada = longitudDeLlegada;
        this.codigoPostal = codigoPostal;
        this.precio = precio;
        this.medioDePago = medioDePago;
        this.cliente = cliente;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDomicilioDeLlegada() {
        return domicilioDeLlegada;
    }

    public void setDomicilioDeLlegada(String domicilioDeLlegada) {
        this.domicilioDeLlegada = domicilioDeLlegada;
    }

    public String getDomicilioDeSalida() {
        return domicilioDeSalida;
    }

    public void setDomicilioDeSalida(String domicilioDeSalida) {
        this.domicilioDeSalida = domicilioDeSalida;
    }

    public String getLatitudDeSalida() {
        return latitudDeSalida;
    }

    public void setLatitudDeSalida(String latitudDeSalida) {
        this.latitudDeSalida = latitudDeSalida;
    }

    public String getLongitudDeSalida() {
        return longitudDeSalida;
    }

    public void setLongitudDeSalida(String longitudDeSalida) {
        this.longitudDeSalida = longitudDeSalida;
    }

    public String getLatitudDeLlegada() {
        return latitudDeLlegada;
    }

    public void setLatitudDeLlegada(String latitudDeLlegada) {
        this.latitudDeLlegada = latitudDeLlegada;
    }

    public String getLongitudDeLlegada() {
        return longitudDeLlegada;
    }

    public void setLongitudDeLlegada(String longitudDeLlegada) {
        this.longitudDeLlegada = longitudDeLlegada;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getMedioDePago() {
        return medioDePago;
    }

    public void setMedioDePago(String medioDePago) {
        this.medioDePago = medioDePago;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}

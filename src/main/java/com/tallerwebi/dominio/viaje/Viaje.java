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
    private Double latitudDeSalida;
    private Double longitudDeSalida;
    private Double latitudDeLlegada;
    private Double longitudDeLlegada;
    private String codigoPostal;
    private String precio;
    private String medioDePago;
    private Integer idConductor;

    @ManyToOne()
    @JoinColumn(name = "idCliente", referencedColumnName = "id")
    private Cliente cliente;

    public Viaje() {

    }

    public Viaje(Integer idViaje, String domicilioDeSalida, String domicilioDeLlegada) {
        this.id = idViaje;
        this.domicilioDeSalida = domicilioDeSalida;
        this.domicilioDeLlegada = domicilioDeLlegada;
    }

    public Viaje(String domicilioDeSalida, String domicilioDeLlegada, Double latitudDeSalida, Double longitudDeSalida, Double latitudDeLlegada, Double longitudDeLlegada, String codigoPostal, String precio, String medioDePago, Integer idConductor, Cliente cliente) {
        this.domicilioDeSalida = domicilioDeSalida;
        this.domicilioDeLlegada = domicilioDeLlegada;
        this.latitudDeSalida = latitudDeSalida;
        this.longitudDeSalida = longitudDeSalida;
        this.latitudDeLlegada = latitudDeLlegada;
        this.longitudDeLlegada = longitudDeLlegada;
        this.codigoPostal = codigoPostal;
        this.precio = precio;
        this.medioDePago = medioDePago;
        this.idConductor = idConductor;
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

    public Double getLatitudDeSalida() {
        return latitudDeSalida;
    }

    public void setLatitudDeSalida(Double latitudDeSalida) {
        this.latitudDeSalida = latitudDeSalida;
    }

    public Double getLongitudDeSalida() {
        return longitudDeSalida;
    }

    public void setLongitudDeSalida(Double longitudDeSalida) {
        this.longitudDeSalida = longitudDeSalida;
    }

    public Double getLatitudDeLlegada() {
        return latitudDeLlegada;
    }

    public void setLatitudDeLlegada(Double latitudDeLlegada) {
        this.latitudDeLlegada = latitudDeLlegada;
    }

    public Double getLongitudDeLlegada() {
        return longitudDeLlegada;
    }

    public void setLongitudDeLlegada(Double longitudDeLlegada) {
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

    public Integer getIdConductor() {
        return idConductor;
    }

    public void setIdConductor(Integer idConductor) {
        this.idConductor = idConductor;
    }
}

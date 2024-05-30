package com.tallerwebi.dominio.viaje;

import com.tallerwebi.dominio.cliente.Cliente;
import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.paquete.Paquete;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "viaje")
public class Viaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "domicilioDeSalida")
    private String domicilioDeSalida;

    @Column(name = "domicilioDeLlegada")
    private String domicilioDeLlegada;

    @Column(name = "latitudDeSalida")
    private Double latitudDeSalida;

    @Column(name = "longitudDeSalida")
    private Double longitudDeSalida;

    @Column(name = "latitudDeLlegada")
    private Double latitudDeLlegada;

    @Column(name = "longitudDeLlegada")
    private Double longitudDeLlegada;

    @Column(name = "codigoPostal")
    private String codigoPostal;

    @Column(name = "precio")
    private String precio;

    @Column(name = "medioDePago")
    private String medioDePago;

    @Column(name = "terminado")
    private Boolean terminado;

    @Column(name = "cancelado")
    private Boolean cancelado;

    @Column(name = "descartado")
    private Boolean descartado;

    @Column(name = "aceptado")
    private Boolean aceptado;

    @ManyToOne()
    @JoinColumn(name = "idCliente", referencedColumnName = "id")
    private Cliente cliente;

    @ManyToOne()
    @JoinColumn(name= "idConductor", referencedColumnName="id")
    private Conductor conductor;

    @OneToOne()
    @JoinColumn(name = "idPaquete", referencedColumnName = "id")
    private Paquete paquete;

    @Column(name = "fecha_terminacion")
    private LocalDateTime fechaDeTerminacion;

    @Column(name = "fecha_cancelacion")
    private LocalDateTime fechaDeCancelacion;

    @Transient
    private Double distanciaDelViaje;

    public Viaje() {

    }

    public Viaje(String domicilioDeSalida, String domicilioDeLlegada, Cliente cliente, String precio, String codigoPostal, Double latitudDeSalida, Double longitudDeSalida, Double latitudDeLlegada, Double longitudDeLlegada, Double distanciaDelViaje, Boolean terminado, Boolean cancelado, Boolean aceptado, Boolean descartado) {
        this.domicilioDeSalida = domicilioDeSalida;
        this.domicilioDeLlegada = domicilioDeLlegada;
        this.cliente = cliente;
        this.precio = precio;
        this.codigoPostal = codigoPostal;
        this.latitudDeSalida = latitudDeSalida;
        this.longitudDeSalida = longitudDeSalida;
        this.latitudDeLlegada = latitudDeLlegada;
        this.longitudDeLlegada = longitudDeLlegada;
        this.distanciaDelViaje = distanciaDelViaje;
        this.terminado = terminado;
        this.cancelado = cancelado;
        this.aceptado = aceptado;
        this.descartado = descartado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDomicilioDeSalida() {
        return domicilioDeSalida;
    }

    public void setDomicilioDeSalida(String domicilioDeSalida) {
        this.domicilioDeSalida = domicilioDeSalida;
    }

    public String getDomicilioDeLlegada() {
        return domicilioDeLlegada;
    }

    public void setDomicilioDeLlegada(String domicilioDeLlegada) {
        this.domicilioDeLlegada = domicilioDeLlegada;
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

    public Boolean getTerminado() {
        return terminado;
    }

    public void setTerminado(Boolean terminado) {
        this.terminado = terminado;
    }

    public Boolean getCancelado() {
        return cancelado;
    }

    public void setCancelado(Boolean cancelado) {
        this.cancelado = cancelado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Conductor getConductor() {
        return conductor;
    }

    public void setConductor(Conductor conductor) {
        this.conductor = conductor;
    }

    public Paquete getPaquete() {
        return paquete;
    }

    public void setPaquete(Paquete paquete) {
        this.paquete = paquete;
    }

    public Boolean getDescartado() {
        return descartado;
    }

    public void setDescartado(Boolean descartado) {
        this.descartado = descartado;
    }

    public Double getDistanciaDelViaje() {
        return distanciaDelViaje;
    }

    public void setDistanciaDelViaje(Double distanciaDelViaje) {
        this.distanciaDelViaje = distanciaDelViaje;
    }

    public LocalDateTime getFechaDeTerminacion() {
        return fechaDeTerminacion;
    }

    public void setFechaDeTerminacion(LocalDateTime fechaDeTerminacion) {
        this.fechaDeTerminacion = fechaDeTerminacion;
    }

    public LocalDateTime getFechaDeCancelacion() {
        return fechaDeCancelacion;
    }

    public void setFechaDeCancelacion(LocalDateTime fechaDeCancelacion) {
        this.fechaDeCancelacion = fechaDeCancelacion;
    }

    public Boolean getAceptado() {
        return aceptado;
    }

    public void setAceptado(Boolean aceptado) {
        this.aceptado = aceptado;
    }
}

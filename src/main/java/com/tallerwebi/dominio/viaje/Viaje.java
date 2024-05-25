package com.tallerwebi.dominio.viaje;

import com.tallerwebi.dominio.cliente.Cliente;
import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.paquete.Paquete;

import javax.persistence.*;

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

    @ManyToOne()
    @JoinColumn(name = "idCliente", referencedColumnName = "id")
    private Cliente cliente;

    @ManyToOne()
    @JoinColumn(name= "idConductor", referencedColumnName="id")
    private Conductor conductor;

    @OneToOne()
    @JoinColumn(name = "idPaquete", referencedColumnName = "id")
    private Paquete paquete;

    public Viaje() {

    }

    public Viaje(String domicilioDeSalida, String domicilioDeLlegada, Double latitudDeSalida, Double longitudDeSalida, Double latitudDeLlegada, Double longitudDeLlegada, String codigoPostal, String precio, String medioDePago, Boolean terminado, Boolean cancelado) {
        this.domicilioDeSalida = domicilioDeSalida;
        this.domicilioDeLlegada = domicilioDeLlegada;
        this.latitudDeSalida = latitudDeSalida;
        this.longitudDeSalida = longitudDeSalida;
        this.latitudDeLlegada = latitudDeLlegada;
        this.longitudDeLlegada = longitudDeLlegada;
        this.codigoPostal = codigoPostal;
        this.precio = precio;
        this.medioDePago = medioDePago;
        this.terminado = terminado;
        this.cancelado = cancelado;
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
}

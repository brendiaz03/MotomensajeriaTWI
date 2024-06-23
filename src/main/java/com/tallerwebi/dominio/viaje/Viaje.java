package com.tallerwebi.dominio.viaje;

import com.tallerwebi.dominio.cliente.Cliente;
import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.enums.TipoEstado;
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

    @Column(name = "precio")
    private Double precio;

    @Column(name = "medioDePago")
    private String medioDePago;

    @Column(name = "estado")
    private TipoEstado estado;

    @ManyToOne()
    @JoinColumn(name = "idCliente", referencedColumnName = "id")
    private Cliente cliente;

    @ManyToOne()
    @JoinColumn(name= "idConductor", referencedColumnName="id")
    private Conductor conductor;

    @OneToOne()
    @JoinColumn(name = "idPaquete", referencedColumnName = "id")
    private Paquete paquete;

    @Column(name = "fecha")
    private LocalDateTime fecha;

    @Column(name = "canceladoPor")
    private Integer canceladoPor;

    @Column(name = "enviadoNuevamente")
    private Boolean enviadoNuevamente;

    @Column(name = "afectaPenalizacion")
    private Boolean afectaPenalizacion;


    @Transient
    private Double distanciaDelViaje;

    public Viaje() {

    }

    public Viaje(String domicilioDeSalida, String domicilioDeLlegada, Cliente cliente, Double precio, Double latitudDeSalida, Double longitudDeSalida, Double latitudDeLlegada, Double longitudDeLlegada, Double distanciaDelViaje, TipoEstado estado) {
        this.domicilioDeSalida = domicilioDeSalida;
        this.domicilioDeLlegada = domicilioDeLlegada;
        this.cliente = cliente;
        this.precio = precio;
        this.latitudDeSalida = latitudDeSalida;
        this.longitudDeSalida = longitudDeSalida;
        this.latitudDeLlegada = latitudDeLlegada;
        this.longitudDeLlegada = longitudDeLlegada;
        this.distanciaDelViaje = distanciaDelViaje;
        this.estado = estado;
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

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
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

    public Double getDistanciaDelViaje() {
        return distanciaDelViaje;
    }

    public void setDistanciaDelViaje(Double distanciaDelViaje) {
        this.distanciaDelViaje = distanciaDelViaje;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public TipoEstado getEstado() {
        return estado;
    }

    public void setEstado(TipoEstado estado) {
        this.estado = estado;
    }

    public String getNombreEstado() {
        return TipoEstado.values()[estado.ordinal()].name();
    }

    public Integer getCanceladoPor() {
        return canceladoPor;
    }

    public void setCanceladoPor(Integer canceladoPor) {
        this.canceladoPor = canceladoPor;
    }

    public Boolean getEnviadoNuevamente() {
        return enviadoNuevamente;
    }

    public void setEnviadoNuevamente(Boolean enviadoNuevamente) {
        this.enviadoNuevamente = enviadoNuevamente;
    }

    public Boolean getAfectaPenalizacion() {
        return afectaPenalizacion;
    }
    public void setAfectaPenalizacion(Boolean afectaPenalizacion) {
        this.afectaPenalizacion = afectaPenalizacion;
    }
}



package co.id.telkom.epuskesmas.model;

import co.id.telkom.epuskesmas.utils.ServerUtils;

import javax.persistence.*;

@Entity
@Table(name = "puskesmas")
public class PuskesmasModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_puskesmas")
    private int id;

    @Column(name = "nama")
    private String nama;

    @Column(name = "alamat")
    private String alamat;

    @Column(name = "no_telp")
    private String telepon;

    @Column(name = "lokasi_longitude")
    private Double lon;

    @Column(name = "lokasi_latitude")
    private Double lat;

    @Column(name = "photo")
    private String foto;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getFotoUrl() {
        if (foto != null) {
            ServerUtils serverUtils = new ServerUtils();
            return serverUtils.getBaseURL("") + "/static/clinics/" + foto;
        }

        return foto;
    }
}

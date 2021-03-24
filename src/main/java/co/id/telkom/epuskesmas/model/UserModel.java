package co.id.telkom.epuskesmas.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user")
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private int id;

    @Column(name = "nama")
    private String nama;

    @Column(name = "provinsi")
    private String provinsi;

    @Column(name = "kabupaten")
    private String kabupaten;

    @Column(name = "no_hp")
    private String phone;

    @Column(name = "no_bpjs")
    private String bpjs;

    @Column(name = "password")
    private String password;

    @Column(name = "jenis_kelamin")
    private String kelamin;

    @Column(name = "tanggal_lahir")
    private Date tanggalLahir;

    @Column(name = "lokasi_longitude")
    private Double lon;

    @Column(name = "lokasi_latitude")
    private Double lat;

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

    public String getProvinsi() {
        return provinsi;
    }

    public void setProvinsi(String provinsi) {
        this.provinsi = provinsi;
    }

    public String getKabupaten() {
        return kabupaten;
    }

    public void setKabupaten(String kabupaten) {
        this.kabupaten = kabupaten;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBpjs() {
        return bpjs;
    }

    public void setBpjs(String bpjs) {
        this.bpjs = bpjs;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getKelamin() {
        return kelamin;
    }

    public void setKelamin(String kelamin) {
        this.kelamin = kelamin;
    }

    public Date getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(Date tanggal_lahir) {
        this.tanggalLahir = tanggal_lahir;
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
}

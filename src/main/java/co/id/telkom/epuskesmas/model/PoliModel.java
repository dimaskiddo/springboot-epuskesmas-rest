package co.id.telkom.epuskesmas.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "poli")
public class PoliModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_poli")
    private int id;

    @Column(name = "id_puskesmas")
    private int idPuskesmas;

    @Column(name = "nama")
    private String nama;

    @Column(name = "waktu_buka")
    private Date waktuBuka;

    @Column(name = "waktu_tutup")
    private Date waktuTutup;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPuskesmas() {
        return idPuskesmas;
    }

    public void setIdPuskesmas(int idPuskesmas) {
        this.idPuskesmas = idPuskesmas;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Date getWaktuBuka() {
        return waktuBuka;
    }

    public void setWaktuBuka(Date waktuBuka) {
        this.waktuBuka = waktuBuka;
    }

    public Date getWaktuTutup() {
        return waktuTutup;
    }

    public void setWaktuTutup(Date waktuTutup) {
        this.waktuTutup = waktuTutup;
    }
}

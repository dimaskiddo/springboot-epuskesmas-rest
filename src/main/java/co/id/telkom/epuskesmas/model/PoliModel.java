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
    private int id_puskesmas;

    @Column(name = "nama")
    private String nama;

    @Column(name = "waktu_buka")
    private Date waktu_buka;

    @Column(name = "waktu_tutup")
    private Date waktu_tutup;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPuskesmas() {
        return id_puskesmas;
    }

    public void setIdPuskesmas(int id_puskesmas) {
        this.id_puskesmas = id_puskesmas;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Date getWaktuBuka() {
        return waktu_buka;
    }

    public void setWaktuBuka(Date waktu_buka) {
        this.waktu_buka = waktu_buka;
    }

    public Date getWaktuTutup() {
        return waktu_tutup;
    }

    public void setWaktuTutup(Date waktu_tutup) {
        this.waktu_tutup = waktu_tutup;
    }
}

package co.id.telkom.epuskesmas.model;

import javax.persistence.*;

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
    private String waktuBuka;

    @Column(name = "waktu_tutup")
    private String waktuTutup;

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

    public String getWaktuBuka() {
        return waktuBuka;
    }

    public void setWaktuBuka(String waktuBuka) {
        this.waktuBuka = waktuBuka;
    }

    public String getWaktuTutup() {
        return waktuTutup;
    }

    public void setWaktuTutup(String waktuTutup) {
        this.waktuTutup = waktuTutup;
    }
}

package co.id.telkom.epuskesmas.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "antrian")
public class QueueModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_antrian")
    private int id;

    @Column(name = "id_user")
    private int id_user;

    @Column(name = "id_poli")
    private int id_poli;

    @Column(name = "id_dokter")
    private int id_dokter;

    @Column(name = "no_antrian")
    private int no_antrian;

    @Column(name = "waktu_ambil_antrian")
    private Timestamp waktu_antrian;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return id_user;
    }

    public void setIdUser(int id_user) {
        this.id_user = id_user;
    }

    public int getIdPoli() {
        return id_poli;
    }

    public void setIdPoli(int id_poli) {
        this.id_poli = id_poli;
    }

    public int getIdDokter() {
        return id_dokter;
    }

    public void setIdDokter(int id_dokter) {
        this.id_dokter = id_dokter;
    }

    public int getNoAntrian() {
        return no_antrian;
    }

    public void setNoAntrian(int no_antrian) {
        this.no_antrian = no_antrian;
    }

    public Timestamp getWaktuAntrian() {
        return waktu_antrian;
    }

    public void setWaktuAntrian(Timestamp waktu_antrian) {
        this.waktu_antrian = waktu_antrian;
    }
}

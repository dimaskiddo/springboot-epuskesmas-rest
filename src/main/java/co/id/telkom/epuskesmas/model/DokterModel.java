package co.id.telkom.epuskesmas.model;

import javax.persistence.*;

@Entity
@Table(name = "dokter")
public class DokterModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dokter")
    private int id;

    @Column(name = "id_poli")
    private int id_poli;

    @Column(name = "nama")
    private String nama;

    @Column(name = "jenis_kelamin")
    private String kelamin;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_poli() {
        return id_poli;
    }

    public void setId_poli(int id_poli) {
        this.id_poli = id_poli;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKelamin() {
        return kelamin;
    }

    public void setKelamin(String kelamin) {
        this.kelamin = kelamin;
    }
}

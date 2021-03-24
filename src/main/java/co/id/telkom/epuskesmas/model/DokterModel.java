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
    private int idPoli;

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

    public int getIdPoli() {
        return idPoli;
    }

    public void setIdPoli(int id_poli) {
        this.idPoli = id_poli;
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

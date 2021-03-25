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

    @Column(name = "photo")
    private String foto;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPoli() {
        return idPoli;
    }

    public void setIdPoli(int idPoli) {
        this.idPoli = idPoli;
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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}

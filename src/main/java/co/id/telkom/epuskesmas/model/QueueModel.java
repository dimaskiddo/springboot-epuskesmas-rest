package co.id.telkom.epuskesmas.model;

import co.id.telkom.epuskesmas.utils.QRCodeUtils;
import jakarta.persistence.*;

@Entity
@Table(name = "antrian")
public class QueueModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_antrian")
    private int id;

    @Column(name = "id_user")
    private int idUser;

    @Column(name = "id_poli")
    private int idPoli;

    @Column(name = "id_dokter")
    private int idDokter;

    @Column(name = "no_antrian")
    private int noAntrian;

    @Column(name = "waktu_ambil_antrian")
    private String waktuAntrian;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdPoli() {
        return idPoli;
    }

    public void setIdPoli(int idPoli) {
        this.idPoli = idPoli;
    }

    public int getIdDokter() {
        return idDokter;
    }

    public void setIdDokter(int idDokter) {
        this.idDokter = idDokter;
    }

    public int getNoAntrian() {
        return noAntrian;
    }

    public void setNoAntrian(int noAntrian) {
        this.noAntrian = noAntrian;
    }

    public String getWaktuAntrian() {
        return waktuAntrian;
    }

    public void setWaktuAntrian(String waktuAntrian) {
        this.waktuAntrian = waktuAntrian;
    }

    public String getQRCode() {
        QRCodeUtils qrCodeUtils = new QRCodeUtils();

        String qrCodeData = "{\"id\":" + this.id + ",\"idUser\":" + this.idUser +
                            ",\"idPoli\":" + this.idPoli + ",\"idDokter\":" + this.idDokter + "}";
        return qrCodeUtils.createQRCodeBase64(qrCodeData, 300);
    }
}

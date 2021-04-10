package co.id.telkom.epuskesmas.utils;

import net.glxn.qrgen.javase.QRCode;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

public class QRCodeUtils {

    public ByteArrayOutputStream createQRCode(String qrContent, Integer qrSize) {
        if (qrSize == null || qrSize < 300) {
            qrSize = 300;
        }

        return QRCode
               .from(qrContent)
               .withSize(qrSize, qrSize)
               .stream();
    }

    public String createQRCodeBase64(String qrContent, Integer qrSize) {
        return "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(createQRCode(qrContent, qrSize).toByteArray());
    }
}

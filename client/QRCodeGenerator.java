package client;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;

import compute.Task;

public class QRCodeGenerator implements Task<String>, Serializable {

    private static final long serialVersionUID = 1L;
    private String texto;
    private int largura;
    private int altura;

    public QRCodeGenerator(String texto, int largura, int altura) {
        this.texto = texto;
        this.largura = largura;
        this.altura = altura;
    }

    @Override
    public String execute() {
        return gerarQRCode(texto, largura, altura);
    }

    private String gerarQRCode(String texto, int largura, int altura) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix;
        try {
            bitMatrix = qrCodeWriter.encode(texto, BarcodeFormat.QR_CODE, largura, altura);
            String nomeArquivo = "QRCode.png";
            File outputFile = new File(nomeArquivo);
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", outputFile.toPath());
            return outputFile.getAbsolutePath();
        } catch (WriterException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

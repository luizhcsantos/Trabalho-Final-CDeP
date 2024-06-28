package client;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import compute.Task;

public class QRCodeGenerator implements Task<String>, Serializable {

    private static final long serialVersionUID = 1L;
    private String texto;
    private int largura;
    private int altura;
    private String caminho;  

   
    

    public QRCodeGenerator(String texto, int largura, int altura, String caminho) {
        this.texto = texto;
        this.largura = largura;
        this.altura = altura;
        this.caminho = caminho; 
    }

    @Override
    public String execute() {
        return gerarQRCode(texto, largura, altura, caminho);
    }

    private String gerarQRCode(String texto, int largura, int altura, String caminho) {
        
        try {
             Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(texto, BarcodeFormat.QR_CODE, largura, altura, hints);
            Path path = Paths.get(caminho);
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

            return path.toAbsolutePath().toString();
        } catch (WriterException | IOException e) {
            return null;
        }
    }
}

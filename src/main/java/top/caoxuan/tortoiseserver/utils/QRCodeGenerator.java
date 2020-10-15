package top.caoxuan.tortoiseserver.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.apache.juli.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class QRCodeGenerator {

	private static final Logger logger = LoggerFactory.getLogger(QRCodeGenerator.class);

	private static final String UUID = java.util.UUID.randomUUID().toString();
	
	private static final String QR_CODE_IMAGE_PATH =  UUID + "_" + System.currentTimeMillis() + ".png";

	public void generateQRCodeImage(String text, int width, int height, String filePath) throws WriterException, IOException {
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		
		BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
		
		Path path = FileSystems.getDefault().getPath(filePath);
		
		MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

		logger.info("已生成二维码 " + path.toString());
	}
	
	/*public static void main(String[] args) {
        try {
            new QRCodeGenerator().generateQRCodeImage("https://www.baidu.com", 350, 350, QR_CODE_IMAGE_PATH);
        } catch (WriterException e) {
            System.out.println("Could not generate QR Code, WriterException :: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Could not generate QR Code, IOException :: " + e.getMessage());
        }
		
	}*/
	
 
}
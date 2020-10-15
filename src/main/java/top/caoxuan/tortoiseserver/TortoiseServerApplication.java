package top.caoxuan.tortoiseserver;

import com.google.zxing.WriterException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import top.caoxuan.tortoiseserver.utils.QRCodeGenerator;

import java.io.IOException;
import java.util.UUID;

/**
 * @author CX
 */
@SpringBootApplication
public class TortoiseServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TortoiseServerApplication.class, args);
    }

}

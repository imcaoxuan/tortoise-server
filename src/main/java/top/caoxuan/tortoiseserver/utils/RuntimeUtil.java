package top.caoxuan.tortoiseserver.utils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;


/**
 * @author CX
 * @date 2020/10/17 22:58
 */
public class RuntimeUtil {

    public static String getIpv4Address() {
        InetAddress inetAddress;
        try {
            inetAddress = Inet4Address.getLocalHost();
            System.out.println("inetAddress:" + inetAddress);
            return inetAddress.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }
    }


}

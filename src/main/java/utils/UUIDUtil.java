package utils;

import java.util.UUID;


/**
 * 用於生成session
 *
 * @author lai
 */
public class UUIDUtil {
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}

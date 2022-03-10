package vn.cmc.du21.paymentservice.common;

import org.apache.commons.codec.digest.HmacUtils;

public class Sha512 {
public static String hmac(String key, String data) {
    String hmac = new HmacUtils("HmacSHA512", key).hmacHex(data);
    return hmac;
}
}

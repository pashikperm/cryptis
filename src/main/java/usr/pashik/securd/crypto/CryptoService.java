package usr.pashik.securd.crypto;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by pashik on 17.03.14 21:44.
 */
public class CryptoService {

    public static String hmacSha1(String value, byte[] secretKey) {
        try {
            SecretKeySpec signingKey = new SecretKeySpec(secretKey, "HmacSHA1");

            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);

            byte[] rawHmac = mac.doFinal(value.getBytes());

            return BinaryService.bytes2base64(rawHmac);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

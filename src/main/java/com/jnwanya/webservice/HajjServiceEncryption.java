package com.jnwanya.webservice;

import org.apache.axis.encoding.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * Created by jnwanya on
 * Sat, 03 Oct, 2020
 */
// https://stackoverflow.com/questions/1400830/net-tripledescryptoserviceprovider-equivalent-in-java
@Component
public class HajjServiceEncryption {

    private final byte[] sharedKey;
    private final byte[] sharedVector;

    public HajjServiceEncryption(@Value("${shared-key-value}") String sharedKeyValue,
                                 @Value("${shared-vector-value}") String sharedVectorValue) {
         sharedKey = binaryToByteArray(sharedKeyValue);
        sharedVector = binaryToByteArray(sharedVectorValue);
    }

    public String encrypt(String plaintext) throws Exception {
        Cipher c = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        c.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(sharedKey, "DESede"), new IvParameterSpec(sharedVector));
        byte[] encrypted = c.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
        return Base64.encode(encrypted);
    }

    public String decrypt(String ciphertext) throws Exception {
        Cipher c = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        c.init(Cipher.DECRYPT_MODE, new SecretKeySpec(sharedKey, "DESede"), new IvParameterSpec(sharedVector));
        byte[] decrypted = c.doFinal(Base64.decode(ciphertext));
        return new String(decrypted, StandardCharsets.UTF_8);
    }


    private byte[] binaryToByteArray(String binaryValue) {
        return new BigInteger(binaryValue, 2).toByteArray();
    }
}

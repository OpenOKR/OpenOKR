package org.openokr.utils;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.util.Assert;

import javax.crypto.Cipher;
import java.security.*;

public final class RSAUtils {
    private static final Provider bcProvider = new BouncyCastleProvider();
    private static final int KEY_SIZE = 1024;

    public static KeyPair generateKeyPair() {
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA",
                    bcProvider);
            keyPairGen.initialize(KEY_SIZE, new SecureRandom());
            return keyPairGen.generateKeyPair();
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            noSuchAlgorithmException.printStackTrace();
        }
        return null;
    }

    public static byte[] encrypt(PublicKey publicKey, byte[] data) {
        Assert.notNull(publicKey, "publicKey");
        Assert.notNull(data, "data");
        try {
            Cipher cipher = Cipher.getInstance("RSA", bcProvider);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String encrypt(PublicKey publicKey, String text) {
        Assert.notNull(publicKey, "publicKey");
        Assert.notNull(text, "text");
        byte[] epByte = encrypt(publicKey, text.getBytes());
        return epByte != null ? Base64.encodeBase64String(epByte) : null;
    }

    public static byte[] decrypt(PrivateKey privateKey, byte[] data) {
        Assert.notNull(privateKey, "privateKey");
        Assert.notNull(data, "data");
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding",
                    bcProvider);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return cipher.doFinal(data);
        } catch (Exception e) {
        }
        return null;
    }

    public static String decrypt(PrivateKey privateKey, String text) {
        Assert.notNull(privateKey, "privateKey");
        Assert.notNull(text, "text");
        byte[] deByte = decrypt(privateKey, Base64.decodeBase64(text));
        return deByte != null ? new String(deByte) : null;
    }
}

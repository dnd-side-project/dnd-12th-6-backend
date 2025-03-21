package com.dnd12.meetinginvitation.util;

import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

@Service
public class AESUtil {

    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final String CHARSET = "UTF-8";
    private static final String SECRET_KEY = "12345678901234561234567890123456  ";
    private static final String IV = "1234567890123456";

    // 키가 32바이트보다 길면 자르고, 짧으면 32바이트로 패딩을 추가하는 방법
    private static SecretKeySpec getSecretKey() throws Exception {
        //return new SecretKeySpec(SECRET_KEY.getBytes(CHARSET), "AES");
        byte[] key = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        if (key.length > 32) {
            key = Arrays.copyOf(key, 32); // 32바이트로 잘라서 사용
        } else if (key.length < 32) {
            key = Arrays.copyOf(key, 32); // 32바이트로 패딩을 추가
        }
        return new SecretKeySpec(key, "AES");
    }

    private static IvParameterSpec getIv() throws Exception {
        return new IvParameterSpec(IV.getBytes(CHARSET));
    }

    // 🔹 AES 암호화
    public static String encrypt(String data) {
        try {
            System.out.println("SECRET_KEY length: " + SECRET_KEY.getBytes(StandardCharsets.UTF_8).length);
            System.out.println("IV length: " + IV.getBytes(StandardCharsets.UTF_8).length);

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(), getIv());
            byte[] encrypted = cipher.doFinal(data.getBytes(CHARSET));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new RuntimeException("암호화 오류", e);
        }
    }

    // 🔹 AES 복호화
    public static String decrypt(String encryptedData) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(), getIv());
            byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);
            byte[] decrypted = cipher.doFinal(decodedBytes);
            return new String(decrypted, CHARSET);
        } catch (Exception e) {
            throw new RuntimeException("복호화 오류", e);
        }
    }

}

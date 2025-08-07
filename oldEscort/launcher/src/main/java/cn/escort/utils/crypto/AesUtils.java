package cn.escort.utils.crypto;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class AesUtils {


    public static byte[] aesCBCEncrypt(byte[] rawData, byte[] key, byte[] iv){
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            Cipher cipher = null;
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            return cipher.doFinal(rawData);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException|InvalidAlgorithmParameterException|IllegalBlockSizeException|BadPaddingException|InvalidKeyException e) {
            throw new CryptoException(e.getMessage());
        }
    }

    public static byte[] cbcDecrypt(byte[] encryptData, byte[] key, byte[] iv){
        try {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
        return cipher.doFinal(encryptData);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException|InvalidAlgorithmParameterException|IllegalBlockSizeException|BadPaddingException|InvalidKeyException e) {
            throw new CryptoException(e.getMessage());
        }
    }

    public static String diyEncrypt(byte[] rawData, byte[] key) {
        byte[] iv = new byte[16];
        // 使用安全的随机数生成IV
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        byte[] data = aesCBCEncrypt(rawData, key, iv);
        byte[] combinedData = new byte[16 + data.length];
        System.arraycopy(iv, 0, combinedData, 0, 16);
        System.arraycopy(data, 0, combinedData, 16, data.length);
        return Base64.getEncoder().encodeToString(combinedData);
    }

    public static String diyEncrypt(String rawData, byte[] key){
        byte[] iv = new byte[16];
        // 使用安全的随机数生成IV
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        byte[] data = aesCBCEncrypt(rawData.getBytes(), key, iv);
        byte[] combinedData = new byte[16 + data.length];
        System.arraycopy(iv, 0, combinedData, 0, 16);
        System.arraycopy(data, 0, combinedData, 16, data.length);
        return Base64.getEncoder().encodeToString(combinedData);
    }

    public static byte[] diyDecrypt(byte[] rawData, byte[] key){
        byte[] data = Base64.getDecoder().decode(rawData);
        byte[] iv = new byte[16];
        System.arraycopy(data, 0, iv, 0, 16);
        byte[] encryptedData = new byte[data.length - 16];
        System.arraycopy(data, 16, encryptedData, 0, encryptedData.length);
        return cbcDecrypt(encryptedData, key, iv);
    }

    public static String diyDecrypt(String rawData, byte[] key){
        byte[] data = Base64.getDecoder().decode(rawData);
        byte[] iv = new byte[16];
        System.arraycopy(data, 0, iv, 0, 16);
        byte[] encryptedData = new byte[data.length - 16];
        System.arraycopy(data, 16, encryptedData, 0, encryptedData.length);
        byte[] decryptedData = cbcDecrypt(encryptedData, key, iv);
        return new String(decryptedData);
    }

    public static void main(String[] args) {
//        final String s = diyDecrypt("Ws+pIsVF/9AUIyL4xb/QFjiMceGkRHjKfSFdYAMJ75Ja4GzKYSvSwQJdbt68BpJg", "oyb8ZvjMd+VvP/mQ".getBytes());
//        System.out.println(s);
        final String s = AesUtils.diyDecrypt("WfKDI9LBcDRbvsPhSAfqWNQ5tiWxhSg+UyARM38cNJmF+v0PwUe4DTqV9KgPZ5LxQsK7VTjVF+zP0EzGsUN4VQ==", "oyb8ZvjMd+VvP/mQ".getBytes());
        System.out.println(s);
    }

}
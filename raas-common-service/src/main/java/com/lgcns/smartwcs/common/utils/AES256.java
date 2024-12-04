package com.lgcns.smartwcs.common.utils;

import org.springframework.stereotype.Service;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;
import java.util.Base64;

@Service
public class AES256 {
    private final String alg = "AES/CBC/PKCS5PADDING"; // Cipher Mode
    private final String key = "r12345a67890a12345s67890p12345l6";  // Secret Key 32byte

    private SecureRandom secureRandom = new SecureRandom();

    public String encrypt(String plainText) throws NoSuchPaddingException, NoSuchAlgorithmException,
            IOException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException,
            InvalidKeyException {
        Cipher cipher = Cipher.getInstance(alg);
        SecretKey keySpec = new SecretKeySpec(key.getBytes(CommonConstants.UTF8), "AES");

        byte[] iv = new byte[cipher.getBlockSize()];
        secureRandom.nextBytes(iv);
        AlgorithmParameterSpec ivParamSpec = new IvParameterSpec(iv);

        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);

        int blockSize = 128; //block size
        byte[] dataBytes = plainText.getBytes(CommonConstants.UTF8);

        //find fillChar & pad
        int plaintextLength = dataBytes.length;
        int fillChar = (blockSize - (plaintextLength % blockSize));
        plaintextLength += fillChar; //pad

        byte[] plaintext = new byte[plaintextLength];
        Arrays.fill(plaintext, (byte) fillChar);
        System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);

        //encrypt
        byte[] cipherBytes = cipher.doFinal(plaintext);

        //add iv to front of cipherBytes
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(iv);
        outputStream.write(cipherBytes);

        //encode into base64
        byte[] encryptedIvText = outputStream.toByteArray();
        return new String(Base64.getEncoder().encode(encryptedIvText), CommonConstants.UTF8);
    }

    public String decrypt(String encryptedText) throws NoSuchPaddingException, NoSuchAlgorithmException,
            UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException,
            InvalidAlgorithmParameterException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance(alg);

        //decode with base64 decoder
        byte[] encryptedIvTextBytes = Base64.getDecoder().decode(encryptedText);

        // Extract IV. (secureRandom is not needed. It only needs to pass sonaQube test)
        int ivSize = cipher.getBlockSize();
        byte[] iv = new byte[ivSize];
        secureRandom.nextBytes(iv);
        System.arraycopy(encryptedIvTextBytes, 0, iv, 0, iv.length);

        // Extract encrypted part.
        int encryptedSize = encryptedIvTextBytes.length - ivSize;
        byte[] encryptedBytes = new byte[encryptedSize];
        System.arraycopy(encryptedIvTextBytes, ivSize, encryptedBytes, 0, encryptedSize);

        // Decryption
        SecretKey keyspec = new SecretKeySpec(key.getBytes(CommonConstants.UTF8), "AES");
        AlgorithmParameterSpec ivspec = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
        byte[] aesdecode = cipher.doFinal(encryptedBytes);

        // unpad
        byte[] origin = new byte[aesdecode.length - (aesdecode[aesdecode.length - 1])];
        System.arraycopy(aesdecode, 0, origin, 0, origin.length);

        return new String(origin, CommonConstants.UTF8);
    }
}

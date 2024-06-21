package com.inyongtisto.myhelper.util;

import android.util.Base64;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionManager {

    private String CIPHER_NAME = "AES/CBC/PKCS5PADDING";
    private String key = "1234567890123456";
    private String iv = "fedcba9876543210";
    private int CIPHER_KEY_LEN = 16; //128 bits

//    private EncryptionManager() {}

    private static class Holder {
        private static final EncryptionManager INSTANCE = new EncryptionManager();
    }

    public static EncryptionManager getInstance() {
        return Holder.INSTANCE;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    public String getIv() {
        return iv;
    }

    /**
     * Encrypt data using AES Cipher (CBC) with 128 bit key
     * key  - key to use should be 16 bytes long (128 bits)
     * iv - initialization vector
     *
     * @param data - data to encrypt
     * @return encryptedData data in base64 encoding with iv attached at end after a :
     */
//    public String encrypt(String data) {
//        try {
//            if (key.length() < CIPHER_KEY_LEN) {
//                int numPad = CIPHER_KEY_LEN - key.length();
//
//                for (int i = 0; i < numPad; i++) {
//                    key += "0"; //0 pad to len 16 bytes
//                }
//
//            } else if (key.length() > CIPHER_KEY_LEN) {
//                key = key.substring(0, CIPHER_KEY_LEN); //truncate to 16 bytes
//            }
//
//
//            IvParameterSpec initVector = new IvParameterSpec(iv.getBytes("ISO-8859-1"));
//            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("ISO-8859-1"), "AES");
//
//            Cipher cipher = Cipher.getInstance(CIPHER_NAME);
//            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, initVector);
//
//            byte[] encryptedData = cipher.doFinal((data.getBytes()));
//
//            String base64_EncryptedData = new String(Base64.encodeToString(encryptedData, Base64.DEFAULT));
//            String base64_IV = new String(Base64.encodeToString(iv.getBytes("ISO-8859-1"), Base64.DEFAULT));
//
//            return base64_EncryptedData + ":" + base64_IV;
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
//        return null;
//    }

    /**
     * Decrypt data using AES Cipher (CBC) with 128 bit key
     * key  - key to use should be 16 bytes long (128 bits)
     *
     * @param data - encrypted data with iv at the end separate by :
     * @return decrypted data string
     */

//    public String decrypt(String data) {
//        try {
//
//            if (key.length() < CIPHER_KEY_LEN) {
//                int numPad = CIPHER_KEY_LEN - key.length();
//
//                for (int i = 0; i < numPad; i++) {
//                    key += "0"; //0 pad to len 16 bytes
//                }
//
//            } else if (key.length() > CIPHER_KEY_LEN) {
//                key = key.substring(0, CIPHER_KEY_LEN); //truncate to 16 bytes
//            }
//
//            String[] parts = data.split(":");
//
//            IvParameterSpec iv = new IvParameterSpec(Base64.decode(parts[1], Base64.DEFAULT));
//            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("ISO-8859-1"), "AES");
//
//            Cipher cipher = Cipher.getInstance(CIPHER_NAME);
//            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
//
//            byte[] decodedEncryptedData = Base64.decode(parts[0], Base64.DEFAULT);
//
//            byte[] original = cipher.doFinal(decodedEncryptedData);
//
//            return new String(original);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
//        return null;
//    }

    public String encrypt(String data) {
        try {
            if (key.length() < CIPHER_KEY_LEN) {
                int numPad = CIPHER_KEY_LEN - key.length();
                for (int i = 0; i < numPad; i++) {
                    key += "0"; //0 pad to len 16 bytes
                }
            } else if (key.length() > CIPHER_KEY_LEN) {
                key = key.substring(0, CIPHER_KEY_LEN); //truncate to 16 bytes
            }

            // Generate a new random IV for each encryption
            SecureRandom random = new SecureRandom();
            byte[] iv = new byte[16];
            random.nextBytes(iv);
            IvParameterSpec initVector = new IvParameterSpec(iv);

            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("ISO-8859-1"), "AES");

            Cipher cipher = Cipher.getInstance(CIPHER_NAME);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, initVector);

            byte[] encryptedData = cipher.doFinal((data.getBytes()));

            String base64_EncryptedData = new String(Base64.encodeToString(encryptedData, Base64.DEFAULT));
            String base64_IV = new String(Base64.encodeToString(iv, Base64.DEFAULT));

            return base64_EncryptedData + ":" + base64_IV;

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public String decrypt(String data) {
        try {
            if (key.length() < CIPHER_KEY_LEN) {
                int numPad = CIPHER_KEY_LEN - key.length();
                for (int i = 0; i < numPad; i++) {
                    key += "0"; //0 pad to len 16 bytes
                }
            } else if (key.length() > CIPHER_KEY_LEN) {
                key = key.substring(0, CIPHER_KEY_LEN); //truncate to 16 bytes
            }

            String[] parts = data.split(":");

            IvParameterSpec iv = new IvParameterSpec(Base64.decode(parts[1], Base64.DEFAULT));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("ISO-8859-1"), "AES");

            Cipher cipher = Cipher.getInstance(CIPHER_NAME);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] decodedEncryptedData = Base64.decode(parts[0], Base64.DEFAULT);

            byte[] original = cipher.doFinal(decodedEncryptedData);

            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

}
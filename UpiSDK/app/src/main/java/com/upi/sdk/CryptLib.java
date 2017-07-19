package com.upi.sdk;

/**
 * Created by NeeloyG on 02-02-2016.
 */


import android.util.Base64;
import android.util.Log;

import com.upi.sdk.utils.SDKUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CryptLib {

    public byte[] SHA256(String paramString)throws Exception{
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(paramString.getBytes("UTF-8"));
        byte[] digest = md.digest();
        return digest;
    }

    public byte[] encrypt(byte[] data, byte[] key)throws Exception
    {
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        byte[] iv = new byte[16];
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        Cipher acipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] arrayOfByte1;
        acipher.init(Cipher.ENCRYPT_MODE, keySpec,ivSpec);
        arrayOfByte1 = acipher.doFinal(data);
        //String str=Base64.encodeToString(arrayOfByte1, Base64.NO_WRAP);
       // Log.e("encryted_text",str);
        return arrayOfByte1;
    }

    public byte[] decrypt(byte[] data, byte[] key)throws Exception
    {
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        byte[] iv = new byte[16];
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        Cipher acipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] arrayOfByte1;
        acipher.init(Cipher.DECRYPT_MODE, keySpec,ivSpec);
        arrayOfByte1 = acipher.doFinal(data);
        return arrayOfByte1;
    }


    public PrivateKey readPrivateKeyFromString(String privateKeyData) throws InvalidKeySpecException,
            NoSuchAlgorithmException, IOException {

        byte[] keyBytes = Base64.decode(privateKeyData, Base64.NO_WRAP);

        // Get private Key
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory fact = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = fact.generatePrivate(pkcs8EncodedKeySpec);

        return privateKey;
    }

    public String decryptData(String encryptedData, PrivateKey privateKey){

        try {
             byte[] encryptedDataBytes = Base64.decode(encryptedData, Base64.NO_WRAP);

            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decryptedData = cipher.doFinal(encryptedDataBytes);
            String palinTextDecryptedData = new String(decryptedData);

            return palinTextDecryptedData;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public byte[] hexStringToByteArray(String s) {
        byte[] b = new byte[s.length() / 2];
        for (int i = 0; i < b.length; i++) {
            int index = i * 2;
            int v = Integer.parseInt(s.substring(index, index + 2), 16);
            b[i] = (byte) v;
        }
        return b;
    }

    public static String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for (byte b : a)
            sb.append(String.format("%02x", b & 0xff));
        return sb.toString();
    }

    public static String encryptDataUsinRSA(String data,PublicKey issuerPublicKey){
        String _out="";
        byte[] dataToEncrypt = data.getBytes();
        byte[] encryptedData = null;
        try {
            //PublicKey pubKey = readPublicKeyFromFile(publicKeyFile);
            //String keyData="MIIBCgKCAQEA+xGZ/wcz9ugFpP07Nspo6U17l0YhFiFpxxU4pTk3Lifz9R3zsIsuERwta7+fWIfxOo208ett/jhskiVodSEt3QBGh4XBipyWopKwZ93HHaDVZAALi/2A+xTBtWdEo7XGUujKDvC2/aZKukfjpOiUI8AhLAfjmlcD/UZ1QPh0mHsglRNCmpCwmwSXA9VNmhz+PiB+Dml4WWnKW/VHo2ujTXxq7+efMU4H2fny3Se3KYOsFPFGZ1TNQSYlFuShWrHPtiLmUdPoP6CV2mML1tk+l7DIIqXrQhLUKDACeM5roMx0kLhUWB8P+0uj1CNlNN4JRZlC7xFfqiMbFRU9Z4N6YwIDAQAB";
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, issuerPublicKey);
            encryptedData = cipher.doFinal(dataToEncrypt);

        } catch (Exception e) {
            //throw new CommonLibraryException(CommonLibraryExceptionList.PUBLICKEY_NOT_FOUND);
            e.printStackTrace();
        }
        _out = Base64.encodeToString(encryptedData, Base64.NO_WRAP);
        return _out;
    }

    public static PublicKey readPublicKeyFromString(String publicKeyData)
            throws InvalidKeySpecException,
            NoSuchAlgorithmException, UnsupportedEncodingException {
        byte[] keyBytes = Base64.decode(publicKeyData.getBytes("utf-8"),
                Base64.NO_WRAP);
        // Get Public Key
        X509EncodedKeySpec rsaPublicKeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory fact = KeyFactory.getInstance("RSA");
        PublicKey publicKey = fact.generatePublic(rsaPublicKeySpec);

        return publicKey;
    }

    public String generateRandomAes256Key() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256);
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] bytes = secretKey.getEncoded();
            return byteArrayToHex(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

}
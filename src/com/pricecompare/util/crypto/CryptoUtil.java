package com.pricecompare.util.crypto;


import javax.crypto.KeyGenerator;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;


import java.security.KeyPairGenerator;
import java.security.KeyPair;
import java.security.PublicKey;
import java.security.PrivateKey;

import java.security.MessageDigest;

import java.security.NoSuchAlgorithmException;

import java.nio.ByteBuffer;

public class CryptoUtil{

   
    private KeyGenerator AESKeyGen = null;
    private KeyPairGenerator RSAKeyGen = null;

    private CryptoUtil()
    {
        try
        {
            this.AESKeyGen = KeyGenerator.getInstance("AES");
            this.RSAKeyGen = KeyPairGenerator.getInstance("RSA");
            this.RSAKeyGen.initialize(1024);
        }
        catch (NoSuchAlgorithmException ex)
        {
            // never occur
            ex.printStackTrace();
        }
    }

    private static class Singleton
    {
        private static CryptoUtil instance = new CryptoUtil();
    }

  // KEY
    public static SecretKey generateAESKey()
    {
        SecretKey key = Singleton.instance.AESKeyGen.generateKey();

        return(key);
    }

    public static KeyPair generateRSAKey()
    {
        KeyPair kp = Singleton.instance.RSAKeyGen.generateKeyPair();

        return(kp);
    }

  // CRYPTO
    public static byte[] AESEncrypt(byte[] in, SecretKey key)
    {
        try
        {
            if (in == null)
                throw(new IllegalArgumentException("in must have walue."));
            if (key == null)
                throw(new IllegalArgumentException("key must have value."));

            // TODO: specified mode and padding.
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.ENCRYPT_MODE, key);

            byte[] out = c.doFinal(in);

            return(out);
        }
        catch (IllegalArgumentException ex)
        {
            throw(ex);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return(null);
        }
    }

    public static byte[] AESDecrypt(byte[] in, SecretKey key)
    {
        try
        {
            if (in == null)
                throw(new IllegalArgumentException("in must have walue."));
            if (key == null)
                throw(new IllegalArgumentException("key must have value."));

            // TODO: specified mode and padding.
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.DECRYPT_MODE, key);

            byte[] out = c.doFinal(in);

            return(out);
        }
        catch (IllegalArgumentException ex)
        {
            throw(ex);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return(null);
        }
    }

    public static byte[] RSAEncrypt(byte[] in, PublicKey key)
    {
        try
        {
            if (in == null)
                throw(new IllegalArgumentException("in must have walue."));
            if (key == null)
                throw(new IllegalArgumentException("key must have value."));

            // TODO: specified mode and padding.
            Cipher c = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            c.init(Cipher.ENCRYPT_MODE, key);

            byte[] out = c.doFinal(in);

            return(out);
        }
        catch (IllegalArgumentException ex)
        {
            throw(ex);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return(null);
        }
    }

    public static byte[] RSADecrypt(byte[] in, PrivateKey key)
    {
        try
        {
            if (in == null)
                throw(new IllegalArgumentException("in must have walue."));
            if (key == null)
                throw(new IllegalArgumentException("key must have value."));

            Cipher c = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            c.init(Cipher.DECRYPT_MODE, key);

            byte[] out = c.doFinal(in);

            return(out);
        }
        catch (IllegalArgumentException ex)
        {
            throw(ex);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return(null);
        }
    }

  // DIGEST
    public static byte[] MD5Digest(byte[] in)
    {
        try
        {
            MessageDigest md = MessageDigest.getInstance("MD5");

            byte[] out = md.digest(in);

            return(out);
        }
        catch (NoSuchAlgorithmException ex)
        {
            // never occur
            ex.printStackTrace();
            return(null);
        }
    }

  // CONVERT
    /** (自己实现�?将HexString转换成byte[]
     * <br />
     * 要保证输入是偶数个否则会丢失�?��端的半个字节.
     */
    public static byte[] hexToBytes(String s)
    {
        int l = s.length() / 2;

        ByteBuffer buf = ByteBuffer.allocate(l);
        for (int i=0; i<s.length(); i+=2)
        {
            buf.put(
                Integer.valueOf(
                    s.substring(i, i+2),
                    16
                ).byteValue()
            );
        }

        return(buf.array());
    }

}

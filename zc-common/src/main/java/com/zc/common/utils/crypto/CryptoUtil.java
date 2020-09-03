package com.zc.common.utils.crypto;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;

import java.security.NoSuchAlgorithmException;

/**
 * @author zichen
 *
 * RSA加密工具
 */
public class CryptoUtil {


    /**
     * 加密
     * @param content   加密内容
     * @param keyType   公钥或私钥
     * @return  { 公钥， 私钥， 密文 }
     */
    public static Crypto encode(String content, KeyType keyType) {
        RSA rsa = new RSA();
        //公钥加密，私钥解密
        return Crypto.builder()
                .publickey(rsa.getPublicKeyBase64())
                .privateKey(rsa.getPrivateKeyBase64())
                .ciphertext(rsa.encryptBase64(StrUtil.bytes(content, CharsetUtil.CHARSET_UTF_8), keyType))
                .build();
    }

    /**
     * 根据私钥解密密文
     * @param key           公钥或私钥
     * @param encrypt       密文
     * @param keyType       公钥或私钥
     * @return              加密内容
     */
    public static String decode(String key, String encrypt, KeyType keyType) {
        return StrUtil.str(
                new RSA(KeyType.PrivateKey == keyType ? key : null, KeyType.PublicKey == keyType ? key : null).decrypt(Base64.decode(encrypt), keyType),
                CharsetUtil.CHARSET_UTF_8);
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        // 公钥加密，私钥解密
        Crypto result = CryptoUtil.encode("cccccc", KeyType.PublicKey);
        System.err.println("公钥：" + result.getPublickey());
        System.err.println("私钥：" + result.getPrivateKey());
        System.err.println("密文：" + result.getCiphertext());
        String decryptStr = CryptoUtil.decode(result.getPrivateKey(), result.getCiphertext(), KeyType.PrivateKey);
        System.err.println("加密内容：" + decryptStr);
        // 私钥加密，公钥解密
        Crypto result2 = CryptoUtil.encode("cccccc", KeyType.PrivateKey);
        System.err.println("公钥2：" + result2.getPublickey());
        System.err.println("私钥2：" + result2.getPrivateKey());
        System.err.println("密文2：" + result2.getCiphertext());
        String decryptStr2 = CryptoUtil.decode(result2.getPublickey(), result2.getCiphertext(), KeyType.PublicKey);
        System.err.println("加密内容2：" + decryptStr2);

        String content = "test中文";
        //随机生成密钥
        byte[] key = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded();
        //构建
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, key);
        //加密为16进制表示
        String encryptHex = aes.encryptBase64(content);
        System.err.println("encryptHex：" + encryptHex);
        //解密为字符串
        String decryptStr3 = aes.decryptStr(encryptHex, CharsetUtil.CHARSET_UTF_8);
        System.err.println("decryptStr3：" + decryptStr3);

        byte[] data = "1".getBytes();
        Sign sign = SecureUtil.sign(SignAlgorithm.MD5withRSA);
        //签名
        byte[] signed = sign.sign(data);
        String signedStr = Base64.encode(signed);
        System.err.println("signedStr：" + signedStr);
        //验证签名
        boolean verify = sign.verify(data, Base64.decode(signedStr));
        System.err.println("verify：" + verify);

        System.err.println(sign.getPublicKeyBase64());
        Sign sign2 = SecureUtil.sign(SignAlgorithm.MD5withRSA);
        sign2.setPublicKey(sign.getPublicKey());
        System.err.println(sign2.verify(data, Base64.decode(signedStr)));


    }

}

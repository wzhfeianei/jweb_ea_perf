package com.ea.trade.thirdpart.hele.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;



/**
 * 文件名称: RSAPrivate.java 类名: RSAPrivate 日期: 2015年8月27日
 *
 * 功能描述：RSA私钥工具包 字符串格式的密钥在未在特殊说明情况下都为BASE64编码格式<br/>
 */
public class RSAPrivate {

	/** 加密算法RSA */
	public static final String KEY_ALGORITHM = "RSA";

	/** 签名算法 */
	public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

	/** RSA最大加密明文大小 */
	private static final int MAX_ENCRYPT_BLOCK = 117;

	/** RSA最大解密密文大小 */
	private static final int MAX_DECRYPT_BLOCK = 128;

	/**
	 * 方法名称: getPrivateKey 功能描述: 获取私钥
	 *
	 * @param sysEnum
	 * @return String
	 */
	public static String getPrivateKey(final String privateKeyStr) {
		if (null == privateKeyStr || "".equals(privateKeyStr.trim())) {
			throw new RuntimeException("系统对应的私钥为空");
		}
		return privateKeyStr;
	}
	/**
	 * 方法名称: getPublicKey 功能描述: 获取公钥
	 *
	 * @param sysEnum
	 * @return String
	 */
	public static String getPublicKey(final String publicKeyStr) {
		if (null == publicKeyStr || "".equals(publicKeyStr.trim())) {
			throw new RuntimeException("系统对应的私钥为空");
		}
		return publicKeyStr;
	}
	/**
	 * 方法名称: encryptByPrivateKey 功能描述: 私钥加密过程
	 * <p>
	 * 私钥加密
	 * </p>
	 *
	 * @param data
	 *            源数据
	 * @param privateKey
	 *            私钥(BASE64编码)
	 * @return byte[] 密文
	 */
	public static byte[] encryptByPrivateKey(final byte[] data, final String privateKey) {
		try {
			byte[] keyBytes = Base64.decode(privateKey);
			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, privateK);
			int inputLen = data.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
					cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
				} else {
					cache = cipher.doFinal(data, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_ENCRYPT_BLOCK;
			}
			byte[] encryptedData = out.toByteArray();
			out.close();
			return encryptedData;
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			return null;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new RuntimeException("私钥加密过程无此加密算法");
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
			throw new RuntimeException("私钥加密过程加密私钥非法");
		} catch (InvalidKeyException e) {
			e.printStackTrace();
			throw new RuntimeException("私钥加密过程加密私钥非法");
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
			throw new RuntimeException("私钥加密过程明文长度非法");
		} catch (BadPaddingException e) {
			e.printStackTrace();
			throw new RuntimeException("私钥加密过程明文数据已损坏");
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("私钥加密过程失败");
		}
	}

	/**
	 * 方法名称: decryptByPrivateKey 功能描述: 私钥解密过程
	 * <P>
	 * 私钥解密
	 * </p>
	 *
	 * @param encryptedData
	 *            已加密数据
	 * @param privateKey
	 *            私钥(BASE64编码)
	 * @return byte[]
	 */
	public static byte[] decryptByPrivateKey(final byte[] encryptedData, final String privateKey) {
		try {
			byte[] keyBytes = Base64.decode(privateKey);
			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.DECRYPT_MODE, privateK);
			int inputLen = encryptedData.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
					cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
				} else {
					cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_DECRYPT_BLOCK;
			}
			byte[] decryptedData = out.toByteArray();
			out.close();
			return decryptedData;
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			return null;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new RuntimeException("私钥解密过程无此算法");
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
			throw new RuntimeException("私钥解密过程解密私钥非法");
		} catch (InvalidKeyException e) {
			e.printStackTrace();
			throw new RuntimeException("私钥解密过程解密私钥非法");
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
			throw new RuntimeException("私钥解密过程密文长度非法");
		} catch (BadPaddingException e) {
			e.printStackTrace();
			throw new RuntimeException("私钥解密过程密文数据已损坏");
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("私钥解密过程失败");
		}
	}

	/**
	 * 方法名称: sign 功能描述:
	 * <p>
	 * 用私钥对信息生成数字签名
	 * </p>
	 *
	 * @param data
	 *            已加密数据
	 * @param privateKey
	 *            私钥(BASE64编码)
	 * @return String 签名值
	 */
	public static String sign(final byte[] data, final String privateKey) {
		try {
			byte[] keyBytes = Base64.decode(privateKey);
			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
			Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
			signature.initSign(privateK);
			signature.update(data);
			return Base64.encode(signature.sign());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("私钥签名异常");
		}
	}

	public static byte[] encryptByPublicKey(final byte[] data, final String publicKey) {
		try {
			// System.out.println("key Paramter: "+new String(publicKey));
			byte[] keyBytes = Base64.decode(publicKey);
			// PKCS8EncodedKeySpec pkcs8KeySpec = new
			// PKCS8EncodedKeySpec(keyBytes);
			X509EncodedKeySpec pkcs8KeySpec = new X509EncodedKeySpec(keyBytes);

			// System.out.println("keyBytes: "+new String(keyBytes));

			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

			// Key publicK = keyFactory.generatePrivate(pkcs8KeySpec);
			Key publicK = keyFactory.generatePublic(pkcs8KeySpec);

			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, publicK);

			// System.out.println("key: "+new String(publicK.getEncoded()));

			int inputLen = data.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;

			while (inputLen - offSet > 0) {
				if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
					cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
				} else {
					cache = cipher.doFinal(data, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_ENCRYPT_BLOCK;
			}
			byte[] encryptedData = out.toByteArray();
			out.close();
			return encryptedData;
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			return null;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new RuntimeException("公钥加密过程无此加密算法");
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
			throw new RuntimeException("公钥加密过程加密私钥非法");
		} catch (InvalidKeyException e) {
			e.printStackTrace();
			throw new RuntimeException("公钥加密过程加密私钥非法");
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
			throw new RuntimeException("公钥加密过程明文长度非法");
		} catch (BadPaddingException e) {
			e.printStackTrace();
			throw new RuntimeException("公钥加密过程明文数据已损坏");
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("公钥加密过程失败");
		}
	}

	public static byte[] decryptByPublicKey(final byte[] encryptedData, final String publicKey) {
		try {

			// byte[] keyBytes = Base64.decode(publicKey);
			//
			// X509EncodedKeySpec pkcs8KeySpec = new
			// X509EncodedKeySpec(keyBytes);
			// KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			// Key publicK = keyFactory.generatePublic(pkcs8KeySpec);
			// Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			// cipher.init(Cipher.ENCRYPT_MODE, publicK);

			byte[] keyBytes = Base64.decode(publicKey);

			// PKCS8EncodedKeySpec pkcs8KeySpec = new
			// PKCS8EncodedKeySpec(keyBytes);
			X509EncodedKeySpec pkcs8KeySpec = new X509EncodedKeySpec(keyBytes);

			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			Key publicK = keyFactory.generatePublic(pkcs8KeySpec);
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.DECRYPT_MODE, publicK);
			int inputLen = encryptedData.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
					cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
				} else {
					cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_DECRYPT_BLOCK;
			}
			byte[] decryptedData = out.toByteArray();
			out.close();
			return decryptedData;
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			return null;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new RuntimeException("公钥解密过程无此算法");
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
			throw new RuntimeException("公钥解密过程解密私钥非法");
		} catch (InvalidKeyException e) {
			e.printStackTrace();
			throw new RuntimeException("公钥解密过程解密私钥非法");
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
			throw new RuntimeException("公钥解密过程密文长度非法");
		} catch (BadPaddingException e) {
			e.printStackTrace();
			throw new RuntimeException("公钥解密过程密文数据已损坏");
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("公钥解密过程失败");
		}
	}

}

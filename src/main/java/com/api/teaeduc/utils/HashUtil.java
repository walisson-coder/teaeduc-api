package com.api.teaeduc.utils;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.apache.commons.codec.binary.Hex;

public class HashUtil {

	public static String sha1Base64(String value) throws NoSuchAlgorithmException {
		MessageDigest algorithm = MessageDigest.getInstance("SHA-1");
		byte buf[] = value.getBytes();
		algorithm.reset();
		algorithm.update(buf);
		byte result[] = algorithm.digest();
		return Base64.getEncoder().encodeToString(result);
	 }
	
	public static String hashToMD5(String text) throws NoSuchAlgorithmException {
		MessageDigest messageDigest;
		messageDigest = MessageDigest.getInstance("MD5");
		messageDigest.reset();
		messageDigest.update(text.getBytes(Charset.forName("UTF8")));
		byte[] resultByte = messageDigest.digest();
		String result = new String(Hex.encodeHex(resultByte));
		return result;
	}
	
	public static String hashToMD5(byte[] bytes) throws NoSuchAlgorithmException {
		MessageDigest messageDigest;
		messageDigest = MessageDigest.getInstance("MD5");
		messageDigest.reset();
		messageDigest.update(bytes);
		byte[] resultByte = messageDigest.digest();
		String result = new String(Hex.encodeHex(resultByte));
		return result;
	}
	
	public static String hashToSHA1(String text) throws NoSuchAlgorithmException {
		return hashToSHA1(text.getBytes(Charset.forName("UTF8")));
	}
	
	public static String hashToSHA1(byte[] bytes) throws NoSuchAlgorithmException {
		MessageDigest messageDigest;
		messageDigest = MessageDigest.getInstance("SHA-1");
		messageDigest.reset();
		messageDigest.update(bytes);
		byte[] resultByte = messageDigest.digest();
		String result = new String(Hex.encodeHex(resultByte));
		return result;
	}
	
	public static String criptografar(String pwd)
			throws NoSuchAlgorithmException {
		if (!initialized)
			initialize();
		byte buf[] = pwd.getBytes();
		algorithm.reset();
		algorithm.update(buf);
		byte result[] = algorithm.digest();
		String aux = Base64.getEncoder().encodeToString(result);
		return aux;
	}

	public static void initialize() throws NoSuchAlgorithmException {
		algorithm = MessageDigest.getInstance("SHA-1");
		initialized = true;
	}

	private static MessageDigest algorithm = null;
	private static boolean initialized = false;
	
// 	public static void main(String[] args) throws NoSuchAlgorithmException {
// 		String pwd = "teste";
// 		String enc = criptografar(pwd);
// //		System.out.println("Hash: " + enc);
// 	}

}

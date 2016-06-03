package bulletinBoard.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.tomcat.util.codec.binary.Base64;



/**
 *
 * 暗号化ユーティリティー1
 *
 */

public class CipherUtil {

	/**
	 * SHA-256で暗号化し、バイト配列をBase64エンコーディングします。
	 */

	public static String encrypt(String target){
		try{
			MessageDigest md =MessageDigest.getInstance("SHA-256");
			md.update(target.getBytes());
			return Base64.encodeBase64URLSafeString(md.digest());
		}catch (NoSuchAlgorithmException e){
			throw new RuntimeException(e);
		}
	}
}

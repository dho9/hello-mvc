package com.sh.mvc.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Base64.Encoder;

public class HelloMvcUtils {
	/**
	 * --//평문 - > 암호문 
	-- 평문 <- 암호문 
	-- 양방향 복호화, 한뱡향 암보화 
	-- 비밀번호같은것은 단방향으로 추적못하게. 주민번호, 개인정보는 양방향 
	-- 암호화 처리
	-- 1. MessageDigest - > 암호화
	-- 2.  결과문은 바이트배열 -> Base64Encoding 을통해 읽을수있게 
	 */
	
	/**
	 * 단방향 암호화(Hashing)
	 * 평문은 특정알고리즘에 의해 암호문으로 변환하면 이를 다시 평문으로 복호화 할 수없다.
	 * 결과값은 고징길이의 해시값은 평문이 비슷해도 전혀 다른결과를 출력함. 유추하기 힘들다.
	 * 
	 *  암호화알고리즘 : md5, sha1, sha256, sha512 등
	 *  	최소 sha256이상의 알고리즘을 사용해야함 
	 *  
	 *  1. MessageDigest
	 *  2. Base64 Encoding
	 * @param memberId 
	 *  
	 */
	public static String getEncryptedPassword(String rawPassword, String salt) {

		String encryptedPassword = null;
		//1. 암호화
		byte[] output = null;
		try {
			MessageDigest md= MessageDigest.getInstance("SHA-512");
			byte[] input = rawPassword.getBytes("utf-8");
			byte[] saltBytes = salt.getBytes("utf-8");
			md.update(saltBytes); // salt추가 ! 
			output = md.digest(input); //평문 비밀번호 전달 메소드
			/**
			 * 1ARVn2Auq2/WAqx2gNrL+q3RNjAzXpUfCXrzkA6d4Xa22yhRLy4AC50E+6UTPoscbo31nbOoq51gvkuXzJ6B2w== 1234
				1ARVn2Auq2/WAqx2gNrL+q3RNjAzXpUfCXrzkA6d4Xa22yhRLy4AC50E+6UTPoscbo31nbOoq51gvkuXzJ6B2w==1234 솔트추가전
				VIi0PNQIR6nQJypdEPFPop3CH3Lj5Vy6AlB7Nw8VV26F+mFbVfLfKHeWtoywcEnCXeO/d7xgnpm9Y8dipgIz5A==1234 솔트추가후 
			 */
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.out.println(new String(output));
		//2. 인코딩
		Encoder encoder = Base64.getEncoder();
		encryptedPassword = encoder.encodeToString(output);
		System.out.println(encryptedPassword); // db에 넣을데이터는이거임 !
		return encryptedPassword;
	}

}

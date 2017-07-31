package com.FG.utils.AESencription;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/*Author : Diana P Lazar
Date created : 10/20/2015
Copyright@ FindGoose
*/

/*Out of the many Cryptographic algorithms, AES seems to be the most secure. Other Cryptographic Algorithms are: DES, ...abstract
The current code is from  : http://www.code2learn.com/2011/06/encryption-and-decryption-of-data-using.html
	
	Many people face problem while decrypting the encrypted data as the KEY used for encryption 
	if stored as String in database then it becomes very tough to use that string as the KEY. 
	So below is the code where you only need to store the encrypted code and not the  key. 
	The decryption will take place as an when wanted.

	For encryption we must use a secret key along with an algorithm. 
	In the following example we use an algorithm called AES 128 and 
	the bytes of the word "TheBestSecretKey" as the secret key (the best 
			secret key we found in this world). AES algorithm can use a 
	key of 128 bits (16 bytes * 8); so we selected that key.*/
public class AESencrp {
    
     private static final String ALGO = "AES";
    private static final byte[] keyValue = 
        new byte[] { 'T', 'h', 'e', 'B', 'e', 's', 't',
'S', 'e', 'c', 'r','e', 't', 'K', 'e', 'y' };

public static String encrypt(String Data) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(Data.getBytes());
        String encryptedValue = new BASE64Encoder().encode(encVal);
        return encryptedValue;
    }

    public static String decrypt(String encryptedData) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedData);
        byte[] decValue = c.doFinal(decordedValue);
        String decryptedValue = new String(decValue);
        return decryptedValue;
    }
    private static Key generateKey() throws Exception {
        Key key = new SecretKeySpec(keyValue, ALGO);
        return key;
}

}
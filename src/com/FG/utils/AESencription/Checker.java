package com.FG.utils.AESencription;

/*We use "generateKey()" method to generate 
a secret key for AES algorithm with a given key.
Below is the code how you can use the above encryption algorithm.
refer AESencrp class for more info*/

/*Author : Diana P Lazar
Date created : 10/20/2015
Copyright@ FindGoose
*/

public class Checker {

    public static void main(String[] args) throws Exception {

        String password = "mypassword";
        String passwordEnc = AESencrp.encrypt(password);
        String passwordDec = AESencrp.decrypt(passwordEnc);

        System.out.println("Plain Text : " + password);
        System.out.println("Encrypted Text : " + passwordEnc);
        System.out.println("Decrypted Text : " + passwordDec);
    }
}
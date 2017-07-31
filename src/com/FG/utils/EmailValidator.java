package com.FG.utils;

/*Author : Diana P Lazar
Date created : 06/21/2016
Copyright@ FindGoose
*/

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator{

  private Pattern pattern;
  private Matcher matcher;

  private static final String EMAIL_PATTERN = 
               "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

  protected EmailValidator(){
      pattern = Pattern.compile(EMAIL_PATTERN);
  }

  /**
   * Validate hex with regular expression
   * @param hex hex for validation
   * @return true valid hex, false invalid hex
   */
  public boolean validate(final String hex){

      matcher = pattern.matcher(hex);
      return matcher.matches();

  }
	   private static EmailValidator instance = null;
	   public static EmailValidator getInstance() {
	      if(instance == null) {
	         instance = new EmailValidator();
	      }
	      return instance;
	   }
  
  
}
package com.api.teaeduc.utils;

import java.text.Normalizer;


public class StringConvertUtil {
	
	private StringConvertUtil() {}

	public static String removerCaracteresEspeciais(String text) {
		if (text == null)
			return text;
		
	    return text.replaceAll("[ãâàáä]", "a")   
	                .replaceAll("[êèéë]", "e")   
	                .replaceAll("[îìíï]", "i")   
	                .replaceAll("[õôòóö]", "o")   
	                .replaceAll("[ûúùü]", "u")   
	                .replaceAll("[ÃÂÀÁÄ]", "A")   
	                .replaceAll("[ÊÈÉË]", "E")   
	                .replaceAll("[ÎÌÍÏ]", "I")   
	                .replaceAll("[ÕÔÒÓÖ]", "O")   
	                .replaceAll("[ÛÙÚÜ]", "U")   
	                .replace('ç', 'c')   
	                .replace('Ç', 'C')   
	                .replace('ñ', 'n')   
	                .replace('Ñ', 'N')
	                .replace('&', 'E')
	                .replace("!", "")	                
	                .replaceAll ("\\[\\´\\`\\?!\\@\\#\\$\\%\\¨\\*"," ")
	                .replaceAll("\\(\\)\\=\\{\\}\\[\\]\\~\\^\\]"," ")
	                .replaceAll("[\\.\\;\\-\\_\\+\\'\\ª\\º\\:\\;\\/]"," ");
	    	
	}	

	public static String normlizeString(String str){
		return Normalizer.normalize(str, Normalizer.Form.NFC).replaceAll("[^\\p{ASCII}]", "");
	}
}
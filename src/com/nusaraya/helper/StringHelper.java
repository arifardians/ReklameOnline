package com.nusaraya.helper;

import java.util.Locale;

public class StringHelper {
	private static final String JAN = "Januari"; 
	private static final String FEB = "Februari"; 
	private static final String MAR = "Maret";
	private static final String APR = "April"; 
	private static final String MEI = "Mei"; 
	private static final String JUN = "Juni"; 
	private static final String JUL = "Juli"; 
	private static final String AGS = "Agustus"; 
	private static final String SEP = "September"; 
	private static final String OCT = "Oktober"; 
	private static final String NOV = "November"; 
	private static final String DES = "Desember";
	
	private static final String[] CAPITALIZE_EXCEPTION = {"PT.", "ATM", "PT", "CV.", "UD."};
	
	public static String toCapitalize(String string) {
        String[] words = string.split(" ");
        String result = "";
        for (String word : words) {
        	if(isAbbreviationWord(word)){
        		result += word+" ";
        	}else{
        		result += word.substring(0, 1).toUpperCase(Locale.getDefault())
        				+ word.substring(1).toLowerCase(Locale.getDefault()) + " ";
        	}
        }
        return result;
    }
	
	private static boolean isAbbreviationWord(String word){
		boolean isAbbreviation = false;
		for (int i = 0; i < CAPITALIZE_EXCEPTION.length; i++) {
			if(word.toUpperCase().equals(CAPITALIZE_EXCEPTION[i])){
				isAbbreviation = true;
			}
		}
		return isAbbreviation;
	}
    
    public static String dateFormatIndonesia(String date){
    	String[] split = date.split("-"); 
    	String tahun = split[0]; 
    	String tanggal = split[2];
    	String bulanIndex = split[1]; 
    	String bulan = null;
    	switch (Integer.parseInt(bulanIndex)) {
		case 1:
			bulan = JAN;
			break;
		case 2: 
			bulan = FEB; 
			break; 
		case 3: 
			bulan = MAR; 
			break; 
		case 4: 
			bulan = APR; 
			break; 
		case 5:
			bulan = MEI; 
			break; 
		case 6: 
			bulan = JUN; 
			break; 
		case 7: 
			bulan = JUL; 
			break; 
		case 8: 
			bulan = AGS; 
			break; 
		case 9: 
			bulan = SEP; 
			break; 
		case 10: 
			bulan = OCT; 
			break; 
		case 11:
			bulan = NOV; 
			break;
		case 12: 
			bulan = DES; 
			break;
			
		default:
			bulan = "Unknown";
			break;
		}
    	
    	return Integer.parseInt(tanggal) + " "+bulan + " "+tahun;
    }
}

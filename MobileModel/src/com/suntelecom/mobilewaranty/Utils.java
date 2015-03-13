package com.suntelecom.mobilewaranty;

import com.suntelecom.mobilewaranty.Contants.GuaranteeType;

public class Utils {

	public static int getGuaranteeType(String gua){
		String s = gua.toLowerCase();
		if (s.equals("no")) {
			return GuaranteeType.NO.getValue();
		}else if (s.equals("yes")) {
			return GuaranteeType.YES.getValue();
		}else if (s.equals("refurbish")) {
			return GuaranteeType.REFURBISH.getValue();
		}else if (s.equals("htcbhqt")) {
			return GuaranteeType.HTCBHQT.getValue();
		}else{
			return GuaranteeType.NO.getValue();
		}
	}
	
	public static String getGuaranteeString(int gua){
		if (gua == GuaranteeType.NO.getValue()) {
			return "NO";
		}else if (gua == GuaranteeType.YES.getValue()) {
			return "YES";
		}else if (gua == GuaranteeType.REFURBISH.getValue()) {
			return "REFURBISH";
		}else if (gua == GuaranteeType.HTCBHQT.getValue()) {
			return "HTC BHQT";
		}else{
			return "NO";
		}
	}
}

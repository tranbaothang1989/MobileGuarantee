package com.android.mobilemodel;

public class Contants {

	public  enum GuaranteeType{
        YES(0),NO(1),REFURBISH(2),HTCBHQT(3);
        private int value;

        GuaranteeType(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }
	
	public static final int INSERT_OK =1;
	public static final int INSERT_FAIL =0;
}

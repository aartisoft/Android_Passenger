package com.netcabs.utils;

public class CardValidationChecking {

	private String retrunResult = "";
	static int array[], count, flag = 0;

	static Boolean DiscoverCard() {
		int i, j;
		if (array[1] == 5 && count == 16) {
			return true;
		} else if (array[1] == 0 && array[2] == 1 && array[3] == 1 && count == 16) {
			return true;
		} else if (array[1] == 4 && count == 16) {
			for (i = 4; i <= 9; i++) {
				if (array[2] == i) {
					return true;
				}
					
			}
		} else if (array[1] == 2 && array[2] == 2 && count == 16) {
			j = array[3] * 100 + array[4] * 10 + array[5];
			for (i = 126; i <= 925; i++) {
				if (j == i) {
					return true;
				}
					
			}
		}
		flag = 1;
		return false;
	}

	static Boolean MasterCard() {
		int i;
		if (count == 16) {
			for (i = 0; i <= 5; i++) {
				if (array[1] == i) {
					return true;
				}
					
			}
		} else {
			flag = 1;
		}
		return false;
	}

	public String getdigit(String n) {
		array = new int[17];
		int i = 0;
		char c;
		count = n.length();
		for (i = 0; i < count; i++) {
			c = n.charAt(i);
			if (c >= 48 && c <= 57) {
				array[i] = c - '0';
			} else {
				System.out.print("Insert valid card number");
				System.out.print('\n');
				return "";
			}
		}
		if (array[0] == 4) {
			if (count == 13 || count == 16) {
				retrunResult = "visa";
				doubleup();
			} else {
				flag = 1;
			}

		} else if (array[0] == 6 && DiscoverCard()) {
			retrunResult = "discover";
			doubleup();

		} else if (array[0] == 5 && MasterCard()) {
			retrunResult = "master";
			doubleup();

		} else if (array[0] == 3 && (array[1] == 4 || array[1] == 7)) {
			if (count == 15) {
				retrunResult = "americanexpress";
				doubleup();
			} else {
				flag = 1;
			}

		} else {
			return "invalid invalid";
		}
		if (flag == 1) {
			return "invalid invalid";
		}
		
		return retrunResult;
	}

	public void doubleup() {
		int i, j = count - 2;
		for (i = j; i >= 0; i -= 2) {
			array[i] = array[i] * 2;
			// System.out.print(array[i]);
		}

		/*
		 * System.out.print("double----> "); for(i=0;i<count-1;i++){
		 * 
		 * System.out.print(array[i]); } System.out.print('\n');
		 */
		sumprdct();
	}

	public void sumprdct() {
		int i, j = count - 2, tmp, sum, k;
		for (i = j; i >= 0; i -= 2) {
			sum = 0;
			tmp = array[i];
			if (tmp > 9) {

				while (tmp > 0) {
					sum += (tmp % 10);
					tmp /= 10;
				}
				array[i] = sum;
			}

		}
		k = sumalldigit();
		chkvalidity(k);
	}

	public int sumalldigit() {
		int i, j = 0;
		for (i = 0; i < count - 1; i++) {
			j += array[i];
		}

		return j;
	}

	public void chkvalidity(int s) {
		s *= 9;
		if (s % 10 == array[count - 1]) {
			retrunResult = retrunResult + " valid";
		} else {
			retrunResult = retrunResult + " invalid";
		}
	}
}

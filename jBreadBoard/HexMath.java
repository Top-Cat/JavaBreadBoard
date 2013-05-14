package jBreadBoard;

public class HexMath {
	public static int HexChartoInt(char hex) {
		if (hex == '0') {
			return 0;
		}
		if (hex == '1') {
			return 1;
		}
		if (hex == '2') {
			return 2;
		}
		if (hex == '3') {
			return 3;
		}
		if (hex == '4') {
			return 4;
		}
		if (hex == '5') {
			return 5;
		}
		if (hex == '6') {
			return 6;
		}
		if (hex == '7') {
			return 7;
		}
		if (hex == '8') {
			return 8;
		}
		if (hex == '9') {
			return 9;
		}
		if (hex == 'A') {
			return 10;
		}
		if (hex == 'B') {
			return 11;
		}
		if (hex == 'C') {
			return 12;
		}
		if (hex == 'D') {
			return 13;
		}
		if (hex == 'E') {
			return 14;
		}
		if (hex == 'F') {
			return 15;
		}
		if (hex == 'a') {
			return 10;
		}
		if (hex == 'b') {
			return 11;
		}
		if (hex == 'c') {
			return 12;
		}
		if (hex == 'd') {
			return 13;
		}
		if (hex == 'e') {
			return 14;
		}
		if (hex == 'f') {
			return 15;
		}

		return -1;
	}

	public static int HexStringtoInt(String hex) {
		int length = hex.length();
		int retVal = 0;

		for (int i = 0; i < length; i++) {
			retVal = (int) (retVal + HexMath.HexChartoInt(hex.charAt(i)) * Math.pow(16.0D, length - i - 1));
		}
		return retVal;
	}
}

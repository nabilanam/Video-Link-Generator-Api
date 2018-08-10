package com.nabilanam.downloader.youtube.cipher;

public class SwapCipherOperation implements CipherOperation {

	private final int index;

	public SwapCipherOperation(int index) {
		this.index = index;
	}

	@Override
	public String decipher(String input) {
		char[] chars = input.toCharArray();
		char f = chars[0];
		chars[0] = chars[index];
		chars[index] = f;
		return String.valueOf(chars);
	}
}

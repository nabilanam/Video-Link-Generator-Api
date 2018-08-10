package com.nabilanam.downloader.youtube.cipher;

public class ReverseCipherOperation implements CipherOperation {
	@Override
	public String decipher(String input) {
		return new StringBuilder(input).reverse().toString();
	}
}

package com.nabilanam.downloader.youtube.cipher;

public class SliceCipherOperation implements CipherOperation {

	private final int index;

	public SliceCipherOperation(int index) {
		this.index = index;
	}

	@Override
	public String decipher(String input) {
		return input.substring(index);
	}
}

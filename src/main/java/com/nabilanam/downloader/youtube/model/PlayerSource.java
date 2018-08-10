package com.nabilanam.downloader.youtube.model;

import com.nabilanam.downloader.youtube.cipher.CipherOperation;

import java.util.List;

public class PlayerSource {
	private final List<CipherOperation> operations;

	public PlayerSource(List<CipherOperation> operations) {
		this.operations = operations;
	}

	public String decipher(String input){
		for (CipherOperation operation : operations){
			input = operation.decipher(input);
		}
		return input;
	}
}

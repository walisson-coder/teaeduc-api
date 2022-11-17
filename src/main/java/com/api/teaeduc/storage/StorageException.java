package com.api.teaeduc.storage;

public class StorageException extends RuntimeException {

	private static final long serialVersionUID = -6539028696080177893L;

	public StorageException(String message) {
		super(message);
	}

	public StorageException(String message, Throwable cause) {
		super(message, cause);
	}
}

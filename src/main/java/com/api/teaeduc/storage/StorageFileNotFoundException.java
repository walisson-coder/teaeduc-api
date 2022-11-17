package com.api.teaeduc.storage;

public class StorageFileNotFoundException extends StorageException {

	private static final long serialVersionUID = 898788013358519689L;

	public StorageFileNotFoundException(String message) {
		super(message);
	}

	public StorageFileNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}

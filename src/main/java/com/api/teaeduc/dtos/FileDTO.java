package com.api.teaeduc.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileDTO extends BaseDTO {
	private byte[] bytes;
	private String fileName;
	private String mimeType;
}

package com.api.teaeduc.storage;

import java.nio.file.Path;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.api.teaeduc.utils.BusinessException;

public interface StorageService {

	void init(Path path);

	void store(MultipartFile file, String relativePath);

	Path load(String filename);

	Resource loadAsResource(String filename);

	void deleteAnexo(String fileName) throws BusinessException;

}

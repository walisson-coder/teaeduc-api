package com.api.teaeduc.storage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.api.teaeduc.utils.BusinessException;
import com.api.teaeduc.utils.StorageTransferUtil;

@Service
public class FileSystemStorageService implements StorageService {

	@Override
	public void store(MultipartFile file, String relativePath) {
	
		final String filename = relativePath;
		
		try {
			if (file.isEmpty()) {
				throw new StorageException("Falha ao armazenar arquivo vazio" + filename);
			}
			if (filename.contains("..")) {
				throw new StorageException(
						"Não e possível salvar o arquivo relativo for do diretório atual " + filename);
			}
			try (InputStream inputStream = file.getInputStream()) {

				//path onde o nome do arquivo
				String absolutePath = StorageTransferUtil.FILE_SYSTEM_STORAGE_PATH + filename;

				//path onde sera crado a pasta
				String pathCreateDirectories = StorageTransferUtil.FILE_SYSTEM_STORAGE_PATH + "/" + relativePath;
				File filePath = new File(absolutePath);
				File createDirectories = new File(pathCreateDirectories);

				//dar permissão nas pastas
				createDirectories.setExecutable(true, false);
				createDirectories.setReadable(true, false);
				createDirectories.setWritable(true, false);

				this.init(createDirectories.toPath());

				Files.copy(inputStream, filePath.toPath() , StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (final IOException e) {
			throw new StorageException("Falha para armazenar o arquivo " + filename, e);
		}
	}

	@Override
	public Resource loadAsResource(final String filename) {
		try {
			final Path file = load(filename);
			final Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new StorageFileNotFoundException("Não foi possivel ler o arquivo: " + filename);

			}
		} catch (final MalformedURLException e) {
			throw new StorageFileNotFoundException("Não foi possivel ler o arquivo: " + filename, e);
		}
	}

	@Override
	public Path load(String filename) {
		String absolutePath = StorageTransferUtil.FILE_SYSTEM_STORAGE_PATH + filename;
		File filePath = new File(absolutePath);
		return filePath.toPath();
	}


	@Override
	public void init(Path path) {
		try {
			File file = new File(path.toString());
			if(!file.exists()){
				Files.createDirectories(path);
			}
		} catch (final IOException e) {
			throw new StorageException("Erro ao criar pasta", e);
		}
	}

	@Override
	public void deleteAnexo(String fileName) throws BusinessException {
        if(fileName != null && !fileName.equals("")){
            File file = new File(StorageTransferUtil.FILE_SYSTEM_STORAGE_PATH + "/", fileName);
            if(file.exists()){
                try {
					StorageTransferUtil.deleteDirectory(fileName);
				} catch (IOException e) {
					throw new BusinessException("Não foi possível remover arquivo.");
				}
            }
        }
    }
}

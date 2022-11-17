package com.api.teaeduc.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Base64;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class StorageTransferUtil {

    private StorageTransferUtil() {
    }

    public static final String PATH_SEPARATOR = "/";

    public static final String FILE_SYSTEM_STORAGE_PATH = getProperties().getProperty("storagePath");

    private static final Logger logger = Logger.getLogger(StorageTransferUtil.class.getName());

    private static final String PERMISSAO_LEITURA = "Erro ao tentar dar permissão de leitura para o arquivo: {0}";
    private static final String PERMISSAO_ESCRITA = "Erro ao tentar dar permissão de escrita para o arquivo: {0}";
    private static final String PERMISSAO_EXECUCAO = "Erro ao tentar dar permissão de execução para o arquivo: {0}";
    private static final String ERROR_UPLOAD = "Erro ao tentar salvar o arquivo no storage (Mensagem: %s )";

    private static Properties getProperties() {
        Properties properties = new Properties();
        InputStream is = StorageTransferUtil.class.getClassLoader().getResourceAsStream("application.properties");

        try {
            properties.load(is);
        }
        catch (IOException e) {
            logger.log(Level.SEVERE, "Erro ao tentar carregar o arquivo de propriedades (Mensagem: {0})", e.getMessage());
        }

        return properties;
    }

    public static String upload(String savePath, String fileName, String base64) throws BusinessException {
        String type = null;

        if (!base64.contains(",") && fileName.contains(".")) {
            type = fileName.substring(fileName.lastIndexOf('.') + 1);
            fileName = fileName.replace("." + type, "");
        }

        return upload(savePath, fileName, type, base64);
    }

    public static String upload(String savePath, String fileName, String type, String base64) throws BusinessException {

        if (base64 == null)
            return null;

        String fileType = null;
        if (type != null && !type.isEmpty()) {
            fileType = type;
        }
        else if (base64.contains(",")) {
            String header = base64.substring(0, base64.indexOf(',') + 1);
            fileType = header.substring(header.indexOf(PATH_SEPARATOR) + 1, header.indexOf(';'));
            base64 = base64.replace(header, "");
        }

        byte[] bytes = Base64.getDecoder().decode(base64.getBytes(StandardCharsets.UTF_8));

        return upload(savePath, fileName, fileType, bytes);
    }

    public static String upload(String savePath, String fileName, InputStream file) throws BusinessException {

        if (file == null)
            return null;

        try {
            byte[] bytes = IOUtils.toByteArray(file);
            return upload(savePath, fileName, bytes);
        }
        catch (IOException e) {
            throw new BusinessException(String.format(ERROR_UPLOAD, e.getMessage()));
        }
    }

    public static String upload(String savePath, String fileName, String type, byte[] bytes) throws BusinessException {
        String fileNameAux = fileName;

        if (type != null)
            fileNameAux = fileNameAux + "." + type;

        return upload(savePath, fileNameAux, bytes);
    }

    public static String upload(String savePath, String fileName, byte[] bytes) throws BusinessException {

        if (bytes == null)
            return null;

        if (!savePath.endsWith(PATH_SEPARATOR))
            savePath += PATH_SEPARATOR;

        createDirectories(savePath);

        String fileToSave = savePath + fileName;

        try {
            FileUtils.writeByteArrayToFile(new File(FILE_SYSTEM_STORAGE_PATH + fileToSave), bytes);

            File file = new File(FILE_SYSTEM_STORAGE_PATH + fileToSave);

            if (!file.setReadable(true, false))
                logger.log(Level.SEVERE, PERMISSAO_LEITURA, fileToSave);

            if (!file.setWritable(true, false))
                logger.log(Level.SEVERE, PERMISSAO_ESCRITA, fileToSave);

            if (!file.setExecutable(true, false))
                logger.log(Level.SEVERE, PERMISSAO_EXECUCAO, fileToSave);

            return fileToSave;
        }
        catch (IOException e) {
            throw new BusinessException(String.format(ERROR_UPLOAD, e.getMessage()));
        }
    }

    private static void createDirectories(String path) throws BusinessException {
        StringBuilder currentDirectory = new StringBuilder(FILE_SYSTEM_STORAGE_PATH);

        String pattern = Pattern.quote(System.getProperty("file.separator"));
        String[] paths = path.split(pattern);

        for (String pathAux : paths) {
            currentDirectory.append(pathAux + PATH_SEPARATOR);
            File file = new File(currentDirectory.toString());

            // if (!file.exists() && !file.mkdir())
            //         throw new BusinessException("Falha ao criar o diretorio: " + currentDirectory.toString());

            if (!file.setWritable(true, false))
                logger.log(Level.SEVERE, PERMISSAO_ESCRITA, currentDirectory.toString());

            if (!file.setReadable(true, false))
                logger.log(Level.SEVERE, PERMISSAO_LEITURA, currentDirectory.toString());

            if (!file.setExecutable(true, false))
                logger.log(Level.SEVERE, PERMISSAO_EXECUCAO, currentDirectory.toString());
        }
    }

    public static void deleteDirectory(String directory) throws IOException {
        deleteDirectory(directory, false);
    }

    public static void deleteDirectory(String directory, boolean absolute) throws IOException {

        String fileNameAux = getFileName(directory, absolute);

        File file = new File(fileNameAux);

        if (file.isDirectory() && file.exists()) {
            File[] files = file.listFiles();

            if (files != null)
                for (File fileAux : files)
                    if (fileAux.isDirectory())
                        deleteDirectory(fileAux.getAbsolutePath(), true);
                    else
                        Files.delete(fileAux.toPath());
        }

        Files.delete(file.toPath());
    }

    private static String getFileName(String directory, boolean absolute) {

        String fileName;

        if (!absolute) {
            fileName = FILE_SYSTEM_STORAGE_PATH;

            if (!directory.startsWith(PATH_SEPARATOR))
                fileName += PATH_SEPARATOR;

            fileName += directory;
        }
        else
            fileName = directory;

        return fileName;
    }

    public static Boolean verifyPathIsBase64(String path) {
        if (path == null)
            return false;

        return path.contains(",");
    }

    public static String removeQueryParams(String path) {
        if (path == null)
            return null;
        if (path.contains("?")) {
            path = path.substring(0, path.indexOf('?'));
        }
        return path;
    }

    public static byte[] download(String relativePath) throws BusinessException {
        File file = new File(FILE_SYSTEM_STORAGE_PATH + relativePath);

        try {
            return Files.readAllBytes(file.toPath());
        }
        catch (IOException e) {
            throw new BusinessException("Erro ao tentar ler o arquivo do storage (Mensagem: " + e.getMessage() + ")");
        }
    }
}
package com.ftn.event_hopper.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

public class ImageUtil {
    private static String imagesDirPath = "src/main/resources/images/";


    public static String saveImage(MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(imagesDirPath);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileExtension = Objects.requireNonNull(multipartFile.getOriginalFilename())
                .substring(multipartFile
                    .getOriginalFilename()
                        .lastIndexOf("."));

        String fileName = getNewIndex() + fileExtension;

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            return fileName;

        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);
        }
    }

    public static byte[] getImage(String imageName) throws IOException {
        try {
            Path path = Paths.get(imagesDirPath).resolve(imageName);
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new IOException("Could not read image file: " + imageName, e);
        }
    }

    public static void deleteImage(String imageName) throws IOException {
        try {
            Path path = Paths.get(imagesDirPath).resolve(imageName);
            Files.delete(path);
        } catch (IOException e) {
            throw new IOException("Could not delete image file: " + imageName, e);
        }
    }

    private static String getNewIndex() {
        File[] files = new File(imagesDirPath).listFiles();
        int maxIndex = 0;
        if (files != null) {
            for (File file : files) {
                String name = file.getName();
                int index = Integer.parseInt(name.substring(0, name.indexOf('.')));
                if (index > maxIndex) {
                    maxIndex = index;
                }
            }
        }
        return String.valueOf(++maxIndex);
    }
}

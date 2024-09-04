package org.forkingaround.adventuretime.services;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.UUID;

@Service
public class ImageService {

    private String uploadFile(File file, String fileName) throws IOException {
        BlobId blobId = BlobId.of("eventsforkingaround.appspot.com", fileName); // Nombre del bucket
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();

        // Cargar las credenciales desde el archivo JSON
        InputStream inputStream = ImageService.class.getClassLoader().getResourceAsStream("firebase-private-key.json"); // Cambia el nombre del archivo si es necesario
        GoogleCredentials credentials = GoogleCredentials.fromStream(inputStream);
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

        // Subir el archivo al bucket
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));

        // Crear la URL de descarga
        String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/eventsforkingaround.appspot.com/o/%s?alt=media";
        return String.format(DOWNLOAD_URL, URLEncoder.encode(fileName, StandardCharsets.UTF_8));
    }

    private File convertToFile(MultipartFile multipartFile, String fileName) throws IOException {
        File tempFile = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(multipartFile.getBytes());
        }
        return tempFile;
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    public String upload(MultipartFile multipartFile) {
        try {
            String fileName = multipartFile.getOriginalFilename();                        // Obtener el nombre original del archivo
            fileName = UUID.randomUUID().toString().concat(this.getExtension(fileName));  // Generar un nombre de archivo aleatorio

            File file = this.convertToFile(multipartFile, fileName);                      // Convertir MultipartFile a File
            String URL = this.uploadFile(file, fileName);                                 // Obtener la URL del archivo subido
            file.delete();                                                                // Eliminar el archivo temporal
            return URL;
        } catch (Exception e) {
            e.printStackTrace();
            return "Image couldn't upload, Something went wrong";
        }
    }
}

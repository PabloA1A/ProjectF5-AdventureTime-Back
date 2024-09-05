package org.forkingaround.adventuretime.services;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ImageServiceTest {

    @Mock
    private Storage storage;

    @Mock
    private MultipartFile multipartFile;

    @InjectMocks
    private ImageService imageService;

    @BeforeEach
    void setup() throws Exception {
        // Configura el mock de Storage para que funcione correctamente con los tests
        when(storage.create(any(BlobInfo.class), any(byte[].class))).thenReturn(mock(Blob.class));
    }

    // Método auxiliar para acceder a métodos privados mediante reflexión
    private Object invokePrivateMethod(String methodName, Class<?>[] paramTypes, Object... params) throws Exception {
        Method method = ImageService.class.getDeclaredMethod(methodName, paramTypes);
        method.setAccessible(true);
        return method.invoke(imageService, params);
    }

    @Test
    void testUploadFile() throws Exception {
        File tempFile = File.createTempFile("test", ".txt");
        tempFile.deleteOnExit();

        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write("test content".getBytes());
        }

        String fileName = UUID.randomUUID().toString();
        String result = (String) invokePrivateMethod("uploadFile", new Class[]{File.class, String.class}, tempFile, fileName);

        assertTrue(result.contains("https://firebasestorage.googleapis.com"));
        verify(storage, times(1)).create(any(BlobInfo.class), any(byte[].class));
    }

    @Test
    void testConvertToFile() throws Exception {
        when(multipartFile.getBytes()).thenReturn("test content".getBytes());
        when(multipartFile.getOriginalFilename()).thenReturn("test.txt");

        File file = (File) invokePrivateMethod("convertToFile", new Class[]{MultipartFile.class, String.class}, multipartFile, "test.txt");

        assertTrue(file.exists());
        assertEquals("test.txt", file.getName());
        file.delete();
    }

    @Test
    void testGetExtension() throws Exception {
        String extension = (String) invokePrivateMethod("getExtension", new Class[]{String.class}, "test.png");
        assertEquals(".png", extension);
    }

    @Test
    void testUpload() throws Exception {
        when(multipartFile.getBytes()).thenReturn("test content".getBytes());
        when(multipartFile.getOriginalFilename()).thenReturn("test.txt");

        String result = imageService.upload(multipartFile);

        assertTrue(result.contains("https://firebasestorage.googleapis.com"));
        verify(storage, times(1)).create(any(BlobInfo.class), any(byte[].class));
    }

    @Test
    void testUploadBase64() throws Exception {
        String fileEncoded = Base64.getEncoder().encodeToString("test content".getBytes());

        Optional<String> result = imageService.uploadBase64(fileEncoded);

        assertTrue(result.isPresent());
        assertTrue(result.get().contains("https://firebasestorage.googleapis.com"));
        verify(storage, times(1)).create(any(BlobInfo.class), any(byte[].class));
    }

    @Test
    void testUploadException() throws Exception {
        when(multipartFile.getBytes()).thenThrow(new IOException("Test exception"));

        String result = imageService.upload(multipartFile);

        assertEquals("Image couldn't upload, Something went wrong", result);
    }

    @Test
    void testUploadBase64Exception() {
        String invalidBase64 = "invalid_base64";

        Optional<String> result = imageService.uploadBase64(invalidBase64);

        assertNull(result);
    }
}

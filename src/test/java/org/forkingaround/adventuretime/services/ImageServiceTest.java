package org.forkingaround.adventuretime.services;

import com.google.cloud.storage.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ImageServiceTest {

    @InjectMocks
    private ImageService imageService;

    @Mock
    private Storage storage;

    @Mock
    private MultipartFile multipartFile;

    private File tempFile;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        tempFile = new File("tempImage.png");
    }

    @AfterEach
    void tearDown() {
        if (tempFile.exists()) {
            tempFile.delete();
        }
    }

    @Test
    void testUploadFailure() throws Exception {
        when(multipartFile.getOriginalFilename()).thenReturn("testimage.png");
        when(multipartFile.getBytes()).thenThrow(new IOException("Error while getting file bytes"));

        String response = imageService.upload(multipartFile);
        assertEquals("Image couldn't upload, Something went wrong", response);
    }

    @Test
    void testGetExtension() throws Exception {
        Method getExtensionMethod = ImageService.class.getDeclaredMethod("getExtension", String.class);
        getExtensionMethod.setAccessible(true);

        String fileName = "image.jpg";
        String extension = (String) getExtensionMethod.invoke(imageService, fileName);
        assertEquals(".jpg", extension);
    }

    @Test
    void testConvertToFile() throws Exception {
        Method convertToFileMethod = ImageService.class.getDeclaredMethod("convertToFile", MultipartFile.class, String.class);
        convertToFileMethod.setAccessible(true);

        String fileName = "image.jpg";
        when(multipartFile.getBytes()).thenReturn("dummy content".getBytes());

        File file = (File) convertToFileMethod.invoke(imageService, multipartFile, fileName);
        assertTrue(file.exists());
        assertEquals(fileName, file.getName());

        file.delete();
    }
}

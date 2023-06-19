package com.dishianerifkinj.util;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class BlobMultipartFile implements MultipartFile {

    private final byte[] blob;
    private final String originalFilename;

    public BlobMultipartFile(byte[] blob, String originalFilename) {
        this.blob = blob;
        this.originalFilename = originalFilename;
    }

    @Override
    public String getName() {
        return originalFilename;
    }

    @Override
    public String getOriginalFilename() {
        return originalFilename;
    }

    @Override
    public String getContentType() {
        return MediaType.IMAGE_PNG_VALUE;
    }

    @Override
    public boolean isEmpty() {
        return blob == null || blob.length == 0;
    }

    @Override
    public long getSize() {
        return blob != null ? blob.length : 0;
    }

    @Override
    public byte[] getBytes() throws IOException {
        if (isEmpty()) {
            return new byte[0];
        }
        try (InputStream inputStream = new ByteArrayInputStream(blob)) {
            return inputStream.readAllBytes();
        }
    }

    @Override
    public InputStream getInputStream() throws IOException {
        if (isEmpty()) {
            return null;
        }
        return new ByteArrayInputStream(blob);
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot transfer an empty MultipartFile");
        }
        try (InputStream inputStream = new ByteArrayInputStream(blob)) {
            Files.copy(inputStream, dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
    }

    @Override
    public void transferTo(Path dest) throws IOException, IllegalStateException {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot transfer an empty MultipartFile");
        }
        try (InputStream inputStream = new ByteArrayInputStream(blob)) {
            Files.copy(inputStream, dest, StandardCopyOption.REPLACE_EXISTING);
        }
    }
}

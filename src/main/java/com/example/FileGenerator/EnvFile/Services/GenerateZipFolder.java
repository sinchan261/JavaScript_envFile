package com.example.FileGenerator.EnvFile.Services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
//import java.nio.file.Files;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
@Slf4j
@Service
public class GenerateZipFolder {

    public Path getZipFile(Path pathDirectory) throws IOException {

        // Create ZIP in the OS temporary directory
        Path zipPath = Files.createTempFile("exam-", ".zip");

        log.info("ZIP path is {}", zipPath);

        try (ZipOutputStream zipOutputStream =
                     new ZipOutputStream(
                             Files.newOutputStream(zipPath)
                     )) {

            Files.walk(pathDirectory)
                    .filter(Files::isRegularFile)
                    .forEach(file -> {

                        try {
                            String entryName = pathDirectory
                                    .relativize(file)
                                    .toString()
                                    .replace("\\", "/");

                            ZipEntry zipEntry = new ZipEntry(entryName);

                            zipOutputStream.putNextEntry(zipEntry);

                            Files.copy(file, zipOutputStream);

                            zipOutputStream.closeEntry();

                        } catch (IOException e) {
                            throw new UncheckedIOException(e);
                        }
                    });
        } catch (UncheckedIOException e) {
            Files.deleteIfExists(zipPath);
            throw e.getCause();
        } catch (IOException e) {
            Files.deleteIfExists(zipPath);
            throw e;
        }

        return zipPath;
    }
}
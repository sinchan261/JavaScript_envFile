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

//    public getZipFolder(){
//        try{
//
//        }catch(IOException exception){
//
//        }
//    }

    public Path getZipFile(Path pathDirectory) throws IOException {
            Path getPath = Path.of("C:\\Users\\DELL\\OneDrive\\Documents\\Maven\\EnvFile\\src\\main\\java\\com\\example\\FileGenerator\\EnvFile\\ZipFile");

            Path zipPath = Files.createTempFile(getPath,"exam" ,".zip");
     log.info("ZipPath is {}",zipPath);
            try (ZipOutputStream zipOutputStream =
                         new ZipOutputStream(
                                 Files.newOutputStream(zipPath)
                         )) {

                Files.walk(pathDirectory)
                        .filter(Files::isRegularFile)
                        .forEach(file -> {

                            try {

                                String entryName =
                                        pathDirectory
                                                .relativize(file)
                                                .toString()
                                                .replace("\\", "/");

                                ZipEntry zipEntry =
                                        new ZipEntry(entryName);

                                zipOutputStream.putNextEntry(zipEntry);

                                Files.copy(
                                        file,
                                        zipOutputStream
                                );

                                zipOutputStream.closeEntry();

                            } catch (IOException e) {
                                throw new UncheckedIOException(e);
                            }
                        });
            }

        return zipPath;
    }

}

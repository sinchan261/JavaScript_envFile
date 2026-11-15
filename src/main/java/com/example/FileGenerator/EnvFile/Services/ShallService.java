package com.example.FileGenerator.EnvFile.Services;

import com.example.FileGenerator.EnvFile.Configuration.SupabaseConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
@Service
public class ShallService {

    private final SupabaseConfiguration supabaseConfiguration;

    private static final Logger log =
            LoggerFactory.getLogger(ShallService.class);

    public ShallService(SupabaseConfiguration supabaseConfiguration) {
        this.supabaseConfiguration = supabaseConfiguration;
    }

    public String generateService(String projectId) throws IOException {

        log.info("Generating script for project: {}", projectId);

        String zipLocation =
                supabaseConfiguration.getSupabaseUrl()
                        + "/storage/v1/object/public/"
                        + "generated-projects/"
                        + projectId
                        + "/project.zip";

        String scriptContent =
                "#!/bin/bash\n\n"
                        + "PROJECT_ID=\"" + projectId + "\"\n"
                        + "ZIP_URL=\"" + zipLocation + "\"\n\n"

                        + "echo \"Project ID: $PROJECT_ID\"\n"
                        + "echo \"Downloading project...\"\n\n"

                        + "curl -L \"$ZIP_URL\" -o project.zip\n\n"

                        + "if [ $? -ne 0 ]; then\n"
                        + "    echo \"Download failed.\"\n"
                        + "    exit 1\n"
                        + "fi\n\n"

                        + "echo \"Download completed.\"\n\n"

                        + "mkdir -p \"$PROJECT_ID\"\n"
                        + "unzip -o project.zip -d \"$PROJECT_ID\"\n\n"

                        + "rm project.zip\n\n"

                        + "echo \"Project generated successfully.\"\n"
                        + "echo \"Location: $PROJECT_ID\"\n";


        return scriptContent;
    }
}

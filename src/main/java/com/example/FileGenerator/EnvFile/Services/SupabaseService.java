package com.example.FileGenerator.EnvFile.Services;

import com.example.FileGenerator.EnvFile.Configuration.SupabaseConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class SupabaseService {

@Autowired
    SupabaseConfiguration supabaseConfiguration;
@Autowired
    RestClient restClient;
@Autowired
KafkaTemplate<String,String> kafkaTemplate;
    @Value("${script.commands.topic.name}")
    private  String ScriptCommandsTopicName;
public String uploadZipToSupabase( Path Zippath) throws IOException {
    String projectId = UUID.randomUUID().toString();

    String storagepath = projectId+"/project.zip";
    byte[] zipBytes = Files.readAllBytes(Zippath);
    restClient.post().uri(supabaseConfiguration.getSupabaseUrl()
    +"/storage/v1/object/"
                    +supabaseConfiguration.getSupabaseBucket()+"/"+storagepath
    ).header("Authorization", "Bearer " + supabaseConfiguration.getSupabaseKey())
            .header("apikey", supabaseConfiguration.getSupabaseKey())
            .contentType(MediaType.APPLICATION_OCTET_STREAM) .body(zipBytes) .retrieve() .toBodilessEntity();
    kafkaTemplate.send(ScriptCommandsTopicName,projectId);
 return projectId;
}
}

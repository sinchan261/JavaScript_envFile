package com.example.FileGenerator.EnvFile.Services;

import org.springframework.stereotype.Service;

@Service
public class GitignoreGeneratorService {

    public String gitIgnore(){
        String content = """
            node_modules/
            .env
            *.log
            """;

        return content;
    }
}

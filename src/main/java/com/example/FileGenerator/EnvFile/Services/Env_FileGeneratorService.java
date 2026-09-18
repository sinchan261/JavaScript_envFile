package com.example.FileGenerator.EnvFile.Services;

import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.random.RandomGenerator;

@Service
public class Env_FileGeneratorService {


    public String getContentEnv(){
        int portNumber = ThreadLocalRandom.current().nextInt(1000,9999);
        String text = """
           PORT = '%d'
           """.formatted(portNumber);
        return text;
    }

}

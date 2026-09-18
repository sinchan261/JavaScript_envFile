package com.example.FileGenerator.EnvFile.Services;

import com.example.FileGenerator.EnvFile.Dto.JsJsonTypeDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
@Service
public class ProjectGenaratoreService {

    @Autowired
    Server_jsGeneratorService serverJsGeneratorService;
    @Autowired
    Env_FileGeneratorService envFileGeneratorService;

    public Path generateProject(JsJsonTypeDto jsJsonTypeDto, String moduleSystem1) throws IOException{
        Path parentDirectory = Path.of(
                "C:\\Users\\DELL\\OneDrive\\Documents\\Maven\\EnvFile\\src\\main\\java\\com\\example\\FileGenerator\\EnvFile\\Example"
        );

        Path projectDirectory = Files.createTempDirectory(
                parentDirectory,
                "Exam" + jsJsonTypeDto.getProjectName()
        );
        log.info("Generator file path is {}",projectDirectory);
        //call for building the project structure
        createDirectories(projectDirectory,jsJsonTypeDto,moduleSystem1);
        return projectDirectory;

    }

    public void createDirectories(Path projectDirectory,JsJsonTypeDto jsJsonTypeDto,String moduleType) throws IOException {
        if(jsJsonTypeDto.getInclude().isControllers()){
           Files.createDirectories(projectDirectory.resolve("controllers"));

        }
        if(jsJsonTypeDto.getInclude().isServices()){
            Files.createDirectories(projectDirectory.resolve("Service"));

        }
        if(jsJsonTypeDto.getInclude().isMiddlewares()){
            Files.createDirectories(projectDirectory.resolve("MiddleWare"));
        }
        if(jsJsonTypeDto.getInclude().isModels()){
            Files.createDirectories(projectDirectory.resolve("Model"));
        }
        if(jsJsonTypeDto.getInclude().isRoutes()){
            Files.createDirectories(projectDirectory.resolve("Route"));
        }
        if(jsJsonTypeDto.getInclude().isConfig()){
            Files.createDirectories(projectDirectory.resolve("Config"));
        }
         // for dotenv file
        if(jsJsonTypeDto.getInclude().isEnv()){
       String content = envFileGeneratorService.getContentEnv();
          Path envFile =   projectDirectory.resolve(".env");
          Files.writeString(envFile,content);
//       Files.createDirectories(envFile);
        }


     //generating Server.js file
      String serverText = serverJsGeneratorService.generateServerFile(projectDirectory,moduleType);
        Path serverfile = projectDirectory.resolve("server.js");

        Files.writeString(serverfile,serverText);

//        Files.createDirectories(serverfile);
    }



}

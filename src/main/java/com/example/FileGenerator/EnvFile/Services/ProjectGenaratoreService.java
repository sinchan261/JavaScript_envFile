package com.example.FileGenerator.EnvFile.Services;

import com.example.FileGenerator.EnvFile.Dto.JsJsonTypeDto;
import com.example.FileGenerator.EnvFile.Dto.ReadmeRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ObjectNode;

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
    @Autowired
    ReadmeGenerator readmeGenerator;
    @Autowired
    GitignoreGeneratorService gitignoreGeneratorService;
    @Autowired
    JsonGenerator jsonGenerator;
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
           Files.createDirectories(projectDirectory.resolve("Controllers/webController.js"));

        }
        if(jsJsonTypeDto.getInclude().isServices()){

            Files.createDirectories(projectDirectory.resolve("Service/webService.js"));

        }
        if(jsJsonTypeDto.getInclude().isMiddlewares()){
            Path middlepath = projectDirectory.resolve("MiddleWare/webMiddleware.js");
            Files.createDirectories(middlepath);
        }
        if(jsJsonTypeDto.getInclude().isModels()){
            Path modelPath = projectDirectory.resolve("Model/webModelPath.js");
            Files.createDirectories(modelPath);
        }
        if(jsJsonTypeDto.getInclude().isRoutes()){
            Path routePath = projectDirectory.resolve("Route/webRouter.js");
            Files.createDirectories(routePath);
        }
        if(jsJsonTypeDto.getInclude().isConfig()){
            Path configPath = projectDirectory.resolve("Config/webConfig.js");
            Files.createDirectories(configPath);
        }

         // for dotenv file
        if(jsJsonTypeDto.getInclude().isEnv()){
       String content = envFileGeneratorService.getContentEnv();
          Path envFile =   projectDirectory.resolve(".env");
          Files.writeString(envFile,content);
//       Files.createDirectories(envFile);
        }

        if(jsJsonTypeDto.getInclude().isReadme()){
            Path envFile =   projectDirectory.resolve("README.md");
            ReadmeRequestDto readmeRequestDto = new ReadmeRequestDto(jsJsonTypeDto.getProjectName(), jsJsonTypeDto.getFramework());
            String content = readmeGenerator.createReadme(readmeRequestDto);
            Files.writeString(envFile,content);
        }

        if(jsJsonTypeDto.getInclude().isGitignore()){
            Path envFile =   projectDirectory.resolve(".gitignore");
            String content = gitignoreGeneratorService.gitIgnore();
            Files.writeString(envFile,content);
        }

     //generating Server.js file
      String serverText = serverJsGeneratorService.generateServerFile(projectDirectory,moduleType);
        Path serverfile = projectDirectory.resolve("server.js");

        Files.writeString(serverfile,serverText);

        //generating PackageJson.js file
        ObjectNode Package = jsonGenerator.getGenearator(jsJsonTypeDto);
        Path serverfile1 = projectDirectory.resolve("package.json");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writerWithDefaultPrettyPrinter()
                .writeValue(serverfile1,
                        Package
                );
//        Files.writeString(serverfile1,  Package.);
    }



}

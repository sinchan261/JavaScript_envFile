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
    @Autowired
    Docker_jsGeneratorService dockerJsGeneratorService;
    @Autowired
    GenerateZipFolder generateZipFolder;
    public Path generateProject(JsJsonTypeDto jsJsonTypeDto, String moduleSystem1) throws IOException{
        Path parentDirectory = Path.of(
                "C:\\Users\\DELL\\OneDrive\\Documents\\Maven\\EnvFile\\src\\main\\java\\com\\example\\FileGenerator\\EnvFile\\Example"
        );

        Path projectDirectory = Files.createTempDirectory(
                parentDirectory,
                  jsJsonTypeDto.getProjectName()
        );
        log.info("Generator file path is {}",projectDirectory);
        //call for building the project structure
       Path pathDirectory = createDirectories(projectDirectory,jsJsonTypeDto,moduleSystem1);
        Path ZipDirectory =generateZipFolder.getZipFile(pathDirectory);
        return ZipDirectory;

    }

    public Path createDirectories(Path projectDirectory,JsJsonTypeDto jsJsonTypeDto,String moduleType) throws IOException {
        if(jsJsonTypeDto.getInclude().isControllers()){
           Path controllerDirectory =  projectDirectory.resolve("Controllers");
           Files.createDirectories(controllerDirectory);
            Path controllerFile =
                    controllerDirectory.resolve("webController.js");
            Files.writeString(controllerFile, "// Controller code");
        }
        if(jsJsonTypeDto.getInclude().isServices()){
            Path Service = projectDirectory.resolve("Service");

            Files.createDirectories(Service);
            Path ServiceJs = Service.resolve("webService.js");
            Files.writeString(ServiceJs, "// Controller code");

        }
        if(jsJsonTypeDto.getInclude().isMiddlewares()){
            Path middlewareDirectory =
                    projectDirectory.resolve("middlewares");

            Files.createDirectories(middlewareDirectory);

            Path middlewareFile =
                    middlewareDirectory.resolve("webMiddleware.js");

            Files.writeString(
                    middlewareFile,
                    "// Middleware code"
            );
        }
        if(jsJsonTypeDto.getInclude().isModels()){
            Path modelDirectory =
                    projectDirectory.resolve("models");

            Files.createDirectories(modelDirectory);

            Path modelFile =
                    modelDirectory.resolve("webModel.js");

            Files.writeString(
                    modelFile,
                    "// Model code"
            );
        }
        if(jsJsonTypeDto.getInclude().isRoutes()){
            Path routeDirectory =
                    projectDirectory.resolve("routes");

            Files.createDirectories(routeDirectory);

            Path routeFile =
                    routeDirectory.resolve("webRouter.js");

            Files.writeString(
                    routeFile,
                    "// Route code"
            );
        }
        if(jsJsonTypeDto.getInclude().isConfig()){
            Path configDirectory =
                    projectDirectory.resolve("config");

            Files.createDirectories(configDirectory);

            Path configFile =
                    configDirectory.resolve("webConfig.js");

            Files.writeString(
                    configFile,
                    "// Configuration code"
            );
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
        if(jsJsonTypeDto.getInclude().isIncludeDocker()){
            Path dockerfile =   projectDirectory.resolve("Dockerfile");

            String content = dockerJsGeneratorService.getJsDockerContent();
            Files.writeString(dockerfile,content);
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

        return projectDirectory;

//        Files.writeString(serverfile1,  Package.);
    }



}

package com.example.FileGenerator.EnvFile.Services;

import com.example.FileGenerator.EnvFile.Dto.JavaScriptJson;
import com.example.FileGenerator.EnvFile.Dto.JsJsonTypeDto;
import com.example.FileGenerator.EnvFile.Dto.ProjectStructureDto;
import com.example.FileGenerator.EnvFile.Dto.RequestDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
//    private String projectName;
//    private String framework;
//    private String moduleSystem;
//    private String packageManager;
//    private List<String> dependencies;
//    private List<String> devDependencies;
//    private String database;
//    private String authentication;
//    private String includeTests;
//    private String includeDocker;
@Service
public class EngineServices {
    @Autowired
    ModelMapper modelMapper;
    public JavaScriptJson generatedJson(RequestDto requestDto) {
        String projectName1 = requestDto.getConfigur().getOrDefault("projectName","FirstProject"+ LocalDateTime.now()).toString();
        String framework1 = requestDto.getConfigur().get("framework").toString();
        String moduleSystem1 = requestDto.getConfigur().get("moduleSystem").toString();
        String packageManager1 = requestDto.getConfigur().get("packageManager").toString();
        String database1 = requestDto.getConfigur().get("database").toString();
        String authentication1 = requestDto.getConfigur().get("authentication").toString();
        String includeTests1 = requestDto.getConfigur().getOrDefault("includeTests",false).toString();
        String includeDocker1 = requestDto.getConfigur().getOrDefault("includeDocker",false).toString();
        List<String> dependencies1 = Arrays.stream(requestDto.getConfigur().get("dependencies").toString().split(",")).toList();
       List<String> devdependencies1 = Arrays.stream(requestDto.getConfigur().get("devDependencies").toString().split(",")).toList();

        ProjectStructureDto projectStructureDto =
                modelMapper.map(requestDto.getConfigur()
                        .get("include"), ProjectStructureDto.class);


        JsJsonTypeDto jsJsonTypeDto = JsJsonTypeDto.builder()
                .projectName(projectName1).authentication(authentication1)
                .framework(framework1).moduleSystem(moduleSystem1).packageManager(packageManager1)
                .database(database1).includeTests(includeTests1)
                .dependencies(dependencies1)
                .devDependencies(devdependencies1)
                .includeDocker(includeDocker1)
                .include(projectStructureDto)
                .build();
        JavaScriptJson javaScriptJson = JavaScriptJson.builder()
                .tag(requestDto.getTag())
                .configure(jsJsonTypeDto)
                .build();
        return javaScriptJson;
    }
}
//public class JsJsonTypeDto {
//
//    private String projectName;
//    private String framework;
//    private String moduleSystem;
//    private String packageManager;
//    private List<String> dependencies;
//    private List<String> devDependencies;
//    private String database;
//    private String authentication;
//    private String includeTests;
//    private String includeDocker;
//}
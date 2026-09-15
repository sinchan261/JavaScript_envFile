package com.example.FileGenerator.EnvFile.Dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JsJsonTypeDto {

    private String projectName;
    private String framework;
    private String moduleSystem;
    private String packageManager;
    private List<String> dependencies;
    private List<String> devDependencies;
    private String database;
    private String authentication;
    private String includeTests;
    private String includeDocker;
    private  ProjectStructureDto include;
}

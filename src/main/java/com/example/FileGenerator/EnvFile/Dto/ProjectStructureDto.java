package com.example.FileGenerator.EnvFile.Dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectStructureDto {
    private boolean controllers;
    private boolean services;
    private boolean middlewares;
    private boolean models;
    private boolean routes;
    private boolean config;
    private boolean env;
    private boolean gitignore;
    private boolean readme;
}

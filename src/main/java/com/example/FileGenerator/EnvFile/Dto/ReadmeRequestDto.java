package com.example.FileGenerator.EnvFile.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ReadmeRequestDto {

    private String ProjectName;
    private String Framework;
}

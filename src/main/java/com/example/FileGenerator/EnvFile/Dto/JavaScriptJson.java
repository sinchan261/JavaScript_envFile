package com.example.FileGenerator.EnvFile.Dto;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JavaScriptJson {
    private  String tag;
    private JsJsonTypeDto configure;
}

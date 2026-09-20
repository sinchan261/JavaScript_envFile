package com.example.FileGenerator.EnvFile.Services;

import com.example.FileGenerator.EnvFile.Dto.JsJsonTypeDto;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ObjectNode;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service

public class JsonGenerator {

    public ObjectNode getGenearator(JsJsonTypeDto jsJsonTypeDto){
        ObjectMapper mapper =  new ObjectMapper();
        ObjectNode packageJson = mapper.createObjectNode();

        packageJson.put("name",jsJsonTypeDto.getProjectName());
        packageJson.put("version","1.0.0");
        packageJson.put("private",true);

        if( jsJsonTypeDto.getModuleSystem().equals("ES_MODULE"))
                 packageJson.put("type", "module");


        ObjectNode scripts = packageJson.putObject("scripts");
        scripts.put("start","node server.js");
        if( jsJsonTypeDto.getDevDependencies().contains("nodemon**"))
            scripts.put("dev","nodemon server.js");

        Map<String,String> dependencies =
                jsJsonTypeDto.getDependencies().stream()
                        .map(e->e.split(":"))
                        .collect(Collectors.toMap(e->e[0],e->e[1]));
        Map<String,String> deveDpendencies =
                jsJsonTypeDto.getDevDependencies().stream()
                        .map(e->e.split(":"))
                        .collect(Collectors.toMap(e->e[0],e->e[1]));

        ObjectNode dependencies1 = packageJson.putObject("dependencies");
        dependencies.forEach(dependencies1::put);

        ObjectNode deveDpendencies1 = packageJson.putObject("devDpendencies");
        dependencies.forEach(deveDpendencies1::put);
 return packageJson;
    }
}

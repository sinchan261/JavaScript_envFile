package com.example.FileGenerator.EnvFile.Controller;

import com.example.FileGenerator.EnvFile.Dto.JavaScriptJson;
import com.example.FileGenerator.EnvFile.Dto.RequestDto;
import com.example.FileGenerator.EnvFile.Services.EngineServices;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/Producer_js_file")
public class RequestController {
    @Autowired
    EngineServices engineServices;
    @GetMapping("/get")
    public JavaScriptJson getData(@RequestBody RequestDto requestDto, HttpServletResponse httpServletResponse){

        httpServletResponse.setContentType("application/json");
        log.info("Hello data is reached{}",requestDto.getConfigur());
        return  engineServices.generatedJson(requestDto);
    }
}

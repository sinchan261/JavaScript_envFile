package com.example.FileGenerator.EnvFile.Controller;

import com.example.FileGenerator.EnvFile.Dto.JavaScriptJson;
import com.example.FileGenerator.EnvFile.Dto.Message;
import com.example.FileGenerator.EnvFile.Dto.RequestDto;
import com.example.FileGenerator.EnvFile.Services.EngineServices;
import com.example.FileGenerator.EnvFile.Services.ShallService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
@Slf4j
@RestController
@RequestMapping("/Producer_js_file")
public class RequestController {
    @Autowired
    EngineServices engineServices;
    @Autowired
    ShallService shallService;
    @GetMapping("/get")
    public Message getData(@RequestBody RequestDto requestDto, HttpServletResponse httpServletResponse) throws IOException {

        httpServletResponse.setContentType("application/json");
        log.info("Hello data is reached{}",requestDto.getConfigur());
        return  engineServices.generatedJson(requestDto);
    }
    @GetMapping("/get/sh_file")
    public ResponseEntity<String> getShFile(@RequestBody String  projectId, HttpServletResponse httpServletResponse) throws IOException {
        String scriptContent = shallService.generateService(projectId);
        httpServletResponse.setContentType("application/json");
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("text/x-shellscript"))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"install-" + projectId + ".sh\"")
                .body(scriptContent);
    }
}

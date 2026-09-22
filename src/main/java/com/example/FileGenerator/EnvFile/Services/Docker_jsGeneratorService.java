package com.example.FileGenerator.EnvFile.Services;

import org.springframework.stereotype.Service;

@Service
public class Docker_jsGeneratorService {

  public String getJsDockerContent(){
      String dockerContent = """
           #```Modification of Docker file is needed as Your Requirement```
           FROM node:22-alpine

           WORKDIR /app

           COPY package*.json ./

           RUN npm install

           COPY . .

           EXPOSE ```Your PortNo ```

           CMD ["npm", "start"]
            """;


      return dockerContent;
  }
}

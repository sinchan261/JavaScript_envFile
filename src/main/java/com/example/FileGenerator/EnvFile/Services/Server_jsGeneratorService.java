package com.example.FileGenerator.EnvFile.Services;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class Server_jsGeneratorService {

    public String generateServerFile(Path projectDirectory, String moduleType) throws IOException {
        String ServerText;
  if(moduleType.equals("ES_MODULE")) {
       ServerText = """
              import express from "express";
              import dotenv from "dotenv";
              
              
              dotenv.config();
              
              
              const app = express();
              
              app.use(express.json());
              
              const PORT=process.env.port ||3000;
              
              
              app.get("/",(req,res)=>{
              res.json({
              message:"server is running"
              });
              
              
              app.listen(PORT,()=>{
              console.log(`server is running on port ${PORT}`);
              });
              
              
              """;
  }else{

      ServerText = """
                   const express = require("express");
                    const dotenv = require("dotenv");
              
              
              dotenv.config();
              
              
              const app = express();
              
              app.use(express.json());
              
              const PORT=process.env.port ||3000;
              
              
              app.get("/",(req,res)=>{
              res.json({
              message:"server is running"
              });
              
              
              app.listen(PORT,()=>{
              console.log(`server is running on port ${PORT}`);
              });
              
              
              """;

  }

return ServerText;

    }
}

package com.fiec.alunofiec.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiec.alunofiec.business.models.Aluno;
import com.fiec.alunofiec.services.IAlunoService;
import net.coobird.thumbnailator.Thumbnailator;
import net.coobird.thumbnailator.Thumbnails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    IAlunoService alunoService;

    @GetMapping
    public List<Aluno> getAlunos(){
        return alunoService.getAlunos();
    }

    @PostMapping
    public void saveAluno(@RequestBody Aluno aluno){
        alunoService.saveAluno(aluno);
    }

    @GetMapping("/{alunoId}")
    public Aluno pegaAluno(@PathVariable("alunoId") String id){

        return alunoService.pegaAluno(id);
    }

    @PutMapping("/{alunoId}")
    public void atualizaAluno(@PathVariable("alunoId") String id, @RequestBody Aluno aluno){
        alunoService.atualizaAluno(aluno, id);
    }

    @DeleteMapping("/{alunoId}")
    public void deletaAluno(@PathVariable("alunoId") String id){
        alunoService.deletaAluno(id);
    }

    @PostMapping(value = "/withPhoto", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public void saveAluno(@RequestPart("aluno") String aluno, @RequestPart("photo") MultipartFile file) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        Aluno newAluno = objectMapper.readValue(aluno, Aluno.class);


        String profileImage = UUID.randomUUID() + "_" + Long.toHexString(new Date().getTime());
        newAluno.setProfileImage(profileImage + ".jpg");
        alunoService.saveAluno(newAluno);
        //Files.copy(file.getInputStream(), Paths.get("uploads").resolve(file.getOriginalFilename()) );
        Path filename = Paths.get("uploads")
            .resolve(profileImage);
        Path thumb = Paths.get("uploads")
            .resolve("thumb_"+profileImage);
        Thumbnails.of(file.getInputStream())
            .size(500,500)
            .outputFormat("jpg")
            .toFile(new File(filename.toString()));
        Thumbnails.of(file.getInputStream())
            .size(100,100)
            .outputFormat("jpg")
            .toFile(new File(thumb.toString()));


    }


}

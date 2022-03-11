package com.fiec.alunofiec.services;


import com.fiec.alunofiec.business.models.Aluno;

import java.util.List;

public interface IAlunoService {

    List<Aluno> getAlunos();

    String saveAluno(Aluno aluno);

    void atualizaAluno(Aluno aluno, String id);

    Aluno pegaAluno(String id);

    void deletaAluno(String id);
}

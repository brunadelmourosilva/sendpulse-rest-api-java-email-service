package com.sendpulse.restapi;

public class PersonalData {

    private String email;
    private String curso;
    private String cidade;

    public PersonalData() {
    }

    public PersonalData(String email, String curso, String cidade) {
        this.email = email;
        this.curso = curso;
        this.cidade = cidade;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
}

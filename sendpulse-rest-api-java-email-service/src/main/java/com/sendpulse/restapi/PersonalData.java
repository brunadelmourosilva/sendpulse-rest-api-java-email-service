package com.sendpulse.restapi;

public class PersonalData {

    private String email;
    private String cidade;
    private String curso;

    public PersonalData() {
    }

    public PersonalData(String email, String cidade, String curso) {
        this.email = email;
        this.cidade = cidade;
        this.curso = curso;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }
}

package org.example.projet_java_fx.models;

import java.time.LocalDate;

public class Student {
    private int id;
    private String nom;
    private String prenom;
    private String cin;
    private String email;
    private String telephone;
    private LocalDate dateNaissance;
    private String niveau;
    private String filiere;
    private String groupe;

    public Student(int id, String nom, String prenom, String cin, String email, String telephone, LocalDate dateNaissance, String niveau, String filiere, String groupe) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.cin = cin;
        this.email = email;
        this.telephone = telephone;
        this.dateNaissance = dateNaissance;
        this.niveau = niveau;
        this.filiere = filiere;
        this.groupe = groupe;
    }

    // Default constructor for adding
    public Student() {}

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public String getCin() { return cin; }
    public void setCin(String cin) { this.cin = cin; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    public LocalDate getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(LocalDate dateNaissance) { this.dateNaissance = dateNaissance; }
    public String getNiveau() { return niveau; }
    public void setNiveau(String niveau) { this.niveau = niveau; }
    public String getFiliere() { return filiere; }
    public void setFiliere(String filiere) { this.filiere = filiere; }
    public String getGroupe() { return groupe; }
    public void setGroupe(String groupe) { this.groupe = groupe; }
}

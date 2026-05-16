package org.example.projet_java_fx.models;

public class Module {
    private String code;
    private String nom;
    private double coefficient;
    private String enseignant;
    private String idAffectation;
    private String documentPath;

    public Module(String code, String nom, double coefficient, String enseignant, String idAffectation, String documentPath) {
        this.code = code;
        this.nom = nom;
        this.coefficient = coefficient;
        this.enseignant = enseignant;
        this.idAffectation = idAffectation;
        this.documentPath = documentPath;
    }

    public Module(String code, String nom, double coefficient, String enseignant) {
        this.code = code;
        this.nom = nom;
        this.coefficient = coefficient;
        this.enseignant = enseignant;
    }

    public Module() {}

    // Getters and Setters
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public double getCoefficient() { return coefficient; }
    public void setCoefficient(double coefficient) { this.coefficient = coefficient; }
    public String getEnseignant() { return enseignant; }
    public void setEnseignant(String enseignant) { this.enseignant = enseignant; }
    public String getIdAffectation() { return idAffectation; }
    public void setIdAffectation(String idAffectation) { this.idAffectation = idAffectation; }
    public String getDocumentPath() { return documentPath; }
    public void setDocumentPath(String documentPath) { this.documentPath = documentPath; }

    @Override
    public String toString() {
        return nom + " (" + code + ")";
    }
}

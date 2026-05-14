package org.example.projet_java_fx.models;

public class Grade {
    private int studentId;
    private String moduleCode;
    private double noteCC;
    private double noteExamen;

    public Grade(int studentId, String moduleCode, double noteCC, double noteExamen) {
        this.studentId = studentId;
        this.moduleCode = moduleCode;
        this.noteCC = noteCC;
        this.noteExamen = noteExamen;
    }

    public Grade() {}

    // Getters and Setters
    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }
    public String getModuleCode() { return moduleCode; }
    public void setModuleCode(String moduleCode) { this.moduleCode = moduleCode; }
    public double getNoteCC() { return noteCC; }
    public void setNoteCC(double noteCC) { this.noteCC = noteCC; }
    public double getNoteExamen() { return noteExamen; }
    public void setNoteExamen(double noteExamen) { this.noteExamen = noteExamen; }

    public double getMoyenne() {
        return (noteCC * 0.4) + (noteExamen * 0.6);
    }

    public String getMention() {
        double moy = getMoyenne();
        if (moy < 10) return "Ajourné";
        if (moy < 12) return "Passable";
        if (moy < 14) return "Assez bien";
        if (moy < 16) return "Bien";
        return "Très bien";
    }
}

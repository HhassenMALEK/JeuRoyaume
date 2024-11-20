package fr.diginamic.model;

public class Batiment {
    private String type;
    private int niveau;
    private String fonction;

    public Batiment(String type, int niveau, String fonction) {
        this.type = type;
        this.niveau = niveau;
        this.fonction = fonction;
    }
}

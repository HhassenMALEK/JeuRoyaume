package fr.diginamic.service;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class GestionBatiments {

    private MongoCollection<Document> batiments;
    private GestionRessources gestionRessources;

    public GestionBatiments(MongoDatabase database, GestionRessources gestionRessources){
        this.batiments = database.getCollection("batiments");
        this.gestionRessources = gestionRessources;
    }

    //Methode pour afficher les batiment
    public void afficherBatiment(){
        for(Document batiment : batiments.find()){
            System.out.println(batiment.toJson());
        }
    }

    //methode pour supprimer un batiment
    public void supprimerBatiment(String type){
        batiments.deleteOne(new Document("type", type));
        System.out.println("Batiment supprimé : " + type);
    }

    //methode pour ajouter un batiment sans consition
    public void ajouterBatiment(String type, int niveau, String fonction){
        Document batiment = new Document("type", type).append("niveau", niveau).append("fonction", fonction);
        batiments.insertOne(batiment);
        System.out.println("Batiment ajouté : " + type);
    }

    //Methode pour constrruire un batiment
    public void construireBatiment(String type, int coutBois, int coutPierre, String fonction) {
        if(gestionRessources.verifierResources("Bois", coutBois) && gestionRessources.verifierResources("Pierre", coutPierre)){
            //deduire les ressources pour la construction
            gestionRessources.mettreAJourRessource("Bois", -coutBois);
            gestionRessources.mettreAJourRessource("Pierre", -coutPierre);

            //Ajouter le batiment
            ajouterBatiment(type, 1, fonction);
            System.out.println("Batiment construit : " + type);
        } else{
            System.out.println("Ressources insuffisantes pour construire le batiment.");
        }
    }



    //Methode pour mettre à jour le niveau d'un batiment
    public void mettreAJourBatiment(String type, int nouveauNiveau){
        //Récupération de  batiment à partir de son type
        Document batiment = batiments.find(new Document("type", type)).first();

        if(batiment != null){
            int niveauActuel = batiment.getInteger("niveau", 1);
            int niveauSuivant = niveauActuel + 1;

            //defini le cout de la mise à jour
            int coutBois = niveauSuivant * 200;
            int coutPierre = niveauSuivant * 100;

        if(gestionRessources.verifierResources("Bois", -coutBois) && gestionRessources.verifierResources("Pierre", -coutPierre)){

            //deduire les ressources pour la mise à jour
            gestionRessources.mettreAJourRessource("Bois", -coutBois);
            gestionRessources.mettreAJourRessource("Pierre", -coutPierre);

            //Mettre à jour le niveau du batiment
            mettreAJourBatiment(type, niveauSuivant);
            System.out.println("Batiment amélioré : " + type + " au niveau " + niveauSuivant);
        } else {
            System.out.println("Ressources insuffisantes pour améliorer le batiment : " + type);
        }
        } else {
            System.out.println("Batiment introuvable : " + type);
        }
    }
}


package fr.diginamic.service;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class GestionRessources {

    private MongoCollection<Document> ressources;

    public GestionRessources(MongoDatabase database) {
        this.ressources = database.getCollection("ressources");
    }

    public void ajouterRessource(String type, int quantite){

        //vérifier si une ressource de même type existe déjà
        Document ressource = ressources.find(new Document("type", type)).first();

        //si la ressource existe mettre à jour la quantité
        if(ressource != null){
            int nouvelQte = ressource.getInteger("quantite") + quantite;
        } else {
            // sinon ajouter une nouvelle ressource
            ressources.insertOne(new Document("type", type).append("quantite", quantite));
            System.out.println("Ressource ajoutée avec succès"+ quantite + " " + type);
        }
    }

    public void afficherRessource(){
        for (Document ressource : ressources.find()) {
            System.out.println(ressource.toJson());
        }
    }

    public void supprimerRessources(String type){
        ressources.deleteOne(new Document("type", type));
        System.out.println("Ressource " + type + " supprimée avec succès");
    }

    public void mettreAJourRessource(String type , int quantite){
        ressources.updateOne(new Document("type", type), new Document("$set", new Document("quantite", quantite)));
        System.out.println("Ressource " + type + " mise à jour avec succès");
    }

    public boolean verfierResources(String type, int quantiteNecessaire){

        Document ressource = ressources.find(new Document("type,", type)).first();
        if(ressource != null) {
            int quantiteDisponible = ressource.getInteger("quntite");

                return quantiteDisponible >= quantiteNecessaire;
            }
    return false;
    }
}

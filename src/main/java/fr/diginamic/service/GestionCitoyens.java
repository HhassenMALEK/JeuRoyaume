package fr.diginamic.service;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;
import org.bson.Document;

public class GestionCitoyens {

    private MongoCollection<Document> citoyens;

    public GestionCitoyens(MongoDatabase database) {
        this.citoyens = database.getCollection("citoyens");
    }

    // Méthode pour ajouter un citoyen
    public void ajouterCitoyen(String nom, int quantite, String role) {
        Document citoyen = citoyens.find(new Document("role",role)).first();
        if (citoyen!= null) {
            mettreAJourCitoyen(role, citoyen.getInteger("quantite", 1)+quantite);
        } else {
            Document nouveauCitoyen = new Document("nom", nom).append("role", role).append("quantite", quantite);
            citoyens.insertOne(nouveauCitoyen);
        }
    }


    // Méthode pour mettre à jour la quantité d'un citoyen (ajout ou suppression)
    public void mettreAJourCitoyen(String role, int changementQuantite){
        citoyens.updateOne(new Document("role", role), new Document("$set", new Document("quantite", changementQuantite)));
        System.out.println("Citoyen " + role + " mis à jour avec succès");
    }

    // Méthode pour mettre à jour le rôle d'un citoyen
    public void mettreAJourCitoyen(String nom, String ancienRole, String nouveauRole){
        Document citoyenNouveauRole = citoyens.find(new Document("role", nouveauRole)).first();

        if(citoyenNouveauRole != null){
            // Si oui, augmenter la quantité du nouveau rôle
            int qteNouveauRole = citoyenNouveauRole.getInteger("quantite")+1;
            citoyens.updateOne(new Document("role", nouveauRole), new Document("$set", new Document("quantite", qteNouveauRole)));
        } else {
            // Sinon, créer un nouveau document pour le nouveau rôle
            citoyens.insertOne(new Document("nom", nom).append("quantite", 1).append("role", nouveauRole));
        }

        // Réduire la quantité de l'ancien rôle
        Document citoyenAncienRole = citoyens.find(new Document("role", ancienRole)).first();
        if(citoyenAncienRole != null){
            int qteAncienRole = citoyenAncienRole.getInteger("quantite")-1;
            citoyens.updateOne(new Document("role", ancienRole), new Document("$set", new Document("quantite", qteAncienRole)));
        }

        // mettreAJourCitoyen("soldat", "defenseur", "attaquant");
        // -> attaquant ?
        // si oui:
        //     - recupere mon nb attaquant
        //     attaquant +1
        // sinon
        //     - attaquant 1
        // defenseur - 1
    }

    // Méthode pour afficher tous les citoyens
    public void afficherCitoyens() {
        for (Document citoyen : citoyens.find()) {
            System.out.println(citoyen.toJson());
        }
    }

    // Méthode pour vérifier les citoyens

    public boolean verifierCitoyens(String role, int quatiteNecessaire){
        //Récuper le document le plus récent
        Document citoyen = citoyens.find(new Document("type", role)).sort(Sorts.descending("_id")).first();
        int quantiteDisponible = citoyen.getInteger("quantite");
        if(citoyens !=null){
            System.out.println("Quantité disponible de " + role + ":" + quantiteDisponible);
            System.out.println("Quantité necessaire de " + role + ":" + quatiteNecessaire);
            return quantiteDisponible >= quatiteNecessaire;
        }
        return false;
    }


}

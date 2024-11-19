package fr.diginamic;

import com.mongodb.client.MongoDatabase;
import fr.diginamic.connexion.ConnetionMongoDB;
import fr.diginamic.service.GestionRessources;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {


        //creéation d'une instance de connexionMongoDB
        ConnetionMongoDB connexion = new ConnetionMongoDB();
        //connexion à la base de données
        connexion.connectToDatabase();
        //récupération de la base de données
        MongoDatabase database = connexion.getDatabase();


        //Inintialisation des gestionnaires
        GestionRessources gestionRessources = new GestionRessources(database);


        //Ajout des ressources initiales
        gestionRessources.ajouterRessource("Pierre", 100);
        gestionRessources.ajouterRessource("Bois", 150);

        //Affichage des ressources
        gestionRessources.afficherRessource();





      connexion.closeConnection();
    }

}
package fr.diginamic;

import com.mongodb.client.MongoDatabase;
import fr.diginamic.connexion.ConnetionMongoDB;
import fr.diginamic.service.GestionBatiments;
import fr.diginamic.service.GestionCitoyens;
import fr.diginamic.service.GestionMissions;
import fr.diginamic.service.GestionRessources;

import java.util.Scanner;


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


        // Initialisation des collections et gestionnaires

        //Inintialisation des gestionnaires
        GestionRessources gestionRessources = new GestionRessources(database);
        GestionCitoyens gestionCitoyens = new GestionCitoyens(database);
        GestionBatiments gestionBatiments = new GestionBatiments(database, gestionRessources);
        GestionMissions gestionMissions = new GestionMissions(gestionRessources, gestionCitoyens, database);


        //Ajout des ressources initiales
        //gestionRessources.ajouterRessource("Pierre", 100);
        //gestionRessources.ajouterRessource("Bois", 150);
       // gestionBatiments.ajouterBatiment("Chateau", 1, "Résidence principale");

        gestionMissions.preparerMission("Mission Terre de Montaigu", 10, 50, 50);
        gestionMissions.envoyerEnMission("Mission Terre de Montaigu", 10);
        gestionMissions.calculerGainMission("Mission Terre de Montaigu", true, 50);


       //Ajout des citoyens initiaux
       // gestionCitoyens.ajouterCitoyen("Soldats",10,"attaquants");
        //gestionCitoyens.ajouterCitoyen("Soldats",5, "defenseurs");



        //Mettre à jour les gestionnaires
        //gestionCitoyens.mettreAJourCitoyen("attaquants", 5);
        //gestionCitoyens.mettreAJourCitoyen("soldat", "defenseurs", "attaquants");


        //Affichage des ressources
        //gestionRessources.afficherRessource();
       // gestionCitoyens.afficherCitoyens();
       // gestionBatiments.afficherBatiment();


      connexion.closeConnection();
    }

}
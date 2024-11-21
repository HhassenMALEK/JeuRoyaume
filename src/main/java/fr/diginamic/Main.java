package fr.diginamic;

import com.mongodb.client.MongoDatabase;
import fr.diginamic.connexion.ConnetionMongoDB;
import fr.diginamic.service.GestionBatiments;
import fr.diginamic.service.GestionCitoyens;
import fr.diginamic.service.GestionMissions;
import fr.diginamic.service.GestionRessources;

import java.util.Scanner;



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

        // Scanner pour les entrées utilisateur
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        // Boucle du menu principal
        while (running) {
            System.out.println("\n=== Menu Principal ===");
            System.out.println("1. Ajouter des ressources");
            System.out.println("2. Afficher les ressources");
            System.out.println("3. Ajouter des citoyens");
            System.out.println("4. Afficher les citoyens");
            System.out.println("5. Préparer une mission");
            System.out.println("6. Envoyer une mission");
            System.out.println("7. Calculer les gains de mission");
            System.out.println("8. Quitter");
            System.out.print("Choisissez une option : ");
            int choix = scanner.nextInt();

            switch (choix) {
                case 1:
                    System.out.print("Type de ressource : ");
                    String typeRessource = scanner.next();
                    System.out.print("Quantité : ");
                    int quantiteRessource = scanner.nextInt();
                    gestionRessources.ajouterRessource(typeRessource, quantiteRessource);
                    break;

                case 2:
                    gestionRessources.afficherRessources();
                    break;

                case 3:
                    System.out.print("Nom du citoyen : ");
                    String nomCitoyen = scanner.next();
                    System.out.print("Quantité : ");
                    int quantiteCitoyen = scanner.nextInt();
                    System.out.print("Rôle : ");
                    String roleCitoyen = scanner.next();
                    gestionCitoyens.ajouterCitoyen(nomCitoyen, quantiteCitoyen, roleCitoyen);
                    break;

                case 4:
                    gestionCitoyens.afficherCitoyens();
                    break;


                case 5:
                    System.out.print("Nom de la mission : ");
                    String nomMission = scanner.next();
                    System.out.print("Nombre de soldats nécessaires : ");
                    int soldatsNecessaires = scanner.nextInt();
                    System.out.print("Bois nécessaire : ");
                    int boisNecessaire = scanner.nextInt();
                    System.out.print("Nourriture nécessaire : ");
                    int nourritureNecessaire = scanner.nextInt();
                    boolean missionPrete = gestionMissions.preparerMission(nomMission, soldatsNecessaires, boisNecessaire, nourritureNecessaire);
                    if (missionPrete) {
                        System.out.println("Mission prête à être envoyée.");
                    } else {
                        System.out.println("Préparation de la mission échouée.");
                    }
                    break;

                case 6:
                    System.out.print("Nom de la mission à envoyer : ");
                    String missionEnvoyee = scanner.next();
                    System.out.print("Nombre de soldats envoyés : ");
                    int soldatsEnvoyes = scanner.nextInt();
                    gestionMissions.envoyerEnMission(missionEnvoyee, soldatsEnvoyes);
                    break;

                case 7:
                    System.out.print("Nom de la mission pour calculer les gains : ");
                    String missionPourGain = scanner.next();
                    System.out.print("Mission réussie ? (true/false) : ");
                    boolean missionReussie = scanner.nextBoolean();
                    System.out.print("Nombre de soldats envoyés : ");
                    int soldatsPourGain = scanner.nextInt();
                    gestionMissions.calculerGainMission(missionPourGain, missionReussie, soldatsPourGain);
                    break;

                case 8:
                    running = false;
                    System.out.println("Au revoir !");
                    break;

                default:
                    System.out.println("Option invalide. Veuillez réessayer.");
            }
        }

        // Fermeture du scanner et de la connexion
        scanner.close();

        // Fermeture de la connexion à la base de données
      connexion.closeConnection();
    }
}
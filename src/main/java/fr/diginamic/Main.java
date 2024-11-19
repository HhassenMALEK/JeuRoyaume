package fr.diginamic;

import com.mongodb.client.MongoDatabase;
import fr.diginamic.connexion.ConnexionMongoDB;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {


        //creéation d'une instance de connexionMongoDB
        ConnexionMongoDB connexion = new ConnexionMongoDB();
        //connexion à la base de données
        connexion.connectToDatabase();
        //récupération de la base de données
        MongoDatabase database = connexion.getDatabase();





      connexion.closeConnection();
    }

}
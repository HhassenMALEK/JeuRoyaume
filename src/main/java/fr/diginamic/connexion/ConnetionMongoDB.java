package fr.diginamic.connexion;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class ConnetionMongoDB {
    private MongoClient mongoClient;
    private MongoDatabase database;

    public void connectToDatabase(){
        try {
            mongoClient = MongoClients.create("mongodb://localhost:27017");
            database = mongoClient.getDatabase("jeuRoyaume");
            System.out.println("Connexion à la base de données "+ database.getName() + " réussie " );
        }
        catch(Exception e){
            System.out.println("Erreur lors de la connexion à la base de données " );
            e.printStackTrace();
        }
    }

    /**
     * Getter
     *
     * @return database
     */
    public MongoDatabase getDatabase() {
        return database;
    }

    public void closeConnection(){
        mongoClient.close();
        System.out.println("Connexion à la base de données " + database.getName() +" fermée");
    }
}

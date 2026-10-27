package tn.esprit.autoloc.exception;

/**
 * Erreur metier : la ressource demandee n'existe pas.
 * Porte sa propre signification HTTP (404) au lieu d'un RuntimeException generique.
 */
public class RessourceIntrouvableException extends RuntimeException {

    public RessourceIntrouvableException(String message) {
        super(message);
    }
}

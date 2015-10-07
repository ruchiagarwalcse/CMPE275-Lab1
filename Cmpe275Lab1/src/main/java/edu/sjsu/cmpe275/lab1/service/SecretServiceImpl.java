package edu.sjsu.cmpe275.lab1.service;

import edu.sjsu.cmpe275.lab1.model.Secret;

import java.util.HashMap;
import java.util.UUID;

/**
 * SecretServiceImpl
 * Created on September 29th, 2015
 * @author Ruchi Agarwal
 */
public class SecretServiceImpl implements SecretService {

    /** Map of secretId with secret object. */
    private static HashMap<UUID, Secret> secrets = new HashMap<UUID, Secret>();

    /**
     * Store a secret in the service.
     * @param userId the ID of the current user
     * @param secret the secret to be stored.
     * @return a new UUID for the given secret
     */
    public UUID storeSecret(String userId, Secret secret) {
        UUID secretId = UUID.randomUUID();
        secret.setOwner(userId);
        secret.getSecretIds().add(secretId);
        secrets.put(secretId, secret);
        return secretId ;
    }

    /**
     * Read a secret by ID
     * @param userId the ID of the current user
     * @param secretId the ID of the secret being requested
     * @return the requested secret
     */
    public Secret readSecret(String userId, UUID secretId) {
        Secret secret = secrets.get(secretId);
        return  secret;
    }

    /**
     * Share a secret with another user.
     * @param userId the ID of the current user
     * @param secretId the ID of the secret being shared
     * @param targetUserId the ID of the user to share the secret with
     */
    public void shareSecret(String userId, UUID secretId, String targetUserId) {
        Secret secret =  secrets.get(secretId);
        if (! secret.getOwner().equals(targetUserId)) {
            secret.getUsers().add(targetUserId);
        }
    }

    /**
     * Unshare the current user's own secret with another user.
     * @param userId the ID of the current user
     * @param secretId the ID of the secret being unshared
     * @param targetUserId the ID of the user to unshare the secret with
     */
    public void unshareSecret(String userId, UUID secretId, String targetUserId) {
        Secret secret = secrets.get(secretId);
        if (secret != null && secret.getOwner().equals(userId)) {
            secret.getUsers().remove(targetUserId);
        }
    }

    /**
     * Get map of secretId with secret object.
     * @return map of secretId with secret object.
     */
    public static HashMap<UUID, Secret>  getSecrets() {
        return secrets;
    }

}

package edu.sjsu.cmpe275.lab1.model;

import java.util.HashSet;
import java.util.UUID;
import java.util.ArrayList;

/**
 * Secret
 * Created on September 29th, 2015
 * @author Ruchi Agarwal
 */
public class Secret {

    /** User who create & owns the secret. */
    private String owner;

    /** Users with whom this secret is shared. */
    private HashSet<String> users;

    /** All the Ids associated with this secret. */
    private ArrayList<UUID> secretIds;

    /** Constructor. */
    public Secret() {
        users = new HashSet<String>();
        secretIds = new ArrayList<UUID>();
    }

    /**
     * Get owner of the secret.
     * @return userId of owner.
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Set owner of the secret.
     * @param owner the ID of the user who is creating the secret.
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * Get list of users with whom this secret is shared.
     * @return list of userIds.
     */
    public HashSet<String> getUsers() {
        return users;
    }

    /**
     * Set list of users with whom this secret is shared.
     * @param users list of userIds.
     */
    public void setUsers(HashSet<String> users) {
        this.users = users;
    }

    /**
     * Get list of secretIds associated with this secret.
     * @return list of secretIds.
     */
    public ArrayList<UUID> getSecretIds() {
        return secretIds;
    }

    /**
     * Set list of secretIds associated with this secret.
     * @param secretIds list of secretIds.
     */
    public void setSecretIds(ArrayList<UUID> secretIds) {
        this.secretIds = secretIds;
    }
}

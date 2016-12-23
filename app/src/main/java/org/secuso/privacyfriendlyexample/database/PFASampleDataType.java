package org.secuso.privacyfriendlyexample.database;

/**
 *
 * @author Karola Marky
 * @version 20161223
 *
 * This class holds the "data type" that will be stored in the database
 * Each column of the database will be a private variable in this class.
 * For each data type one class is required.
 * In our example we only use one data type, which is sampleData
 *
 */

public class PFASampleDataType {

    private int ID;
    private String DOMAIN;

    private String USERNAME;
    private int LENGTH;

    public PFASampleDataType() {    }


    /**
     * Always use this constructor to generate data with values.
     * @param ID
     * @param DOMAIN
     * @param USERNAME
     * @param LENGTH
     */
    public PFASampleDataType(int ID, String DOMAIN, String USERNAME, int LENGTH) {

        this.ID=ID;
        this.DOMAIN=DOMAIN;
        this.USERNAME=USERNAME;
        this.LENGTH=LENGTH;
    }

    /**
     * All variables need getters and setters. because the variables are private.
     */

    public int getLENGTH() {
        return LENGTH;
    }

    public void setLENGTH(int LENGTH) {
        this.LENGTH = LENGTH;
    }

    public String getDOMAIN() {
        return DOMAIN;
    }

    public void setDOMAIN(String DOMAIN) {
        this.DOMAIN = DOMAIN;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

}
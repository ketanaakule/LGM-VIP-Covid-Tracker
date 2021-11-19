package com.example.covidtracker;

public class Model {

    private String state;
    private String confirmed;
    private String active;
    private String death;
    private String recovered;
    public Model(String state, String confirmed, String active,String recovered,
                          String death) {
        this.state = state;
        this.confirmed = confirmed;
        this.active = active;
        this.recovered = recovered;
        this.death = death;
    }

    public String getState() {
        return state;
    }

    public String getConfirmed() {
        return confirmed;
    }

    public String getActive() {
        return active;
    }

    public String getDeath() {
        return death;
    }

    public String getRecovered() {
        return recovered;
    }
}
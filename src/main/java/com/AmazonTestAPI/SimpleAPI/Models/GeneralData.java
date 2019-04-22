package com.AmazonTestAPI.SimpleAPI.Models;

public class GeneralData {
    private long numberOfBikes, numberOfOperativeStations;

    public GeneralData() {
    }

    public GeneralData(long numberOfBikes, long numberOfOperativeStations) {
        this.numberOfBikes = numberOfBikes;
        this.numberOfOperativeStations = numberOfOperativeStations;
    }

    public long getNumberOfBikes() {
        return numberOfBikes;
    }

    public void setNumberOfBikes(long numberOfBikes) {
        this.numberOfBikes = numberOfBikes;
    }

    public long getNumberOfOperativeStations() {
        return numberOfOperativeStations;
    }

    public void setNumberOfOperativeStations(long numberOfOperativeStations) {
        this.numberOfOperativeStations = numberOfOperativeStations;
    }
}

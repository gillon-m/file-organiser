package com.gillonmanalastas.fileorganiser;

public class ProgressBar {

    private static final int DEFAULT_BAR_LENGTH = 10;
    private int loadBarLength;
    private int totalToLoad;
    private int loaded;
    private String loadBar;


    public ProgressBar(int totalToLoad) {
        this(totalToLoad, DEFAULT_BAR_LENGTH);
    }

    public ProgressBar(int totalToLoad, int loadBarLength) {
        this.totalToLoad = totalToLoad;
        this.loadBarLength = loadBarLength;
        this.loaded = 0;
    }

    public String increment() {
        return increment("");
    }

    public String increment(String item) {
        loaded++;
        double percentageLoaded = (double) loaded / (double) totalToLoad;
        loadBar = createLoadBar(loadBarLength, percentageLoaded);
        loadBar += " " + loaded + " out of " + totalToLoad + " | ";
        if (loaded >= totalToLoad) {
            loadBar += "DONE";
        } else {
            loadBar += item;
        }
        return loadBar;
    }

    private String createLoadBar(int length, double percentageLoaded) {
        String bar = "|";
        double currentlyLoaded = length * percentageLoaded;
        for (int i = 0; i < currentlyLoaded; i++) {
            bar += "=";
        }
        for (int i = 0; i < length - currentlyLoaded; i++) {
            bar += " ";
        }
        bar += "|";
        return bar;
    }

    @Override
    public String toString() {
        return loadBar;
    }
}

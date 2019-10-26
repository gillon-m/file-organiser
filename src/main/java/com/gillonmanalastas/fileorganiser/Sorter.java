package com.gillonmanalastas.fileorganiser;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Sorter {

    private static final int LOAD_LENGTH = 20;
    private Map<String, Integer> duplicatesCount = new HashMap<String, Integer>();

    @Getter
    @Setter
    private Collection<File> files;
    @Getter
    @Setter
    private String sourceDirectory;
    @Getter
    @Setter
    private String outputDirectory;

    public Sorter(Collection<File> filesToSort, String sourceDirectory, String outputDirectory) {
        this.files = filesToSort;
        this.sourceDirectory = sourceDirectory;
        this.outputDirectory = outputDirectory;
    }

    public void sort() throws IOException {
        ProgressBar progressBar = new ProgressBar(files.size(), LOAD_LENGTH);
        for (File file : files) {
            System.out.print(progressBar.increment(file.getName()) + "\r");
            if (!file.exists()) {
                FileUtils.copyFileToDirectory(file, new File(outputDirectory));
            }else{
                saveDuplicate(file);
            }
        }
    }

    private void saveDuplicate(File file) throws IOException {
        String filename = file.getName();
        Integer count = duplicatesCount.get(filename);
        if (count == null) {
            duplicatesCount.put(filename, 1);
        } else {
            duplicatesCount.put(filename, count + 1);
        }
        FileUtils.copyFileToDirectory(file, new File(outputDirectory +"-copy-"+count));
    }
}

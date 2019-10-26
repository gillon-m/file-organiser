package com.gillonmanalastas.fileorganiser;

import lombok.Data;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

@Data
public class Sorter {

    private static final int LOAD_LENGTH = 20;

    private Collection<File> files;
    private String sourceDirectory;
    private String outputDIrectory;

    public Sorter(Collection<File> filesToSort, String sourceDirectory, String outputDIrectory){
        this.files = filesToSort;
        this.sourceDirectory = sourceDirectory;
        this.outputDIrectory = outputDIrectory;
    }

    public void sort() throws IOException {
        ProgressBar progressBar = new ProgressBar(files.size(), LOAD_LENGTH);
        for(File file : files) {
            System.out.print(progressBar.increment(file.getName())+"\r");
            FileUtils.copyFileToDirectory(file, new File(outputDIrectory));
        }
    }
}

package com.gillonmanalastas.fileorganiser;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

public class FileOrganiser{
    public static void main(String[] args) throws IOException {
//        new Importer().importFiles(System.getProperty("user.dir"));
//        String directoryToSort = System.getProperty("user.dir")+ File.separator+"to_sort";
//        String sortedDirectory = System.getProperty("user.dir")+ File.separator+"sorted";
        String directoryToSort = "C:\\Users\\Gillon\\IdeaProjects\\to_sort";
        String sortedDirectory = "C:\\Users\\Gillon\\IdeaProjects\\sorted";
        Collection<File> filesToSort = FileUtils.listFiles(new File(directoryToSort), null, true);
        ProgressBar progressBar = new ProgressBar(filesToSort.size(), 20);
        for(File file : filesToSort) {
            System.out.print(progressBar.increment(file.getName())+"\r");
            FileUtils.copyFileToDirectory(file, new File(sortedDirectory));
        }
    }
}

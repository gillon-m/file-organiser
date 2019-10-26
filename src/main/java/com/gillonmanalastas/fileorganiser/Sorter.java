package com.gillonmanalastas.fileorganiser;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.DateFormatSymbols;
import java.util.*;

public class Sorter {

    private static final int LOAD_LENGTH = 20;
    private Map<String, Integer> duplicatesCount = new HashMap<String, Integer>();

    @Getter
    @Setter
    private Collection<File> files;
    @Getter
    @Setter
    private String outputDirectory;

    public Sorter(Collection<File> filesToSort, String outputDirectory) {
        this.files = filesToSort;
        this.outputDirectory = outputDirectory + File.separator;
    }

    public void sort() throws IOException, ImageProcessingException {
        ProgressBar progressBar = new ProgressBar(files.size(), LOAD_LENGTH);
        for (File file : files) {
            System.out.print(progressBar.increment(file.getName()) + "\r");
            String filename = file.getName();
            String dateDir = dateDirectory(file);
            if (doesFileExistInOutputDirectory(file)) {
                filename = getDuplicateFileName(file);
            }
            FileUtils.copyFile(file, new File(outputDirectory + dateDir + filename));
        }
    }

    private String getDuplicateFileName(File file) throws IOException {
        String filename = file.getName();
        Integer count = duplicatesCount.get(filename);
        if (count == null) {
            duplicatesCount.put(filename, 1);
        }
        count = duplicatesCount.get(filename);
        while (doesFileExistInOutputDirectory(new File(outputDirectory + "copy" + count + "-" + file.getName()))) {
            count++;
        }
        duplicatesCount.put(filename, count);
        return "copy" + count + "-" + file.getName();
    }

    private String dateDirectory(File file) throws ImageProcessingException, IOException {
//        Metadata metadata = ImageMetadataReader.readMetadata(file);
//        Directory directory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
//        Date date = directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
//        if (date == null) {
//            date = new Date(file.lastModified());
//        }

        Date date = new Date(file.lastModified());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int monthInt = calendar.get(Calendar.MONTH);
        String month = new DateFormatSymbols().getMonths()[monthInt];
        int year = calendar.get(Calendar.YEAR);
        return year + File.separator + monthInt + "-" + month + File.separator;
    }

    private boolean doesFileExistInOutputDirectory(File file) {
        return new File(outputDirectory + file.getName()).exists();
    }
}
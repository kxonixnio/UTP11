package zad1;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.io.IOException;

public class TravelData {
    private File dataDir;   //"data"
    private static List<String> output = new ArrayList<>();

    public TravelData(File datafir) {
        this.dataDir = datafir;
    }

    public List<String> getOffersDescriptionsList(String loc, String dateFormat) {
        readFilesToList(dataDir);

        return output;
    }

    public void readFilesToList(File dataDir){
        try {
            FileVisitor<Path> fileVisitor = new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path visitedFilePath, BasicFileAttributes fileAttributes) throws IOException {
                    try (BufferedReader in = new BufferedReader(
                            new InputStreamReader(new FileInputStream(visitedFilePath.toFile()), StandardCharsets.UTF_8))) {
                        String str;
                        while ((str = in.readLine()) != null) {
                            output.add(str);
                        }
                    }
                    return FileVisitResult.CONTINUE;
                }
            };

            Files.walkFileTree(Paths.get(String.valueOf(dataDir)), fileVisitor);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


//https://mkyong.com/java/how-to-read-utf-8-encoded-data-from-a-file-java/
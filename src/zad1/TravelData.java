package zad1;

import java.io.File;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.io.IOException;

public class TravelData {
    private List<Record> data = new ArrayList<>();
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public TravelData(File datadir) {
        readData(datadir);
    }

    public List<String> getOffersDescriptionsList(String loc, String dateFormat) {
        return null;
    }

    public void readData(File dataDir){
        try {
            FileVisitor<Path> fileVisitor = new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path visitedFilePath, BasicFileAttributes fileAttributes) throws IOException {
                    try (BufferedReader in = new BufferedReader(
                            new InputStreamReader(new FileInputStream(visitedFilePath.toFile()), StandardCharsets.UTF_8))) {
                        String line;
                        while ((line = in.readLine()) != null) {
                            String[] lineData = line.split("\t");
                            int i = 0;
                            Locale locale = Locale.forLanguageTag(lineData[i++].replace("_", "-"));
                            NumberFormat numberFormat = NumberFormat.getInstance(locale);

                            try {
                                data.add(
                                        new Record(
                                                locale,
                                                lineData[i++],
                                                simpleDateFormat.parse(lineData[i++]),
                                                simpleDateFormat.parse(lineData[i++]),
                                                lineData[i++],
                                                numberFormat.parse(lineData[i++]).doubleValue(),
                                                lineData[i]
                                        )
                                );
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    return FileVisitResult.CONTINUE;
                }
            };

            Files.walkFileTree(Paths.get(String.valueOf(dataDir)), fileVisitor);

            for(Record record : data){
                System.out.println(record.getCountryCode() + " " + record.getCountryName() + " " + record.getDateFrom() + " " + record.getDateTo() + " " + record.getLocation() + " " + record.getPrice() + " " + record.getCurrency());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


//https://mkyong.com/java/how-to-read-utf-8-encoded-data-from-a-file-java/
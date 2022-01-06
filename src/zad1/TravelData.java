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
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TravelData {
    private List<Record> dataAsRecords = new ArrayList<>();
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private List<String> dataAsString = new ArrayList<>();

    public TravelData(File datadir) {
        readData(datadir);
    }

    public List<String> getOffersDescriptionsList(String loc, String dateFormat) {
        List<String> list = new ArrayList<>();

        Locale destLocale = Locale.forLanguageTag(loc.replace("_", "-"));
        NumberFormat numberFormat = NumberFormat.getInstance(destLocale);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);

        dataAsRecords.forEach(record -> {
            StringBuilder bobTheBuilder = new StringBuilder();

            String country = translateCountry(record.getCountryCode(), destLocale, record.getCountryName());
            bobTheBuilder.append(country).append(" ");
            bobTheBuilder.append(simpleDateFormat.format(record.getDateFrom())).append(" ");
            bobTheBuilder.append(simpleDateFormat.format(record.getDateTo())).append(" ");
            bobTheBuilder.append(translateLocation(destLocale, record.getLocation())).append(" ");
            bobTheBuilder.append(numberFormat.format(record.getPrice())).append(" ");
            bobTheBuilder.append(record.getCurrency());

            list.add(bobTheBuilder.toString());
        });

        //łączymy to co już zczytane z tym co zczytaliśmy teraz
        dataAsString = Stream.concat(dataAsString.stream(), list.stream()).collect(Collectors.toList());

        //Zwracamy tylko listę wczytaną "teraz" tak, żeby tylko ona została wyświetlona w Mainie (żeby nie wyświetlać
        //drugi raz tego, co zostało wcześniej wyświetlone
        return list;
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
                                dataAsRecords.add(
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

//            for(Record record : data){
//                System.out.println(record.getCountryCode() + " " + record.getCountryName() + " " + record.getDateFrom() + " " + record.getDateTo() + " " + record.getLocation() + " " + record.getPrice() + " " + record.getCurrency());
//            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String translateCountry(Locale inLocale, Locale outLocale, String countrName) {
        for (Locale loc : Locale.getAvailableLocales()) {
            if (loc.getDisplayCountry(inLocale).equals(countrName)) {
                return loc.getDisplayCountry(outLocale);
            }
        }

        return null;
    }

    private String translateLocation(Locale destLocale, String location){
        HashMap<String, String> locations = new HashMap<>();
        locations.put("morze", "sea");
        locations.put("jezioro", "lake");
        locations.put("góry", "mountains");

        if(destLocale.toString().startsWith("pl")) {
            for (Map.Entry<String,String> entry : locations.entrySet()){
                if(location.equals(entry.getValue())){
                    return entry.getKey();
                }
            }
        }
        else if(destLocale.toString().startsWith("en")) {
            for (Map.Entry<String,String> entry : locations.entrySet()){
                if(location.equals(entry.getKey())){
                    return entry.getValue();
                }
            }
        }

        return location;
    }

    public List<String> getDataAsString() {
        return dataAsString;
    }

    public List<Record> getDataAsRecords() {
        return dataAsRecords;
    }
}


//https://mkyong.com/java/how-to-read-utf-8-encoded-data-from-a-file-java/
//https://stackoverflow.com/questions/189559/how-do-i-join-two-lists-in-java

/*
Japonia 2015-09-01 2015-10-01 jezioro 10 000,2 PLN
Włochy 2015-07-10 2015-07-30 morze 4 000,1 PLN
Stany Zjednoczone Ameryki 2015-07-10 2015-08-30 góry 5 400,2 USD
Japan 2015-09-01 2015-10-01 lake 10,000.2 PLN
Italy 2015-07-10 2015-07-30 sea 4,000.1 PLN
United States 2015-07-10 2015-08-30 mountains 5,400.2 USD
 */
package zad1;

import java.util.ListResourceBundle;

public class LocationInfo_pl_PL extends ListResourceBundle {

    @Override
    protected Object[][] getContents() {
        return contents;
    }

    private Object[][] contents = {
            { "sea", "morze" },
            { "lake", "jezioro" },
            { "mountains", "góry" }
    };
}
package zad1;

import java.util.ListResourceBundle;

public class LocationInfo_en_GB extends ListResourceBundle {

    @Override
    protected Object[][] getContents() {
        return contents;
    }

    private Object[][] contents = {
            { "morze", "sea" },
            { "jezioro", "lake" },
            { "góry", "mountains" }
    };
}

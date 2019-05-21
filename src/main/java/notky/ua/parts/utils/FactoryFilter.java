package notky.ua.parts.utils;

import notky.ua.parts.constants.Filter;

public class FactoryFilter {

    public static Filter getFilter(String value){
        if(value == null){
            return null;
        }
        switch (value){
            case "All items":
                return Filter.All;
            case "Only necessary items":
                return Filter.NECESSARY;
            case "Other items":
                return Filter.OTHER;
        }
        return null;
    }
}

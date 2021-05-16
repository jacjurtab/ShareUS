package com.example.shareus;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Utils {

    public static String prettyParse(Date given) {
        SimpleDateFormat full = new SimpleDateFormat("dd/MM/yyyy", Locale.forLanguageTag("es-ES"));
        SimpleDateFormat time = new SimpleDateFormat("HH:mm", Locale.forLanguageTag("es-ES"));
        SimpleDateFormat date = new SimpleDateFormat("dd MMMM", Locale.forLanguageTag("es-ES"));

        Date current = new Date();
        Calendar currentCal = Calendar.getInstance();
        currentCal.setTime(current);
        Calendar givenCal = Calendar.getInstance();
        givenCal.setTime(given);

        int givenYear = givenCal.get(Calendar.YEAR);
        int currentYear = currentCal.get(Calendar.YEAR);
        int givenDay = givenCal.get(Calendar.DAY_OF_YEAR);
        int currentDay = currentCal.get(Calendar.DAY_OF_YEAR);

        if (givenYear == currentYear) {
            int value = givenDay - currentDay;
            switch (value) {
                case -1:
                    return "ayer a las " + time.format(given);
                case 0:
                    return "hoy a las " + time.format(given);
                case 1:
                    return "mañana a las " + time.format(given);
                default:
                    String format = date.format(given);
                    if(format.startsWith("0"))
                        format = format.substring(1);
                    return format + " a las " + time.format(given);
            }
        } else {
            return full.format(given) + " a las " + time.format(given);
        }
    }
}

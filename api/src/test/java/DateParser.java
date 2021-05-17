import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateParser {

    public static void main(String[ ] args) {
        System.out.println(prettyParse(new Date()));
        System.out.println(prettyParse(new Date(new Timestamp(1621085400000L).getTime())));
        System.out.println(prettyParse(new Date(new Timestamp(1621276200000L).getTime())));
        System.out.println(prettyParse(new Date(new Timestamp(1620671400000L).getTime())));
        System.out.println(prettyParse(new Date(new Timestamp(1589135400000L).getTime())));
    }

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
                    return date.format(given) + " a las " + time.format(given);
            }
        } else {
            return full.format(given) + " a las " + time.format(given);
        }
    }

}

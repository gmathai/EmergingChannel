package utils;

/**
 * Created by sangeethaasokarajan on 11/9/17.
 */
public class DateTimeFormat {

    public static String getDateTimeFormatP2F(String dateTime) {
        String date = dateTime.substring(0, 10);
        String splitdate[] = date.split("-");
        return splitdate[2] + " " + month(splitdate[1]) + " " + splitdate[0];
    }

    private static String month(String month) {

        switch (month) {

            case "01":
                return "JAN";
            case "02":
                return "FEB";
            case "03":
                return "MAR";
            case "04":
                return "APR";
            case "05":
                return "MAY";
            case "06":
                return "JUN";
            case "07":
                return "JUL";
            case "08":
                return "AUG";
            case "09":
                return "SEP";
            case "10":
                return "OCT";
            case "11":
                return "NOV";
            case "12":
                return "DEC";

        }
        return null;
    }
}

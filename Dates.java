import java.io.*;
import java.util.*;
import java.text.*;

class MyData {

    int day;
    int month;
    int year;
    String weekday;

    public MyData(int day, int month, int year, String weekday) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.weekday = weekday;
    }
}

public class Dates {

    public static void main(String[] args) {
        int numberofdates = convertDate("InputData.txt", "MyData.txt");
        System.out.println("Number of unique dates is : " + numberofdates);
    }

    public static int convertDate(String Inputfile, String Outputfile) {
        ArrayList<MyData> uniqueData = new ArrayList<MyData>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(Inputfile));
            String line;
            while ((line = reader.readLine()) != null) {
                MyData mydata = parseDate(line);
                if (mydata != null && !uniqueData.contains(mydata)) {
                    uniqueData.add(mydata);
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            BufferedWriter Writer = new BufferedWriter(new FileWriter(Outputfile));
            for (MyData mydata : uniqueData) {
                Writer.write(String.format("day = %d, month = %d, year = %d, weekday = %s%n", mydata.day, mydata.month, mydata.year, mydata.weekday));
            }
            Writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return uniqueData.size();
    }

    public static MyData parseDate(String line) {
        DateFormat[] formats = {
            new SimpleDateFormat("dd/MM/yyyy EEEE"),
            new SimpleDateFormat("dd/M/yyyy EEEE"),
            new SimpleDateFormat("yyyy-MM-dd EEEE"),
            new SimpleDateFormat("EEEE dd.MM.yyyy"),
            new SimpleDateFormat("MMMM dd, yyyy EEEE"),
            new SimpleDateFormat("EEEE dd/MM/yyyy")
        };

        for (DateFormat dateformat : formats) {
            try {
                Date date = dateformat.parse(line);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH) + 1;
                int year = calendar.get(Calendar.YEAR);
                String weekday = findWeekdayInLine(line);
                if (weekday != null) {
                    return new MyData(day, month, year, weekday);
                }
            } catch (ParseException ignored) {
            }
        }
        return null;
    }

    public static String findWeekdayInLine(String line) {
        String[] weekdays = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

        for (String weekday : weekdays) {
            if (line.contains(weekday)) {
                return weekday;
            }
        }

        return null;
    }
}

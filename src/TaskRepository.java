import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TaskRepository {
    public static Scanner in = new Scanner(System.in);
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static String FILE_PATH = "tasks.json";


// פונקצית עזר שממירה את קובץ ה json ומחזירה אותו כמערך
    public static List<Task> ConvertsToArray() throws IOException{
        try (FileReader reader = new FileReader(FILE_PATH)) {
            Type listType = new TypeToken<ArrayList<Task>>(){}.getType();
            List<Task> tasks = gson.fromJson(reader, listType);

            return tasks != null ? tasks : new ArrayList<>();
        }
        catch (IOException e) {
            System.err.println("error reading file: " + e.getMessage());
            return new ArrayList<>();
        }
    }

//    פונקצית עזר שממירה את המערך לקובץ json ןמכניסה אותו לקובץ
    public static void ConvertsToJson(List<Task> tasks) throws IOException{
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(tasks, writer);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

//    פונקציה להוספת משימה חדשה
    public static void addTask() {
        try {
        List<Task> tasks = ConvertsToArray(); //שליפה מה json

        System.out.println("להוספת משימה חדשה, הזן כותרת:");
        String ttl = in.next();
        System.out.println("הזן תאור משימה:");
        String dsc = in.next();

        Task t = new Task(ttl, dsc); // קריאה לבנאי
        tasks.add(t); //  והוספה
        ConvertsToJson(tasks); // החזרה ל json
        System.out.println(t.toString());
        }
        catch (IOException e) {
            System.err.println(" שגיאה בקריאה לקובץ: " + e.getMessage());
        }
        catch (Exception e) {
            System.err.println(" שגיאה" + e.getMessage());
        }
    }

    // פונקצית עדכון לפי Id
    public static void updateById(int id, String title, String description, Status status) {
        try{
        List<Task> tasks = ConvertsToArray(); //שליפה מה json
        boolean ok = false;
        //מחפש את הid כדי להדכנו
        for (Task t : tasks) {
            if (t.getId() == id) {
                // האיפים הפנימים הם לבדיקה האם הכניסו ערך לעדכון או לא
                // (אולי לא רצו לעדכן ולא שלחו בערך כלום).
                if(description != "")
                    t.setDescription(description);
                if(title != "")
                    t.setTitle(description);
                t.setStatus(status);
                ok = true;
                break;
            }
        }

        if (ok) { // אם מצא id מעדכן לקובץ
            ConvertsToJson(tasks);
            getById(id);
        }
        else
            System.out.println(id + " not found id");
        }
        catch (IOException e) {
            System.err.println(" שגיאה בקריאה לקובץ: " + e.getMessage());
        }
        catch (Exception e) {
            System.err.println(" שגיאה" + e.getMessage());
        }
    }

    // פונקצית מחיקה לפי id
    public static void deleteById(int id) {
        try {
            List<Task> tasks = ConvertsToArray(); //שליפה מה json
            boolean deleted = tasks.removeIf(task -> task.getId() == id);// מחיקת ה Id המרשימה

            if (deleted) { // אם מחק את המשימה
                ConvertsToJson(tasks); // מעדכן את הקובץ
                System.out.println(id + "נמחק ");
            } else
                System.out.println(id + " לא נמצא ");
        }
        catch (IOException e) {
            System.err.println(" שגיאה בקריאה לקובץ: " + e.getMessage());
        }
        catch (Exception e) {
            System.err.println(" שגיאה" + e.getMessage());
        }
    }


    // פונקצית הדפסה לפי id
    public static void getById(int id) {
        try {
            List<Task> tasks = ConvertsToArray(); //שליפה מה json
            for (Task task : tasks) // מחפש את המשימה לפי Id
                if (task.getId() == id) // אם מצא - מדפיס
                    System.out.println(task.toString());
        }
        catch (IOException e) {
            System.err.println(" שגיאה בקריאה לקובץ: " + e.getMessage());
        }
        catch (Exception e) {
            System.err.println(" שגיאה" + e.getMessage());
        }
    }

    // מדפיסה את כל המשימות
    public static void listAll() {
        try{
        List<Task> tasks = ConvertsToArray(); // שליפה מה-json
        if (tasks.isEmpty())
            System.out.println("אין משימות");
        else {
            System.out.println("--- רשימת משימות ---");
            for (Task task : tasks) { // עוברת בלולאה על המשימות ושולחת לטוסטרינג
                System.out.println(task.toString());
            }
            System.out.println("----------------------");
        }
        }
        catch (IOException e) {
            System.err.println(" שגיאה בקריאה לקובץ: " + e.getMessage());
        }
        catch (Exception e) {
            System.err.println(" שגיאה" + e.getMessage());
        }
    }
}

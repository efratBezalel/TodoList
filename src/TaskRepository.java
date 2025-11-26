import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import javax.crypto.spec.PSource;
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
            System.err.println("error reading file1111: " + e.getMessage());
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
    public static void updateById(int id, String ttl, String dsc, Status s) {
        try{
        List<Task> tasks = ConvertsToArray(); //שליפה מה json
        boolean ok = false;
        //מחפש את הid כדי להדכנו
        for (Task t : tasks) {
            if (t.getId() == id) {
                // האיפים הפנימים הם לבדיקה האם הכניסו ערך לעדכון או לא
                // (אולי לא רצו לעדכן ולא שלחו בערך כלום).
                if(!dsc.equals("לא"))
                    t.setDescription(dsc);
                if(!ttl.equals("לא"))
                    t.setTitle(ttl);
                if (s != null)
                t.setStatus(s);
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
    // פונקצית עזר שקולטת נתונים מהמשתמש ובונה משימה
    public static void updtTask(){

        System.out.println("הזן id אותו תרצה לשנות");
        int id = in.nextInt();
        System.out.println("אם ברצונך לשנות את הכותרת כיתבי אותה כאן ולא כתבי לא:");
        String ttl = in.next();
        System.out.println("אם ברצונך לשנות את התאור כיתבי אותה כאן ולא כתבי לא:");
        String dsc = in.next();
        System.out.println("האם ברצונך לשנות את סטטוס הפעולה?");
        System.out.println("לשינוי הקש 1, להשארת המצב הקיים הקש 0");
        int status = in.nextInt();
        Status s = null;
        if(status == 1)
            s = getStatus();
        updateById(id, ttl, dsc, s);
    }

    // פונקצית מחיקה לפי id
    public static void deleteById() {
        try {
            System.out.println("הזן id אותו תרצה למחוק");
            int id = in.nextInt();
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
    // פונקצית עזר ל getById כדי שתתאים לעוד פונקציות
    public static void getByIdBefore() {
        System.out.println("הזן id אותו תרצה לראות");
        int id = in.nextInt();
        getById(id); // קריאה לפונקציה שמציגה את המשימה
    }

    // מדפיסה את כל המשימות
    public static void listAll() {
        try{
            List<Task> tasks = ConvertsToArray(); // שליפה מה-json
            printListAll(tasks);
        }
        catch (IOException e) {
            System.err.println(" שגיאה בקריאה לקובץ: " + e.getMessage());
        }
        catch (Exception e) {
            System.err.println(" שגיאה" + e.getMessage());
        }
    }

    // פונקצית עזר להדפסת רשימה
    public static void printListAll(List<Task> tasks){
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

    // פונקציית עזר להגדרת ה id ללא חזרות
    private static int maxId = 0;
    public static int getNextId() {
        return ++ maxId;
    }

    // פונקצייה שנקראת בתחילת הפרויקט ומאתחלת את ה id
    public static void initialId() throws IOException {
        List<Task> tasks = ConvertsToArray();
        if (tasks != null && !tasks.isEmpty()) {
            int max = tasks.stream()
                    .mapToInt(Task::getId)
                    .max() // מוצא את המקסימום
                    .orElse(0); // אם הרשימה ריקה, מחזיר 0

            maxId = max;
        }
        else
            maxId = 0;

    }

    //פונקציה לבחירת סטטוס לפי מספר שמזינים
    public static Status getStatus(){
        System.out.println("להגדרת משימה כ- NEW הקש 1");
        System.out.println("להגדרת משימה כ- IN_PROGRESS הקש 2");
        System.out.println("להגדרת משימה כ- DONE הקש 3");
        System.out.println("הקש את המספר הרצוי:");
        int a = in.nextInt();

        while (a != 1 || a != 2 || a != 3)
            switch (a) {
                case (1):
                    return Status.NEW;
                case (2):
                    return Status.IN_PROGRESS;
                case (3):
                    return Status.DONE;
                default:
                    System.out.println("מספר לא תקין הכנס מספר בין 1 ל 3");
                    a = in.nextInt();
                    break;
            }
        return Status.NEW;
    }
}

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TaskRepository {
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final String FILE_PATH = "tasks.json";


// פונקצית עזר שממירה את קובץ ה json ומחזירה אותו כמערך
    public List<Task> ConvertsToArray() {
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
    public void ConvertsToJson(List<Task> tasks) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(tasks, writer);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

//    פונקציה להוספת משימה חדשה
    public void addTask(Task newTask) throws IllegalArgumentException {
        List<Task> tasks = ConvertsToArray(); //שליפה מה json
        tasks.add(newTask); // הוספה
        ConvertsToJson(tasks); // החזרה ל json
        System.out.println(newTask.getId() + "המשימה נוספה למערכת, id = " );
    }

    // פונקצית עדכון לפי Id
    public void updateById(int id, String title, String description, Status status) {

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
            System.out.println(id + " found id");
        }
         else
            System.out.println(id + " not found id");
    }

    // פונקצית מחיקה לפי id
    public void deleteById(int id) {

        List<Task> tasks = ConvertsToArray(); //שליפה מה json
        boolean deleted = tasks.removeIf(task -> task.getId() == id);// מחיקת ה Id המרשימה

        if (deleted) { // אם מחק את המשימה
            ConvertsToJson(tasks); // מעדכן את הקובץ
            System.out.println(id + "נמחק ");
        } else
            System.out.println(id + " לא נמצא ");
    }


    // פונקצית הדפסה לפי id
    public void getById(int id) {

        List<Task> tasks = ConvertsToArray(); //שליפה מה json
        for (Task task : tasks) // מחפש את המשימה לפי Id
            if (task.getId() == id) // אם מצא - מדפיס
                System.out.println(task.toString());
    }

    // מדפיסה את כל המשימות
    public void listAll() {

        List<Task> tasks = ConvertsToArray(); // שליפה מה-json
        for (Task task : tasks) // עוברים בלולאה ומדפיסים
            System.out.println(task.toString());
    }
}

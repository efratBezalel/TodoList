import java.util.List;
import java.util.Collections;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.io.IOException;

public class TaskService {
    public static Scanner in = new Scanner(System.in);
    // פונקצית סיום משימה
    public static void EndTask() {
        System.out.println("הזן id של המשימה שאותה סימת");
        int id = in.nextInt();
        TaskRepository.updateById(id, "", "" ,Status.DONE); // זה המבנה של פונקציית עדכון
    }

    // פונקציית מיון לפי הסטטוס
    public static void getSortedTasks() {
        try {
            System.out.println("a");
            List<Task> tasks = TaskRepository.ConvertsToArray(); // שליפה מ JSON

            List<Task> sortTask = tasks.stream() // מבצע מיון על מערך עזר כדי שהקובץ ישאר לפי סדר ה id
                    .sorted()
                    .collect(Collectors.toList());
            TaskRepository.printListAll(sortTask);
        }
        catch (IOException e) {
            System.err.println(" שגיאה" + e.getMessage());
        }
        catch (Exception e) {
            System.err.println(" שגיאה" + e.getMessage());
        }
    }


    public static void searchTask() {
        try {
            System.out.println("הזן את הטקסט אותו תרצה לחפש");
            String txt = in.next();
            List<Task> tasks = TaskRepository.ConvertsToArray(); //שליפה מה json

            // מבצע סינון ומחזיר רק את המשימות שעומדות בקריטריונים
            if (txt != null) {
                List<Task> results = tasks.stream()
                        .filter(task ->
                                task.getTitle().contains(txt) ||
                                        task.getDescription().contains(txt)
                        )
                        .collect(Collectors.toList());
                if (results.isEmpty())
                    System.out.println("אין משימות שתואמות למחרוזת החיפוש.");
                for (Task task : results) { // עוברת בלולאה על המשימות ושולחת לטוסטרינג
                    System.out.println(task.toString());
                }
                System.out.println("----------------------");
            }
            else
                System.out.println("לא נמצאה מחרוזת מתאימה");
        }

        catch (IOException e) {
            System.err.println(" שגיאה" + e.getMessage());
        }
        catch (Exception e) {
            System.err.println(" שגיאה" + e.getMessage());
        }
    }
}

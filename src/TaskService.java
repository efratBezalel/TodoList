import java.util.List;
import java.util.Collections;
import java.util.stream.Collectors;
import java.io.IOException;

public class TaskService {

    // פונקצית סיום משימה
    public void EndTask(int id) {
        TaskRepository.updateById(id, "", "" ,Status.DONE); // זה המבנה של פונקציית עדכון
    }

    // פונקציית מיון לפי הסטטוס
    public void getSortedTasks() {
        try {
            List<Task> tasks = TaskRepository.ConvertsToArray(); // שליפה מ JSON
            Collections.sort(tasks);
//            return tasks;
        }
        catch (IOException e) {
            System.err.println(" שגיאה" + e.getMessage());
        }
        catch (Exception e) {
            System.err.println(" שגיאה" + e.getMessage());
        }
    }


    public void searchTask(String txt) {
        try {
        List<Task> tasks = TaskRepository.ConvertsToArray(); //שליפה מה json

        // מבצע סינון ומחזיר רק את המשימות שעומדות בקריטריונים
        if (txt != null) {
            List<Task> results = tasks.stream()
                    .filter(task ->
                            task.getTitle().contains(txt) ||
                                    task.getDescription().contains(txt)
                    )
                    .collect(Collectors.toList());

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

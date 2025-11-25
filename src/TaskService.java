import java.util.List;
import java.util.Collections;
import java.util.stream.Collectors;

public class TaskService {

    // פונקצית סיום משימה
    public void EndTask(int id) {
        TaskRepository.updateById(id, "", "" ,Status.DONE); // זה המבנה של פונקציית עדכון
    }

    // פונקציית מיון לפי הסטטוס
    public List<Task> getSortedTasks() {

        List<Task> tasks = TaskRepository.ConvertsToArray(); // שליפה מ JSON
        Collections.sort(tasks);
        return tasks;
    }


    public void searchTask(String txt) {

        List<Task> tasks = TaskRepository.ConvertsToArray(); //שליפה מה json

        // 3. שימוש ב-Stream לסינון והחזרה
        if (txt != null) {
            List<Task> results = tasks.stream()
                    .filter(task ->
                            task.getTitle().contains(txt) ||
                                    task.getDescription().contains(txt)
                    )
                    .collect(Collectors.toList());
            printList(results);
        }
        else
            System.out.println("לא נמצאה מחרוזת מתאימה");
    }
}

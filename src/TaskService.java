import java.util.List;
import java.util.Collections;

public class TaskService {

    // פונקצית סיום משימה
    public void EndTask(int id) {
        updateById(id, "", "" ,Status.DONE); // זה המבנה של פונקציית עדכון
    }

    // פונקציית מיון לפי הסטטוס
    public List<Task> getSortedTasks() {

        List<Task> tasks = ConvertsToArray(); // שליפה מ JSON
        Collections.sort(tasks);
        return tasks;
    }
    
}

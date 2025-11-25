import java.util.List;

public class TaskService {

    // פונקצית סיום משימה
    public void EndTask(int id) {
        updateById(id, "", "" ,Status.DONE); // זה המבנה של פונקציית עדכון
    }

}

import java.util.List;

public class Task implements Comparable<Task>{

    private int id;
    private String title;
    private String description;
    private Status status;

    public Task(String title, String description) {
        this.id = TaskRepository.getNextId();;
        this.title = title;
        this.description = description;
        this.status = Status.NEW;
    }
    public Task(int id, String title, String description, Status status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }


    // פונקציית עזר בשביל לקבוע מיון לפונקצייה הדורסת של קומפר תו
    public static int getNmber(Task task) {

        if (task == null || task.getStatus() == null)
            return 999999; // כדי שיופיע בסוף המיון
        switch (task.getStatus()) {
            case NEW:
                return 1;
            case IN_PROGRESS:
                return 2;
            default:
                return 3;
        }
    }

    //דריסת פונקציית קומפראבל שתתאים למיון לפי הסטטוס
    @Override
    public int compareTo(Task task) {
        //שולח לפונקציה למעלה ולפי זה משווה בין האיברים ובודק מי לפני מי
        int cmpr = Integer.compare(getNmber(this),getNmber(task));
        if (cmpr != 0)
            return cmpr;
        return Integer.compare(this.id, task.id);// אם הסטטוסים שווים מחזירים את הקודם בסדר הכרונולוגי
    }



}

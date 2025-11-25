import com.google.gson.Gson;

import java.util.Scanner;

public class Main {
    Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int a = 1;

        while(a != 0) {

            System.out.println("--- תפריט פעולות ---");
            System.out.println("איזו פעולה תרצה לבצע? אנא בחר את המספר הרצוי:");
            System.out.println("");
            System.out.println("1. הוספת משימה חדשה (Add)");
            System.out.println("2. עדכון משימה קיימת (Update)");
            System.out.println("3. מחיקת משימה (Delete)");
            System.out.println("4. אחזור משימה לפי מזהה (getById)");
            System.out.println("5. הצגת רשימת כל המשימות (listAll)");
            System.out.println("6. סימון משימה כ-DONE");
            System.out.println("7. חיפוש משימות לפי טקסט ב-Title או Description");
            System.out.println("8. החזרת רשימת משימות ממויינת לפי Status");
            System.out.println("ליציאה הקש 0");
            System.out.println("");
            System.out.println("אנא הזן את מספר הפעולה:");
            a = in.nextInt();

            switch (a) {
                case 1: TaskRepository.addTask(); break;
                case 2:
                case 3:
                case 4:
                case 5:

                    break;
                case 6:
                case 7:

                    break;

                default:

                    break;
            }
        }
    }

}


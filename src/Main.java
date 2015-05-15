import controller.CurrentTask;
import view.Menu;


public class Main {
    public static void main(final String[] args) {
        CurrentTask.getInstance();
        Menu menu = Menu.getMenu();
        menu.printMenu();
    }
}


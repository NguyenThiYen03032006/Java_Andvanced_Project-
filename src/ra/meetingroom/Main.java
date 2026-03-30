package ra.meetingroom;

import ra.meetingroom.model.people.User;
import ra.meetingroom.presentation.AdminMenu;
import ra.meetingroom.presentation.AuthMenu;
import ra.meetingroom.presentation.EmployeeMenu;
import ra.meetingroom.presentation.SupportMenu;

public class Main {
    public static User currentUser = null;

    public static void main(String[] args) {
        AuthMenu authMenu = new AuthMenu();

        while (true) {
            currentUser = authMenu.showMenuAuth();

            if (currentUser != null) {
                switch (currentUser.getRole()) {
                    case "ADMIN":
                        new AdminMenu().show();
                        break;

                    case "SUPPORT":
                        new SupportMenu().show(currentUser);
                        break;

                    default:
                        new EmployeeMenu().show();
                }

                currentUser = null; // logout
            }
        }
    }
}


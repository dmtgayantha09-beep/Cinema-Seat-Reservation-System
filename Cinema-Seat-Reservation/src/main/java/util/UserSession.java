package util;

import model.User;

public final class UserSession {

    private static User currentUser;

    private UserSession() {
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static int getCurrentUserId() {
        return currentUser == null ? 0 : currentUser.getUserId();
    }

    public static void clear() {
        currentUser = null;
    }
}

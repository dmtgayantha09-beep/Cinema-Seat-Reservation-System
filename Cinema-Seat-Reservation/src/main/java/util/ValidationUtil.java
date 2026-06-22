package util;

public class ValidationUtil {

    public static boolean isValidEmail(
            String email) {

        return email.matches(
                "^[A-Za-z0-9+_.-]+@(.+)$"
        );
    }

    public static boolean isEmpty(
            String text) {

        return text == null ||
               text.trim().isEmpty();
    }
}
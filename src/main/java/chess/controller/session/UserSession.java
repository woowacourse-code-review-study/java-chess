package chess.controller.session;

import chess.domain.user.User;

public class UserSession {
    private static final ThreadLocal<User> session = new ThreadLocal<>();

    private UserSession() {
    }

    public static void add(final User user) {
        session.set(user);
    }

    public static User get() {
        return session.get();
    }

    public static int getId() {
        final User auth = session.get();
        if (auth == null) {
            return AnonymousKeyGenerator.getKey();
        }
        return session.get().getId();
    }

    public static String getName() {
        final User auth = session.get();
        if (auth == null) {
            return "anonymous";
        }
        return auth.getName();
    }

    public static void remove() {
        session.remove();
        AnonymousKeyGenerator.remove();
    }
}

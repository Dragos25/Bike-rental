package inc.conn;

import inc.def.*;

public class Session {
    static Account loggedIn;
    Session(){}

    public static Account getLoggedIn() {
        return loggedIn;
    }

    public static void setLoggedIn(user loggedIn) {
        Session.loggedIn = loggedIn;
    }

    public static void setLoggedIn(company loggedIn) {
        Session.loggedIn = loggedIn;
    }
}

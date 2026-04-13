package lcu.backend.server.session;

public class SessionData {
    private final String username;
    private final String role;
    private final String message;

    public SessionData(String cookieId, String role, String message) {
        this.username = cookieId;
        this.role = role;
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public String getMessage() {
        return message;
    }
}

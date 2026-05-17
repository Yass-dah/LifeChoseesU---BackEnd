package lcu.backend.server.session;

public class SessionData {
    private final String username;
    private final String role;
    private final String country;
    private final String message;

    public SessionData(String cookieId, String role, String country, String message) {
        this.username = cookieId;
        this.role = role;
        this.country = country;
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public String getCountry() {
        return country;
    }

    public String getMessage() {
        return message;
    }
}

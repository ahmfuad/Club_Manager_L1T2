import com.javafx.clubclient.*;
public class Client {
    private Credentials credentials;
    private boolean authStatus;

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public boolean isAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(boolean authStatus) {
        this.authStatus = authStatus;
    }

    Client(Credentials cr, boolean authStatus) {
        this.credentials = cr;
        this.authStatus = authStatus;
    }




}

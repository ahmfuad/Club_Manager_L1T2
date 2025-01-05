import com.javafx.clubclient.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClubDatabase {
    private List<Credentials> clubData = new ArrayList<>();
    private static final String INPUT_FILE_NAME = "F:\\java\\Server\\src\\ClubCredentials.txt";

    ClubDatabase() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE_NAME));
            while (true) {
                String line = br.readLine();
                if (line == null) break;
                //System.out.println(line);
                String[] club = line.split(",");

                Credentials newClub = new Credentials(club[0], club[1]);
                clubData.add(newClub);
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean checkCredentials(String username, String password) {
        if(username.trim().isEmpty() || password.trim().isEmpty()) {
            return false;
        }

        for(Credentials x : clubData) {

            if(x.getUsername().equals(username) && x.getPassword().equals(password)) {
                return true;
            }
        }

        return false;
    }

    public boolean ifContains(Credentials c) {
        for(Credentials x: clubData) {
            if(x.equals(c)) {
                return true;
            }

        }

        return false;
    }


}

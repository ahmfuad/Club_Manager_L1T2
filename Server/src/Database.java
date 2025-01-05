import com.javafx.clubclient.BidRequest;
import com.javafx.clubclient.Player;
import com.javafx.clubclient.PlayerWithButton;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database {
  private List<Player> playerDatabase = new ArrayList<>();
  private List<BidRequest> allBids = new ArrayList<>();
  private static final String INPUT_FILE_NAME = "src/players.txt";
  private static final String OUTPUT_FILE_NAME = "src/players.txt";

  private static Database instance;

  private Database() {
    try {
      BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE_NAME));
      while (true) {
        String line = br.readLine();
        if (line == null) break;
        String[] player = line.split(",");
        Player temp = new Player(player[0], player[1], player[2], player[3], player[4],
                player[5], player[6], player[7]);
        playerDatabase.add(temp);
      }
      br.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static synchronized Database getInstance() {
    if (instance == null) {
      instance = new Database();
    }
    return instance;
  }

  public List<Player> getPlayersList() {
    return playerDatabase;
  }

  public boolean addPlayer(Player p) {
    playerDatabase.add(p);
    return true;
  }

  public void showPlayerInfo() {
    for (Player p : playerDatabase) {
      p.showInfo();
    }
  }

  public Player searchByName(String x) {
    for (Player p : playerDatabase) {
      if (p.getName().equalsIgnoreCase(x)) {
        return p;
      }
    }
    return null;
  }

  public List<Player> searchByClubAndCountry(String club, String country) {
    List<Player> result = new ArrayList<>();
    if (country.equals("ANY")) {
      for (Player p : playerDatabase) {
        if (p.getClub().equalsIgnoreCase(club)) {
          result.add(p);
        }
      }
    } else if (club.equals("ANY")) {
      for (Player p : playerDatabase) {
        if (p.getCountry().equalsIgnoreCase(country)) {
          result.add(p);
        }
      }
    } else {
      for (Player p : playerDatabase) {
        if (p.getClub().equalsIgnoreCase(club) && p.getCountry().equalsIgnoreCase(country)) {
          result.add(p);
        }
      }
    }
    return result;
  }

  public List<Player> searchByPosition(String position) {
    List<Player> result = new ArrayList<>();
    for (Player p : playerDatabase) {
      if (p.getPosition().equalsIgnoreCase(position)) {
        result.add(p);
      }
    }
    return result;
  }

  public List<Player> searchBySalaryRange(int min, int max) {
    List<Player> result = new ArrayList<>();
    for (Player p : playerDatabase) {
      if (p.getSalary() >= min && p.getSalary() <= max) {
        result.add(p);
      }
    }
    return result;
  }

  public Map<String, Integer> countryWisePlayerCount() {
    Map<String, Integer> countryCount = new HashMap<>();
    for (Player p : playerDatabase) {
      countryCount.put(p.getCountry(), countryCount.getOrDefault(p.getCountry(), 0) + 1);
    }
    return countryCount;
  }

  public List<Player> maxSalaryOfClub(String club) {
    List<Player> result = new ArrayList<>();
    int max = 0;
    for (Player p : playerDatabase) {
      if (p.getClub().equalsIgnoreCase(club)) {
        max = Math.max(max, p.getSalary());
      }
    }
    for (Player p : playerDatabase) {
      if (p.getClub().equalsIgnoreCase(club) && p.getSalary() == max) {
        result.add(p);
      }
    }
    return result;
  }

  public List<Player> maxAgeOfClub(String club) {
    List<Player> result = new ArrayList<>();
    int max = 0;
    for (Player p : playerDatabase) {
      if (p.getClub().equalsIgnoreCase(club)) {
        max = Math.max(max, p.getAge());
      }
    }
    for (Player p : playerDatabase) {
      if (p.getClub().equalsIgnoreCase(club) && p.getAge() == max) {
        result.add(p);
      }
    }
    return result;
  }

  public List<Player> maxHeightOfClub(String club) {
    List<Player> result = new ArrayList<>();
    double max = 0;
    for (Player p : playerDatabase) {
      if (p.getClub().equalsIgnoreCase(club)) {
        max = Math.max(max, p.getHeight());
      }
    }
    for (Player p : playerDatabase) {
      if (p.getClub().equalsIgnoreCase(club) && p.getHeight() == max) {
        result.add(p);
      }
    }
    return result;
  }

  public int totalSalaryOfClub(String club) {
    int total = 0;
    for (Player p : playerDatabase) {
      if (p.getClub().equalsIgnoreCase(club)) {
        total += p.getSalary();
      }
    }
    return total * 52;
  }

  public boolean saveToFile() {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(OUTPUT_FILE_NAME))) {
      for (Player p : playerDatabase) {
        bw.write(String.join(",",
                p.getName(), p.getCountry(), String.valueOf(p.getAge()), String.valueOf(p.getHeight()),
                p.getClub(), p.getPosition(), String.valueOf(p.getJourseyNumber()), String.valueOf(p.getSalary())));
        bw.newLine();
      }
      return true;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }

  public void changePlayerInMarketplace(Player p) {
    for (Player x : playerDatabase) {
      if (x.getName().equals(p.getName())) {
        x.setInMarketPlace(!x.isInMarketPlace());
      }
    }
  }

  public List<Player> searchByMarketplace() {
    List<Player> result = new ArrayList<>();
    for (Player p : playerDatabase) {
      if (p.isInMarketPlace()) {
        result.add(p);
      }
    }
    return result;
  }

  public List<BidRequest> getAllBids() {
    return allBids;
  }

  public void setAllBids(List<BidRequest> allBids) {
    this.allBids = allBids;
  }

  public void addBid(BidRequest b) {
    this.allBids.add(b);
  }

  public void removeBid(BidRequest bidRequest) {
    allBids.removeIf(b -> b.getPlayer().getName().equals(bidRequest.getPlayer().getName()));
  }

  public void transferClub(Player p, String newClub) {
    for (Player x : playerDatabase) {
      if (x.getName().equalsIgnoreCase(p.getName())) {
        x.setClub(newClub);
        x.setInMarketPlace(false);
        //system.out.println.*
      }
    }
  }
}

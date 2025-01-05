package com.example.player_database;

public class Main {
  public static void main(String[] args) {
    
    Database database = new Database();
    //List<Player> allPlayers = database.getPlayersList();

    System.out.println(" ____  _      ____ __ __   ___ ____       ___    ____ ______  ____ ____   ____  _____  ___ ");
        System.out.println("|    \\| |    /    |  |  | /  _]    \\     |   \\  /    |      |/    |    \\ /    |/ ___/ /  _]");
        System.out.println("|  o  ) |   |  o  |  |  |/  [_|  D  )    |    \\|  o  |      |  o  |  o  )  o  (   \\_ /  [_ ");
        System.out.println("|   _/| |___|     |  ~  |    _]    /     |  D  |     |_|  |_|     |     |     |\\__  |    _]");
        System.out.println("|  |  |     |  _  |___, |   [_|    \\     |     |  _  | |  | |  _  |  O  |  _  |/  \\ |   [_ ");
        System.out.println("|  |  |     |  |  |     |     |  .  \\    |     |  |  | |  | |  |  |     |  |  |\\    |     |");
        System.out.println("""
        |__|  |_____|__|__|____/|_____|__|\\_|    |_____|__|__| |__| |__|__|_____|__|__| \\___|_____|
        
        """);
    
    MainMenu x = new MainMenu(database);
    x.showMenu();
    //database.showPlayerInfo();
    //new MainMenu(database).showMenu();
  }
}

package es.ericsson.masterCraftmanship.tfm.models;

public class GameTest {

    protected Game game;
    protected Game expectedGame;

    protected void setGame(Color color, String... strings) {
        this.game = new GameBuilder().color(color).rows(strings).build();
    }
    
    protected void setGameWithName(Color color,String gameName,  String... strings) {
    	this.game = new GameBuilder().color(color).rows(strings).gameName(gameName).build();
    }

    protected void setExpectedGame(Color color, String... strings) {
        this.expectedGame = new GameBuilder().color(color).rows(strings).build();
    }

}

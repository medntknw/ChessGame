package medntknw.chess_backend.controller;

import medntknw.chess_backend.game.ChessGame;
import medntknw.chess_backend.model.Player;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GameService {
    private final Map<String, ChessGame> games = new ConcurrentHashMap<>();

    public ChessGame createGame(String gameId, String player1, String player2) {
        ChessGame game = new ChessGame(gameId);
        Player p1 = new Player(player1, player1, player1);
        Player p2 = new Player(player2, player2, player2);
        p1.setWhiteSide(true);
        game.addPlayer(p1);
        game.addPlayer(p2);
        game.resetBoard();
        games.put(gameId, game);
        return game;
    }

    public ChessGame getGame(String gameId) {
        return games.get(gameId);
    }
}

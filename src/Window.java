/**
 *  Class representing game's main window.
 *  This class defines visual shape of game, handles buttons functionality
 *  and also creates game board as a two dimensional array.
 *
 *  Created by MK on 2017-06-10.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

class Window extends JFrame {

    static JPanel[][] boardTile = new Tile[8][8];

    Window(){
        super("Chess");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(500,550);
        setResizable(false);
        setLocation(200,100);

        setLayout(new FlowLayout(FlowLayout.TRAILING));

        //Buttons actions
        JButton NowaGra = new JButton("Nowa gra");
        JButton Zamknij = new JButton("Zamknij");

        NowaGra.addActionListener((ActionEvent e) -> GameState.newGame());

        Zamknij.addActionListener((ActionEvent e) -> {
            Window.this.setVisible(false);
            Window.this.dispose();
        });

        add(NowaGra);
        add(Zamknij);

        //Initializing Chess board
        boolean tileColor = true; // true - white, false - black (dark gray)
        for (int j = 0; j < 8; j++){
            for (int i = 0; i < 8; i++) {
                tileColor = !tileColor;
                boardTile[j][i] = new Tile(i, j, tileColor);
                add(boardTile[j][i]);
            }
            tileColor = !tileColor;
        }

        //Setting figures on right places
        ((Tile)boardTile[0][0]).setFieldContent(Tile.ROOK_WHITE);
        ((Tile)boardTile[0][1]).setFieldContent(Tile.KNIGHT_WHITE);
        ((Tile)boardTile[0][2]).setFieldContent(Tile.BISHOP_WHITE);
        ((Tile)boardTile[0][3]).setFieldContent(Tile.KING_WHITE);
        ((Tile)boardTile[0][4]).setFieldContent(Tile.QUEEN_WHITE);
        ((Tile)boardTile[0][5]).setFieldContent(Tile.BISHOP_WHITE);
        ((Tile)boardTile[0][6]).setFieldContent(Tile.KNIGHT_WHITE);
        ((Tile)boardTile[0][7]).setFieldContent(Tile.ROOK_WHITE);
        for (int i = 0; i < 8; i++){
            ((Tile)boardTile[1][i]).setFieldContent(Tile.PAWN_WHITE);
        }

        ((Tile)boardTile[7][0]).setFieldContent(Tile.ROOK_BLACK);
        ((Tile)boardTile[7][1]).setFieldContent(Tile.KNIGHT_BLACK);
        ((Tile)boardTile[7][2]).setFieldContent(Tile.BISHOP_BLACK);
        ((Tile)boardTile[7][3]).setFieldContent(Tile.KING_BLACK);
        ((Tile)boardTile[7][4]).setFieldContent(Tile.QUEEN_BLACK);
        ((Tile)boardTile[7][5]).setFieldContent(Tile.BISHOP_BLACK);
        ((Tile)boardTile[7][6]).setFieldContent(Tile.KNIGHT_BLACK);
        ((Tile)boardTile[7][7]).setFieldContent(Tile.ROOK_BLACK);
        for (int i = 0; i < 8; i++){
            ((Tile)boardTile[6][i]).setFieldContent(Tile.PAWN_BLACK);
        }
    }
}




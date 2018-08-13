/**
 * This class handles technical side of game - game state. It checks possible moves for selected figure,
 * whose turn is it, etc. Class also contains two dimensional array representing actual game state - for
 * making eventual save games.
 *
 * Created by MK on 2017-06-13.
 */
class GameState {

    private static int[][] tilesState = new int[8][8];

    private static int[][] possibleMoveArray = new int[8][8];

    private static boolean whiteTurn = true;

    /**
     * @return true when it's white's turn.
     */
    static boolean getWhiteTurn(){
        return whiteTurn;
    }

    /**
     * Method is changing value of variable whiteTurn when it's called.
     */
    static void endTurn(){
        whiteTurn = !whiteTurn;
    }

    /**
     * Method is preparing chess board for new game. It puts figures in places and sets variable whiteTurn on true.
     */
    static void newGame () {
        whiteTurn = true;
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                ((Tile)Window.boardTile[i][j]).setFieldContent(Tile.EMPTY);
            }
        }

        ((Tile)Window.boardTile[0][0]).setFieldContent(Tile.ROOK_WHITE);
        ((Tile)Window.boardTile[0][1]).setFieldContent(Tile.KNIGHT_WHITE);
        ((Tile)Window.boardTile[0][2]).setFieldContent(Tile.BISHOP_WHITE);
        ((Tile)Window.boardTile[0][3]).setFieldContent(Tile.KING_WHITE);
        ((Tile)Window.boardTile[0][4]).setFieldContent(Tile.QUEEN_WHITE);
        ((Tile)Window.boardTile[0][5]).setFieldContent(Tile.BISHOP_WHITE);
        ((Tile)Window.boardTile[0][6]).setFieldContent(Tile.KNIGHT_WHITE);
        ((Tile)Window.boardTile[0][7]).setFieldContent(Tile.ROOK_WHITE);
        for (int i = 0; i < 8; i++){
            ((Tile)Window.boardTile[1][i]).setFieldContent(Tile.PAWN_WHITE);
        }

        ((Tile)Window.boardTile[7][0]).setFieldContent(Tile.ROOK_BLACK);
        ((Tile)Window.boardTile[7][1]).setFieldContent(Tile.KNIGHT_BLACK);
        ((Tile)Window.boardTile[7][2]).setFieldContent(Tile.BISHOP_BLACK);
        ((Tile)Window.boardTile[7][3]).setFieldContent(Tile.KING_BLACK);
        ((Tile)Window.boardTile[7][4]).setFieldContent(Tile.QUEEN_BLACK);
        ((Tile)Window.boardTile[7][5]).setFieldContent(Tile.BISHOP_BLACK);
        ((Tile)Window.boardTile[7][6]).setFieldContent(Tile.KNIGHT_BLACK);
        ((Tile)Window.boardTile[7][7]).setFieldContent(Tile.ROOK_BLACK);
        for (int i = 0; i < 8; i++){
            ((Tile)Window.boardTile[6][i]).setFieldContent(Tile.PAWN_BLACK);
        }

        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                Window.boardTile[i][j].repaint();
            }
        }
    }

    /**
     * This method makes update to the array representing actual game state.
     * @param updatedTile
     *          Tile of a chess board which have changed as last one (new position of figure after move).
     */
    static void updateGameState(Tile updatedTile){
        int x = updatedTile.x;
        int y = updatedTile.y;
        tilesState[y][x] = updatedTile.getFieldContent();
    }

    /**
     * This method makes update to the array representing actual game state.
     * @param x
     *          X coordinate of tile which have changed (old position of figure after move).
     * @param y
     *          Y coordinate of tile which have changed (old position of figure after move).
     */
    static void updateGameState(int x, int y){
        ((Tile)Window.boardTile[y][x]).setFieldContent(Tile.EMPTY);
        ((Tile)Window.boardTile[y][x]).isSelected = false;
        Window.boardTile[y][x].repaint();
    }

    /**
     * This method is checking possible moves for selected tile (figure). It fills array of possible moves -
     * possibleMoveArray with '1' if move is possible, and provides graphical representation of it, by repainting
     * tiles for which move is possible.
     * @param selectedTile
     *          Tile of a chess board which has been selected for move.
     * @return boolean
     *          Specifies whether 'check' condition occurs or not.
     */
    static boolean possibleMoves(Tile selectedTile, boolean redraw){
        GameState.possibleMoveArrayErase();
        int x = selectedTile.x;
        int y = selectedTile.y;
        int selectedFieldContent = selectedTile.getFieldContent();
        int colorSpecifier = 0;

        boolean checkCond = false;

        switch(selectedFieldContent){
            case Tile.PAWN_WHITE:
                if(y == 7)
                    break;
                if (((Tile)Window.boardTile[y+1][x]).getFieldContent() == Tile.EMPTY)
                    possibleMoveArray[y+1][x] = 1;
                if (y == 1 && (((Tile)Window.boardTile[y+2][x]).getFieldContent() == Tile.EMPTY)
                        && (((Tile)Window.boardTile[y+1][x]).getFieldContent() == Tile.EMPTY))
                    possibleMoveArray[y+2][x] = 1;
                if (x < 7 && ((Tile)Window.boardTile[y+1][x+1]).getFieldContent() > 10)
                    possibleMoveArray[y+1][x+1] = 1;
                if (x > 0 && ((Tile)Window.boardTile[y+1][x-1]).getFieldContent() > 10)
                    possibleMoveArray[y+1][x-1] = 1;
                break;

            case Tile.ROOK_BLACK:
                colorSpecifier = 10;
            case Tile.ROOK_WHITE:
                for(int i = y+1; i < 8; i++){
                    possibleMoveArray[i][x] = 1;
                    if (tilesState[i][x] != 0) {
                        break;
                    }
                }

                for(int i = y-1; i >= 0; i--){
                    possibleMoveArray[i][x] = 1;
                    if (tilesState[i][x] != 0) {
                        break;
                    }
                }

                for(int i = x+1; i < 8; i++){
                    possibleMoveArray[y][i] = 1;
                    if (tilesState[y][i] != 0) {
                        break;
                    }
                }

                for(int i = x-1; i >= 0; i--){
                    possibleMoveArray[y][i] = 1;
                    if (tilesState[y][i] != 0) {
                        break;
                    }
                }
                break;

            case Tile.KNIGHT_BLACK:
                colorSpecifier = 10;
            case Tile.KNIGHT_WHITE:
                if(y - 1 >= 0) {
                    if (x - 2 >= 0)
                        possibleMoveArray[y - 1][x - 2] = 1;
                    if (x + 2 < 8)
                        possibleMoveArray[y - 1][x + 2] = 1;
                    if(y - 2 >= 0) {
                        if (x - 1 >= 0)
                            possibleMoveArray[y - 2][x - 1] = 1;
                        if (x + 1 < 8)
                            possibleMoveArray[y - 2][x + 1] = 1;
                    }
                }
                if(y + 1 < 8) {
                    if (x - 2 >= 0)
                        possibleMoveArray[y + 1][x - 2] = 1;
                    if (x + 2 < 8)
                        possibleMoveArray[y + 1][x + 2] = 1;
                    if(y + 2 < 8) {
                        if (x + 1 < 8)
                            possibleMoveArray[y + 2][x + 1] = 1;
                        if (x - 1 >= 0)
                            possibleMoveArray[y + 2][x - 1] = 1;
                    }
                }
                break;

            case Tile.BISHOP_BLACK:
                colorSpecifier = 10;
            case Tile.BISHOP_WHITE:
                for(int i = y+1, j = x+1; i < 8 || j < 8; i++, j++){
                    if(i < 8 && j < 8) {
                        possibleMoveArray[i][j] = 1;
                        if (tilesState[i][j] != 0) {
                            break;
                        }
                    }
                }

                for(int i = y-1, j = x-1; i >= 0 || j >= 0; i--, j--){
                    if(i >= 0 && j >= 0) {
                        possibleMoveArray[i][j] = 1;
                        if (tilesState[i][j] != 0) {
                            break;
                        }
                    }
                }

                for(int i = y+1, j = x-1; i < 8  || j >= 0; i++, j--){
                    if(i < 8 && j >= 0) {
                        possibleMoveArray[i][j] = 1;
                        if (tilesState[i][j] != 0) {
                            break;
                        }
                    }
                }

                for(int i = y-1, j = x+1; i >= 0 || j < 8; i--, j++){
                    if(i >= 0 && j < 8) {
                        possibleMoveArray[i][j] = 1;
                        if (tilesState[i][j] != 0) {
                            break;
                        }
                    }
                }
                break;

            case Tile.QUEEN_BLACK:
                colorSpecifier = 10;
            case Tile.QUEEN_WHITE:
                for(int i = y+1, j = x+1; i < 8 || j < 8; i++, j++){
                    if(i < 8 && j < 8) {
                        possibleMoveArray[i][j] = 1;
                        if (tilesState[i][j] != 0) {
                            break;
                        }
                    }
                }

                for(int i = y-1, j = x-1; i >= 0 || j >= 0; i--, j--){
                    if(i >= 0 && j >= 0) {
                        possibleMoveArray[i][j] = 1;
                        if (tilesState[i][j] != 0) {
                            break;
                        }
                    }
                }

                for(int i = y+1, j = x-1; i < 8  || j >= 0; i++, j--){
                    if(i < 8 && j >= 0) {
                        possibleMoveArray[i][j] = 1;
                        if (tilesState[i][j] != 0) {
                            break;
                        }
                    }
                }

                for(int i = y-1, j = x+1; i >= 0 || j < 8; i--, j++){
                    if(i >= 0 && j < 8) {
                        possibleMoveArray[i][j] = 1;
                        if (tilesState[i][j] != 0) {
                            break;
                        }
                    }
                }

                for(int i = y+1; i < 8; i++){
                    if(i < 8) {
                        possibleMoveArray[i][x] = 1;
                        if (tilesState[i][x] != 0) {
                            break;
                        }
                    }
                }

                for(int i = y-1; i >= 0; i--){
                    if(i >= 0) {
                        possibleMoveArray[i][x] = 1;
                        if (tilesState[i][x] != 0) {
                            break;
                        }
                    }
                }

                for(int i = x+1; i < 8; i++){
                    if(i < 8) {
                        possibleMoveArray[y][i] = 1;
                        if (tilesState[y][i] != 0) {
                            break;
                        }
                    }
                }

                for(int i = x-1; i >= 0; i--){
                    if(i >= 0) {
                        possibleMoveArray[y][i] = 1;
                        if (tilesState[y][i] != 0) {
                            break;
                        }
                    }
                }
                break;

            case Tile.KING_BLACK:
                colorSpecifier = 10;
            case Tile.KING_WHITE:
                if(y < 7){
                    possibleMoveArray[y + 1][x] = 1;
                    if(x < 7)
                        possibleMoveArray[y + 1][x + 1] = 1;
                    if(x > 0)
                        possibleMoveArray[y + 1][x - 1] = 1;
                }
                if(x < 7)
                    possibleMoveArray[y][x+1] = 1;
                if(x > 0)
                    possibleMoveArray[y][x-1] = 1;
                if(y > 0){
                    possibleMoveArray[y-1][x] = 1;
                    if(x < 7)
                        possibleMoveArray[y-1][x+1] = 1;
                    if(x > 0)
                        possibleMoveArray[y-1][x-1] = 1;
                }
                break;

            case Tile.PAWN_BLACK:
                colorSpecifier = 10;
                if(y == 0)
                    break;
                if ((((Tile)Window.boardTile[y-1][x]).getFieldContent() == Tile.EMPTY))
                    possibleMoveArray[y-1][x] = 1;
                if (y == 6 && (((Tile)Window.boardTile[y-2][x]).getFieldContent() == Tile.EMPTY)
                        && (((Tile)Window.boardTile[y-1][x]).getFieldContent() == Tile.EMPTY))
                    possibleMoveArray[y-2][x] = 1;
                if (x > 0 && (((Tile)Window.boardTile[y-1][x-1]).getFieldContent() != Tile.EMPTY)
                        && (((Tile)Window.boardTile[y-1][x-1]).getFieldContent() < 10))
                    possibleMoveArray[y-1][x-1] = 1;
                if (x < 7 && (((Tile)Window.boardTile[y-1][x+1]).getFieldContent() != Tile.EMPTY)
                        && (((Tile)Window.boardTile[y-1][x+1]).getFieldContent() < 10))
                    possibleMoveArray[y-1][x+1] = 1;
                break;
        }

        // repaint board and look for check condition
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if (possibleMoveArray[i][j] != 0 && (tilesState[i][j] == 0
                        || tilesState[i][j] >= 11 - colorSpecifier && tilesState[i][j] <= 16 - colorSpecifier)) {
                    ((Tile) Window.boardTile[i][j]).isPossibleToMove = true;
                    if(colorSpecifier == 10 && tilesState[i][j] == Tile.KING_WHITE
                            || colorSpecifier == 0 && tilesState[i][j] == Tile.KING_BLACK)
                        checkCond = true;
                    if(redraw)
                        Window.boardTile[i][j].repaint();
                }
            }
        }
        // TODO: reorganize this method to check whether move will cause check condition even for same color figures
        // IDEA: iterate through board and for each tile with state different than 0 check possible moves for check conditions.
        // Maybe this method should return possibleMoveArray and check cond checking logic should be moved to another method.
        return checkCond;
    }

    /**
     * This method resets array of possible moves and its graphical representation.
     */
    static void resetPossibleMoves(){
        GameState.possibleMoveArrayErase();
        for(int x = 0; x<8; x++){
            for(int y = 0; y<8; y++){
                ((Tile)Window.boardTile[y][x]).isPossibleToMove = false;
                Window.boardTile[y][x].repaint();
            }
        }
    }

    /**
     * This method resets array of possible moves.
     */
    private static void possibleMoveArrayErase(){
        for(int x = 0; x<8; x++){
            for(int y = 0; y<8; y++){
                possibleMoveArray[x][y] = 0;
            }
        }
    }

}

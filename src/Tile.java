import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * This class represents one tile of a chess board as a JPanel, and handles interaction with player
 * within the chess board.
 *
 * Created by MK on 2017-06-10.
 */
public class Tile extends JPanel implements MouseListener, MouseMotionListener{

    final static int EMPTY = 0;
    final static int PAWN_WHITE = 1;
    final static int ROOK_WHITE = 2;
    final static int KNIGHT_WHITE = 3;
    final static int BISHOP_WHITE = 4;
    final static int QUEEN_WHITE = 5;
    final static int KING_WHITE = 6;
    final static int PAWN_BLACK = 11;
    final static int ROOK_BLACK = 12;
    final static int KNIGHT_BLACK = 13;
    final static int BISHOP_BLACK = 14;
    final static int QUEEN_BLACK = 15;
    final static int KING_BLACK = 16;

    int x = 0; // x position on Chess Board
    int y = 0; // y position on Chess Board
    private static int numberOfSelections = 0; // counts number of selected fields (gets only values 0 or 1)
    private boolean isHighlighted = false; // setting state of tile to highlighted (while cursor is within field)
    boolean isSelected = false; // setting state of tile to selected
    boolean isPossibleToMove = false; // setting state of tile to possible to move
    private boolean tileColor = false; // setting a tile color

    private static int selectedX = 100; // x coordinate of selected tile
    private static int selectedY = 100; // y coordinate of selected tile
    private static int selectedFigure = EMPTY; // content of selected tile

    private int fieldContent = EMPTY; // content of tile
    private String imagePath;

    /**
     * Constructor.
     * @param x
     *          X coordinate of tile.
     * @param y
     *          Y coordinate of tile.
     * @param tileColor
     *          Color of tile - true for white, false for black.
     */
    Tile(int x, int y, boolean tileColor){
        setPreferredSize(new Dimension(50, 50));
        addMouseListener(this);
        addMouseMotionListener(this);
        this.x = x;
        this.y = y;
        this.tileColor = tileColor;
        GameState.updateGameState(this);
    }

    /**
     * This method changes content of a tile(field) to a value given as a parameter. It also ties different
     * path to String imagePath to load proper image for further graphic representation of figures.
     * @param newContent
     *              Variable representing field(tile) content. Must be value within 0-6 or 11-16.
     */
    void setFieldContent(int newContent){
        fieldContent = newContent;
        switch(fieldContent){
            case PAWN_WHITE:
                imagePath = ".\\src\\images\\Pawn_white.png";
                break;
            case ROOK_WHITE:
                imagePath = ".\\src\\images\\Rook_white.png";
                break;
            case KNIGHT_WHITE:
                imagePath = ".\\src\\images\\Knight_white.png";
                break;
            case BISHOP_WHITE:
                imagePath = ".\\src\\images\\Bishop_white.png";
                break;
            case QUEEN_WHITE:
                imagePath = ".\\src\\images\\Queen_white.png";
                break;
            case KING_WHITE:
                imagePath = ".\\src\\images\\King_white.png";
                break;
            case PAWN_BLACK:
                imagePath = ".\\src\\images\\Pawn_black.png";
                break;
            case ROOK_BLACK:
                imagePath = ".\\src\\images\\Rook_black.png";
                break;
            case KNIGHT_BLACK:
                imagePath = ".\\src\\images\\Knight_black.png";
                break;
            case BISHOP_BLACK:
                imagePath = ".\\src\\images\\Bishop_black.png";
                break;
            case QUEEN_BLACK:
                imagePath = ".\\src\\images\\Queen_black.png";
                break;
            case KING_BLACK:
                imagePath = ".\\src\\images\\King_black.png";
                break;
            default:
                imagePath = "";
                break;
        }
        GameState.updateGameState(this);
    }

    /**
     * @return content of a tile(field).
     */
    int getFieldContent() {
        return fieldContent;
    }

    /**
     * This method handles mouse dragging within tile.
     * @param mouseEvent
     *          mouse event
     */
    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
    }

    /**
     * This method handles mouse moving within tile.
     * @param mouseEvent
     *          mouse event
     */
    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
    }

    /**
     * This method handles mouse clicking within tile.
     * @param mouseEvent
     *          mouse event
     */
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
    }

    /**
     * This method handles pressing mouse button within tile.
     * @param mouseEvent
     *          mouse event
     */
    @Override
    public void mousePressed(MouseEvent mouseEvent) {
    }

    /**
     * This method handles mouse button releasing within tile. It checks selecting and moving possibilities.
     * @param mouseEvent
     *          mouse event
     */
    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if ((isSelected)) {
            isSelected = false;
            numberOfSelections -= 1;
            GameState.resetPossibleMoves();
            repaint();
        } else if ((numberOfSelections == 0)&&(fieldContent != EMPTY)&&((fieldContent < 10)
                &&(GameState.getWhiteTurn())||(fieldContent > 10)&&(!GameState.getWhiteTurn()))) {
            isSelected = true;
            numberOfSelections += 1;
            repaint();
            selectedFigure = getFieldContent();
            selectedX = x;
            selectedY = y;
            GameState.possibleMoves(this, true);
        } else if ((numberOfSelections == 1)&&(isPossibleToMove)){
            numberOfSelections -= 1;
            this.setFieldContent(selectedFigure);
            GameState.updateGameState(this);
            GameState.updateGameState(selectedX,selectedY);
            repaint();
            if(GameState.possibleMoves(this, false)) {
                JOptionPane jOptionPane = new JOptionPane("CHECK!", JOptionPane.INFORMATION_MESSAGE);
                JDialog dialog = jOptionPane.createDialog(this.getParent(), "Chess");
                dialog.setSize(120, 120);
                dialog.setModal(true);
                dialog.setLocationRelativeTo(this.getParent());
                dialog.setVisible(true);
            }
            GameState.resetPossibleMoves();
            GameState.endTurn();
        }
    }

    /**
     * This method handles actions related with mouse entering tile. It is setting variable isHighlighted
     * to true for providing graphical representation.
     * @param mouseEvent
     *          mouse event
     */
    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
        isHighlighted = true;
        repaint();
    }

    /**
     * This method handles actions related with mouse exiting tile. It is setting variable isHighlighted
     * to false for providing graphical representation.
     * @param mouseEvent
     *          mouse event
     */
    @Override
    public void mouseExited(MouseEvent mouseEvent) {
        isHighlighted = false;
        repaint();
    }

    /**
     * This method provides graphical representation of chess board tiles. It draws tiles in right position,
     * size and state - highlighted, selected, or possible to move. It loads also images from path, set before
     * in method setFieldContent.
     * @param g
     *          graphics
     */
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        //Drawing a one Tile of a Chess Board
        Rectangle2D field = new Rectangle2D.Double();
        setLocation(x*50+50,y*50+90);
        field.setRect(0,0,50,50);
        if (tileColor){
            g2d.setColor(Color.darkGray);
        }
        if (!tileColor){
            g2d.setColor(Color.white);
        }
        g2d.fill(field);

        if (isSelected) {
            g2d.setColor(Color.RED);
        }
        if (isHighlighted) {
            g2d.setColor(Color.CYAN);
        }
        if (isPossibleToMove) {
            g2d.setColor(Color.GREEN);
        }
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(5,5,40,40);

        // Image loading
        if (fieldContent != EMPTY){
            try{
                File imageFile = new File(imagePath);
                BufferedImage image = ImageIO.read(imageFile);
                g2d.drawImage(image,0,0, 50,50,this);
            }
            catch(IOException ie)
            {
                System.out.println(ie.getMessage());
            }
        }
    }
}

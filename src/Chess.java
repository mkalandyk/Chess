import java.awt.*;

/**
 * Main class.
 */
public class Chess {
    public static void main(String[] args){
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Window();
            }
        });
    }
}

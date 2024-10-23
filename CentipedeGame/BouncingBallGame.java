import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.*;

public class BouncingBallGame extends JFrame {
    public BouncingBallGame() {
        setTitle("Centipede Game");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        GamePanel panel = new GamePanel();
        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        new BouncingBallGame();
    }
}

class GamePanel extends JPanel implements ActionListener, KeyListener {
    private Timer timer;
    private Player player;
    private ArrayList<Bullet> bullets;
    private Centipede centipede;

    public GamePanel() {
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        player = new Player(400, 500);
        bullets = new ArrayList<>();
        centipede = new Centipede();

        timer = new Timer(30, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        player.draw(g);
        centipede.draw(g);

        for (Bullet bullet : bullets) {
            bullet.draw(g);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        player.update();
        centipede.update();

        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            bullet.update();

            if (bullet.getY() < 0) {
                bullets.remove(i);
            }
        }

        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            player.moveLeft();
        }
        if (key == KeyEvent.VK_RIGHT) {
            player.moveRight();
        }
        if (key == KeyEvent.VK_SPACE) {
            bullets.add(new Bullet(player.getX() + 15, player.getY()));
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
            player.stop();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}

class Player {
    private int x, y, dx;
    private final int WIDTH = 30, HEIGHT = 10;
    

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        this.dx = 0;
    }

    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(x, y, WIDTH, HEIGHT);
    }

    public void moveLeft() {
        dx = -5;
    }

    public void moveRight() {
        dx = 5;
    }

    public void stop() {
        dx = 0;
    }

    public void update() {
        x += dx;
        if (x < 0) x = 0;
        if (x > 770) x = 770;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

class Bullet {
    private int x, y;
    private final int WIDTH = 5, HEIGHT = 10;
    private final int SPEED = 5;

    public Bullet(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, WIDTH, HEIGHT);
    }

    public void update() {
        y -= SPEED;
    }

    public int getY() {
        return y;
    }
}

class Centipede {
    private ArrayList<Segment> segments;

    public Centipede() {
        segments = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            segments.add(new Segment(50 + i * 20, 50));
        }
    }

    public void draw(Graphics g) {
        for (Segment segment : segments) {
            segment.draw(g);
        }
    }

    public void update() {
        for (Segment segment : segments) {
            segment.update();
        }
    }
}

class Segment {
    private int x, y;
    private int dx = 5;

    public Segment(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillRect(x, y, 20, 20);
    }

    public void update() {
        x += dx;
        if (x < 0 || x > 780) {
            dx = -dx;
            y += 20;
        }
    }
}


import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class MapObject {

    private AnimationTimer timer1;
    private AnimationTimer timer2;
    private int velocity = 10;
    private int score;
    private int level;
    private int passedCar;

    private Line line;
    private Line line2;
    private Line line3;

    private Rectangle r;
    private Rectangle r2;

    private Rectangle enemy;
    private Rectangle enemy2;

    private Rectangle myCar;

    private Circle c;//lawns
    private Circle c2;
    private Circle c3;

    private Group root;
    private Text Score, Level;
    private boolean gameRunning = false;
    private int x = 120, y = 30;
    private Text gameOverText;

    public MapObject() {
        setup();
    }

    public void setup() {//this loads map elements

        setupMycar();
        setupEnemy(x, y);
        setupLawns();//lawns and roads
        root = new Group();
        root.getChildren().addAll(line, line2, line3, r, r2, c, c2, c3, myCar);
        root.getChildren().addAll(enemy, enemy2);
    }

    public Group getRoot() {
        displayScoreLevel();
        return this.root;
    }

    public boolean isRunning() {
        return gameRunning;
    }

    public int getInitialVelocity() {
        return 10;
    }

    public void setupLawns() {
        //Lawns
        r = new Rectangle(0, 0, 50, 500);
        r.setStroke(Color.GREENYELLOW);
        r.setStrokeWidth(100);

        r2 = new Rectangle(550, 0, 600, 500);
        r2.setStroke(Color.GREENYELLOW);
        r2.setStrokeWidth(100);

        //Lines
        line3 = new Line(500, 0, 500, 500);
        //Road coordinates between x(100-500)-y(0-500) 
        line3.setStrokeWidth(10);
        line2 = new Line(300, 0, 300, 500);
        line2.setStroke(Color.BLACK);
        line2.setStrokeWidth(10);
        line2.setStrokeLineCap(StrokeLineCap.BUTT);
        line2.getStrokeDashArray().addAll(20d, 40d);
        line2.setStrokeDashOffset(0);
        line = new Line(100, 0, 100, 500);
        line.setStrokeWidth(10);

        //Tress
        c = new Circle(25, 50, 30, Color.GREEN);
        c.setStrokeWidth(10);

        c2 = new Circle(45, 400, 35, Color.GREEN);
        c2.setStrokeWidth(10);

        c3 = new Circle(575, 155, 30, Color.GREEN);
        c3.setStrokeWidth(10);
    }

    public void setupMycar() {
        gameRunning = true;
        score = 0;
        level = 1;
        passedCar = 0;
        myCar = new Rectangle(250, 325, 40, 70);
        myCar.setStroke(Color.RED);
        myCar.setStrokeWidth(15);
    }

    public void setupEnemy(int x, int y) {
        enemy = new Rectangle(x, 30, 40, 70);
        enemy.setStroke(Color.YELLOW);
        enemy.setStrokeWidth(15);

        enemy2 = new Rectangle(x + 150, -230, 40, 70);
        enemy2.setStrokeWidth(15);
        enemy2.setStroke(Color.YELLOW);

    }

    public void move() {
        line2.setStrokeDashOffset(line2.getStrokeDashOffset() - velocity);
        Score.setText("Score: " + score);
        Level.setText("Level: " + level);

        if (c2.getLayoutY() + c2.getRadius() >= 200) {
            c2.setLayoutY(-500);
        } else {
            c2.setLayoutY(c2.getLayoutY() + velocity);
        }
        if (c.getLayoutY() + c.getRadius() >= 450) {
            c.setLayoutY(-500);
        } else {
            c.setLayoutY(c.getLayoutY() + velocity);
        }
        if (c3.getLayoutY() + c3.getRadius() >= 350) {
            c3.setLayoutY(-300);
        } else {
            c3.setLayoutY(c3.getLayoutY() + velocity);
        }
        controlCollide();//car collide

    }

    public void controlCollide() {
        int a = (int) (Math.random() * 340 + 110);
        int b = (int) (Math.random() - 100);
        if (enemy.getLayoutY() > 650) {//if cars passed
            root.getChildren().remove(enemy);

            enemy = new Rectangle(a, b, 40, 70);
            enemy.setStroke(Color.YELLOW);
            enemy.setStrokeWidth(15);
            root.getChildren().add(enemy);
            if (passedCar == 5) {
                level++;
                passedCar = 0;
            } else {
                passedCar++;
            }
            score = score + level;

        } else {
            enemy.setLayoutY(enemy.getLayoutY() + velocity);
            
            if (enemy.getLayoutY() + enemy.getY() > myCar.getY()) {
                enemy.setStroke(Color.GREEN);
            }
        }

        if (enemy2.getLayoutY() > 650) {//if cars passed
            root.getChildren().remove(enemy2);
            int a2 = (int) (Math.random() * 150);
            enemy2 = new Rectangle((a + a2) % 340 + 110, b - 100, 40, 70);
            enemy2.setStroke(Color.YELLOW);
            enemy2.setStrokeWidth(15);
            root.getChildren().add(enemy2);
            if (passedCar == 5) {
                level++;
                passedCar = 0;
            } else {
                passedCar++;
            }
            score = score + level;
        } else {
            enemy2.setLayoutY(enemy2.getLayoutY() + velocity);
            
            if (enemy2.getLayoutY() + enemy2.getY() > myCar.getY()) {
                enemy2.setStroke(Color.GREEN);
            }
        }collision();
    }

    public void collision() {
        if (myCar.getBoundsInParent().intersects(enemy.getBoundsInParent())) {//if cars collide
            enemy.setStroke(Color.BLACK);
            myCar.setStroke(Color.BLACK);//if collide
            gameRunning = false;
            timer1.stop();
            timer2.stop();
            GameOverText();
        }
        else if (myCar.getBoundsInParent().intersects(enemy2.getBoundsInParent())) {//if cars collide
            enemy2.setStroke(Color.BLACK);
            myCar.setStroke(Color.BLACK);//if collide
            gameRunning = false;
            timer1.stop();
            timer2.stop();
            GameOverText();
        }
    }

    public void restart(ArrayList<String> input, ArrayList<String> input2) {

        root.getChildren().clear();
        setupLawns();
        setupEnemy(x, y);
        setupMycar();

        gameRunning = true;
        root.getChildren().addAll(line, line2, line3, r, r2, c, c2, c3);
        root.getChildren().addAll(enemy, enemy2, myCar);
        Animation(input, input2);
        displayScoreLevel();
    }

    public void Animation(ArrayList<String> input, ArrayList<String> input2) {

        timer1 = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (input2 != null) {
                    if (input2.contains("UP")) {
                        if (now - lastUpdate > 20000000) {
                            if (velocity >= 0) {
                                velocity--;
                                move();
                            }
                            lastUpdate = now;
                        }
                    }
                }

            }
        };
        timer1.start();

        timer2 = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (null != input) {
                    if (input.contains("UP")) {
                        velocity = getInitialVelocity() + level;
                        move();
                    }

                    if (input.contains("LEFT")) {
                        if (myCar.getX() > 110) {
                            myCar.setX(myCar.getX() - 10);
                        } else {
                            myCar.setX(110);
                        }collision();

                    }

                    if (input.contains("RIGHT")) {
                        if (myCar.getX() < 450) {
                            myCar.setX(myCar.getX() + 10);
                        } else {
                            myCar.setX(450);
                        }collision();
                    }
                }

            }
        };
        timer2.start();
    }

    public void displayScoreLevel() {

        Score = new Text(25, 25, "Score: " + score);
        Score.setFill(Color.BLACK);
        Level = new Text(25, 40, "Level: " + level);
        Score.setFill(Color.BLACK);
        root.getChildren().addAll(Score, Level);
    }

    public void GameOverText() {
        gameOverText = new Text(140, 200, "\tGAME OVER!\n\tYour Score: " + score + "\nPress ENTER to restart!");
        gameOverText.setFill(Color.WHITE);
        gameOverText.setFont(new Font(35));
        root.getChildren().add(gameOverText);
    }

}

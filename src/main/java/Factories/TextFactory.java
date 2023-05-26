package Factories;

import Domain.Message;
import Utils.Tuple;
import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import static java.lang.Math.min;

public class TextFactory {

    private double lastPosition = 5;

    private TextFactory(){

    }

    public static TextFactory getInstance(){
        return new TextFactory();
    }

    //To create rectangles and possition them properly on the Y Axis take the Y of the previous message add height and then add 15
    //Also, start at Y = 5
    //To position rectangles properly on the X axis it's X = 1 for the received messages and X = 80 for the sent messages
    //Pentru text cu rectangle iei height de la text il pui la rectangle si adaugi 5, sa tii cont de asta si la calcul
    //Cand o sa adaugi mai multe texte cu rectangleuri


    public Tuple<Rectangle, Text> createChat(Message givenMessage, String sender){
        String text1 = givenMessage.getMessage();
        Rectangle rectangle = new Rectangle();
        rectangle.setArcHeight(10);
        rectangle.setArcWidth(10);
        rectangle.setY(lastPosition);
        Color paint = new Color(0.1176, 0.5647, 1.0, 1.0);
        rectangle.setFill(paint);
        rectangle.setSmooth(true);
        Text message1 = new Text();
        message1.setText(text1);


        double minimum = min(message1.getBoundsInLocal().getWidth(), 150);

        if(!givenMessage.getSender().equals(sender)) {
            message1.setX(3);
            rectangle.setX(1);
        }
        else {
         message1.setX(262 - minimum);
         rectangle.setX(265 - minimum - 5);
        }

        rectangle.setWidth(minimum + 5);
        message1.setWrappingWidth(minimum);
        rectangle.setHeight(message1.getBoundsInLocal().getHeight() + 5);

        message1.setY(rectangle.getY() + 15);
        Color textPaint = new Color(0.0, 0.0, 0.0, 1.0);
        message1.setFill(textPaint);
        lastPosition = lastPosition + rectangle.getHeight() + 10;



        return new Tuple<>(rectangle, message1);
    }

}

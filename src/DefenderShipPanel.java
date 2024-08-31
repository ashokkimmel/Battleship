import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DefenderShipPanel extends JPanel {
    private ArrayList<Ship> myships;
    private ArrayList<Attack> myattacks;
    public DefenderShipPanel(ArrayList<Attack> a, ArrayList<Ship> s) {
        myships = s;
        myattacks = a;
        setBounds(0, 0, 500,500);
        setOpaque(false);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        myships.forEach((n) -> paintship(n, g2d));
        paintBlackCircles(g2d);
        myattacks.forEach((n) -> paintattack(n, g2d));
    }
    protected void paintBlackCircles(Graphics2D g2d){
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 11; y++) {
                g2d.setColor(Color.BLACK);
                g2d.fillOval(x *50 +10,y*50 +10,30,30);
            }
        }
    }

    protected void paintattack(Attack tattack, Graphics2D g2d){
        if (tattack.hit) g2d.setColor(Color.RED);
        else g2d.setColor(Color.GRAY);
        g2d.fillOval(tattack.x*50 + 20, tattack.y*50 + 20, 10,10);
    }
    protected void paintship(Ship tship, Graphics2D g2d) {
        g2d.setColor(Color.GRAY);
        int longside = tship.ogposns.size() * 50;
        if (tship.round) {
            g2d.fillOval(tship.ogposns.getFirst().x*50, tship.ogposns.getFirst().y*50, tship.vert?50:longside, tship.vert?longside:50);
        } else {
            g2d.fillRect(tship.ogposns.getFirst().x*50, tship.ogposns.getFirst().y*50, tship.vert?50:longside, tship.vert?longside:50);
        }
    }
}
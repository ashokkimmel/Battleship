import java.awt.*;
import java.util.ArrayList;

public class AttackerShipPanel extends DefenderShipPanel {
    public AttackerShipPanel(ArrayList<Attack> a, ArrayList<Ship> s) {
        super(a,s);
    }
    @Override
    protected void paintship(Ship tship, Graphics2D g2d) {
        if (!tship.sunk) return;
        super.paintship(tship,g2d);
    }
}

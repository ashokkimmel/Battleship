import java.util.ArrayList;
public class Ship {
    public boolean vert;// vertical?
    public boolean sunk;//
    public boolean round;
    public final ArrayList<Posn> ogposns;
    public ArrayList<Posn> cuposns;


    public Ship(ArrayList<Posn> poray, boolean vertical,boolean rounded) {
        cuposns = (ArrayList<Posn>) poray.clone();
        ogposns = poray;
        vert = vertical;
        sunk = false;
        round = rounded;
    }
    public boolean attackhit(Posn theirattack) {
        for (Posn pos : cuposns) {
            if (theirattack.x == pos.x && theirattack.y == pos.y) {
                cuposns.remove(pos);
                return true;
            }
        }
        return false;
    }
}

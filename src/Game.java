import java.util.ArrayList;
import java.lang.Exception;

public class Game {
    private boolean pl1w; //player 1 win?
    private boolean pl1t; //player 1 turn?
    public ArrayList<Attack> pl1atk; // player 1 attacks
    public ArrayList<Attack> pl2atk;// player 2 attacks
    public ArrayList<Ship> pl1s;//player 1 ships
    public ArrayList<Ship> pl2s;//player 2 ships


    public boolean isPl1w() {
        return pl1w;
    }

    public ArrayList<Attack> getpl1atk() {
        return pl1atk;
    }

    public ArrayList<Attack> getPl2atk() {
        return pl2atk;
    }

    public ArrayList<Ship> getPl1s() {
        return pl1s;
    }

    public ArrayList<Ship> getPl2s() {
        return pl2s;
    }

    public boolean isPl1t() {
        return pl1t;
    }

    public Game() {

        pl1w = false;
        pl1t = true;
        ArrayList<Posn> myar;

        myar = new ArrayList<>();
        myar.add(new Posn(9,0));
        Ship myship1 = new Ship(myar, true, true);

        myar = new ArrayList<>();
        myar.add(new Posn(8,2));
        Ship myship2 = new Ship(myar, true, true);

        myar = new ArrayList<>();
        myar.add(new Posn(1,6));
        Ship myship3 = new Ship(myar, true, true);

        myar = new ArrayList<>();
        myar.add(new Posn(3, 4));
        myar.add(new Posn(3, 5));
        myar.add(new Posn(3, 6));
        myar.add(new Posn(3, 7));


        Ship myship4 = new Ship(myar, true, true);

        myar = new ArrayList<>();
        myar.add(new Posn(5,7));
        Ship myship5 = new Ship(myar, true, true);
        myar = new ArrayList<>();
        myar.add(new Posn(1,2));
        pl1atk = new ArrayList<>(); // player 1 attacks
        pl2atk = new ArrayList<>(); // player 1 attacks
        pl1s = new ArrayList<>(); // player 1 attacks
        pl2s = new ArrayList<>(); // player 1 attacks
        pl1s.add(myship1);
        pl1s.add(myship2);
        pl1s.add(myship3);
        pl1s.add(myship4);
        pl1s.add(myship5);
        Ship myoship = new Ship(myar, true, true);
        pl2s.add(myoship);
    }
    public boolean attack(Posn attacklocation,boolean bpl1t) throws Exception{
        if (bpl1t != pl1t) {
            Exception myexeption =  new Exception("It is not your turn");
            throw myexeption;
        } else if ((pl1t?pl1atk:pl2atk).contains(new Attack(attacklocation,true)) || (pl1t?pl1atk:pl2atk).contains(new Attack(attacklocation,false)))  {
            Exception myexeption =  new Exception("You already attacked at " + attacklocation.x + ", "+attacklocation.y+".");
            throw myexeption;
        }
        System.out.println("You just attacked at " + attacklocation.x + ", "+attacklocation.y+".");

        return unsafeattack(attacklocation);

    }

    private boolean unsafeattack(Posn attacklocation){
        boolean attackhit = false;
        boolean allsunk = true;
        for (Ship cuship :(pl1t?pl2s:pl1s)) {
            if (!attackhit && cuship.attackhit(attacklocation)) {
                attackhit = true;
                if (cuship.cuposns.isEmpty()) {
                    cuship.sunk = true;
                    System.out.println("A ship should appear now!");
                }
            }
            allsunk = allsunk && cuship.sunk;
            if (pl1t){
                pl1w = allsunk;
            }
        }
        System.out.println(attackhit);
        (pl1t?pl1atk:pl2atk).add(new Attack(attacklocation, attackhit));
        pl1t = pl1t == false;
    return allsunk;
    }

}

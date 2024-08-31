public class Attack extends Posn{
    public boolean hit;

    public Attack(int a, int b, boolean c) {
        super(a,b);
        this.hit = c;
    }

    public Attack(Posn p, boolean c) {
        super(p);
        this.hit = c;
    }


}
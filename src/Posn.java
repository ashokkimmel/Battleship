public class Posn {
    public int x;
    public int y;
    public Posn(int a, int b) {
        this.x = a;
        this.y = b;
    }
    public Posn(Posn myp) {
        this.x = myp.x;
        this.y = myp.y;
    }
}

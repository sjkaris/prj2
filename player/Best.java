package player;

public class Best{

    protected double score;
    protected Move move;

    public Best(){
        move = new Move();
        this.score = 0;
    }

    public Best(double score){
        move = new Move();
        this.score = score;
    }

    public Best(double score, Move move){
        this(score);
        this.move = move;
    }
}

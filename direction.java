package lab01;

// indica a direção da palavra
public enum direction {

    UP(-1, 0), // SOBE UMA LINHA (DX = -1)
    DOWN(1, 0), // DESCE UMA LINHA (DX = +1)
    LEFT(0, -1), // VAI UMA COLUNA PARA A ESQUERDA (DY = -1)
    RIGHT(0, 1), // VAI UMA COLUNA PARA A DIREITA (DY = 1)
    UP_LEFT(-1, -1), // SOBE UMA LINHA E VAI UMA COLUNA PARA A ESQUERDA (DX = -1, DY = -1) 
    UP_RIGHT(-1, 1),    // SOBE UMA LINHA E VAI UMA COLUNA PARA A DIREITA (DX = -1, DY = +1)
    DOWN_LEFT(1, -1), // DESCE UMA LINHA E VAI UMA COLUNA PARA A ESQUERDA (DX = +1, DY = -1) 
    DOWN_RIGHT(1, 1); // DESCE UMA LINHA E VAI UMA COLUNA PARA A DIREITA (DX = +1, DY = +1)

    

    private final int dx;
    private final int dy;

    direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public int getDx() {
        return dx;
    }
    public int getDy() {
        return dy;
    }
    
}

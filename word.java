package lab01;

// representa uma palavra na sopa de letras

public class word {
    private String text; // texto da palavra
    private int length; // tamanho da palavra
    private int startRow; // posição inicial da palavra na sopa numa linha
    private int startCol; // posição inicial da palavra na sopa numa coluna
    private direction dir; // direção da palavra

    // construtor
    public word(String text) {
        // transforma o texto para maiúsculas para facilitar a comparação
        this.text = text.toUpperCase();
        this.length = this.text.length();
        this.startRow = -1; // posição inicial não definida
        this.startCol = -1; // posição inicial não definida
        this.dir = null; // direção não definida
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getStartCol() {
        return startCol;
    }

    public void setStartCol(int startCol) {
        this.startCol = startCol;
    }

    public direction getDir() {
        return dir;
    }

    public void setDir(direction dir) {
        this.dir = dir;
    }

    @Override
    public String toString() {
        return text + " (" + startRow + "," + startCol + ") " + dir;
    }

    
    

}

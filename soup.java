package lab01;

// representa a sopa de letras

public class soup {
    private char[][] grid; // representar a sopa de letras numa matriz
    private int size; // tamanho da sopa (n x n)

    public soup(char[][] grid) {
        this.size = grid.length; // verificar se é quadrada
        this.grid = new char[size][size]; // inicializar a matriz para a sopa

        // para garantir que a sopa/matriz tenha maiusculas
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.grid[i][j] = Character.toUpperCase(grid[i][j]);
            }
        }
    }


    public int getSize() {
        return size;
    }

    public char[][] getGrid() {
        return grid;
    }

    public char getLetter(int row, int col) {
        return grid[row][col];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                sb.append(grid[i][j]).append(' ');
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    
}

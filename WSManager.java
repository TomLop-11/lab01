package lab01;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WSManager {
    private List<word> words; // lista de palavras a procurar
    private soup s; // sopa de letras

    public WSManager() {
        this.words = new ArrayList<>();
        this.s = null;
    }

    // getters
    public List<word> getWords() {
        return words;
    }
    public soup getS() {
        return s;
    }

    // ler o ficheiro
    public void readfile(String file) {
        Scanner sc = null;
        try {
            sc = new Scanner(new File(file));

            List<String> matriz = new ArrayList<>(); // lista para adicionar as letras da sopa
            List<String> wordlist = new ArrayList<>(); // lista para adicionar as palavras a procurar
            boolean foundwordlist = false; // indica se já encontrou a lista de palavras

            while(sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) {
                    continue; 
                }

                if (line.equals(line.toUpperCase()) && !foundwordlist) {
                    // se a linha for maiúscula e ainda não encontrou a lista de palavras
                    if (line.length() <= 40) {

                        matriz.add(line.replaceAll("[^A-Z]", "")); // adicionar a linha à matriz
                    } else {
                        return;
                    }
                } else {
                    foundwordlist = true; // encontrou a lista de palavras
                    String[] parts = line.split("[,\\s;]+"); // dividir a linha por vírgulas ou espaços

                    for (String part : parts) {
                        if (!part.isEmpty() && part.matches("[A-Za-z]+")) { // verifica se a linha é constituída por carateres alfabéticos
                            wordlist.add(part);
                        }
                    }
                
                }
            }

            if (!matriz.isEmpty()) {
                // converter a lista de strings para uma matriz de caracteres
                int size = matriz.size();

                // verificar se a matriz é quadrada
                for (String row : matriz) {
                    if (row.length() != size) {
                        
                        return;
                    }
                }

                char[][] grid = new char[size][size];

                for (int i = 0; i < size; i++) {
                    String row = matriz.get(i);
                    for (int j = 0; j < size; j++) {
                        grid[i][j] = row.charAt(j);
                    }
                }

                s = new soup(grid); // criar a sopa de letras
            }

            for (String w : wordlist) {
                words.add(new word(w)); 
            }

        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            if (sc != null) {
                sc.close();
            }
        }
    }

    //encontrar as palavras na sopa de letras
    public void findWords() {
        if (s == null || words.isEmpty()) {
            return;
        }
    
        int size = s.getSize();
        char[][] grid = s.getGrid();

        for (word w : words) {
            String text = w.getText();
            int len = w.getLength();
            boolean found = false;

            for (int i = 0; i< size; i++) {
                for (int j = 0; j<size; j++) {
                    for(direction dir : direction.values()) {
                        // palavra encontrada APENAS 1 VEZ
                        if (found) {
                            break;
                        }
                        int dx = dir.getDx();
                        int dy = dir.getDy();

                        // row = i, col = j
                        int x = i;
                        int y = j;

                        int k;

                        // se estiver dentro dos limites da sopa
                        for(k = 0; k < len; k++) {
                            if (x >= 0 && x < size && y >= 0 && y < size) {
                                if (grid[x][y] != text.charAt(k)) {
                                    break;
                                }
                                x += dx;
                                y += dy;
                            } else {
                                break;
                            }
                        }
                        if (k == len) {
                            w.setStartRow(i);
                            w.setStartCol(j);
                            w.setDir(dir);
                            found = true; // palavra encontrada
                            break;
                        }
                    }
                }
            }
            // se não for encontrada
            if (found == false) {
                w.setStartRow(-1);
                w.setStartCol(-1);
                w.setDir(null); 
            }
        }
    }

    // saida
    public void writefile(String filename) {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new File(filename));
            for (word w : words) {
                if (w.getStartRow()!=-1 && w.getDir() != null) {
                    pw.printf("%s %d %d,%d %s \n", w.getText().toLowerCase(), w.getLength(), w.getStartRow()+1, w.getStartCol()+1, w.getDir());
                } else {
                    pw.printf("%s - NOT FOUND \n", w.getText().toLowerCase());
                }
            }
            int tamanho = s.getSize();
            char[][] sopa = s.getGrid();
            char[][] mostrarsopa = new char[tamanho][tamanho];

            for (int i = 0; i < tamanho; i++) {
                for (int j = 0; j < tamanho; j++) {
                    mostrarsopa[i][j] = '.';
                }
            }

            for (word w1 : words) {
                if (w1.getStartRow()!=-1 && w1.getDir() != null) {
                    int row = w1.getStartRow();
                    int col = w1.getStartCol();
                    direction dir = w1.getDir();
                    String text = w1.getText();
        
                    // adiciona-se as letras à medida que o k aumenta
                    for (int k = 0; k < text.length(); k++){
                        int actrow = row + k*dir.getDx();
                        int actcol = col + k*dir.getDy();
        
                        if (actrow >= 0 && actcol >= 0 && actcol <tamanho && actrow < tamanho) {
                            mostrarsopa[actrow][actcol] = sopa[actrow][actcol];
                        }
                    }
                }
            }

            for (int r = 0; r < tamanho; r++) {
                for (int c = 0; c < tamanho; c++) {
                    pw.print(mostrarsopa[r][c] + " ");
                }
                pw.println();
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            if(pw != null) {
                pw.close();
            }
        }
    }

    // resultados
    public void printresult() {
        for (word w : words) {
            if (w.getStartRow()!=-1 && w.getDir() != null) {
                System.out.printf("%s %d %d,%d %s \n", w.getText().toLowerCase(), w.getLength(), w.getStartRow()+1, w.getStartCol()+1, w.getDir());
            } else {
                System.out.printf("%s - NOT FOUND \n", w.getText().toLowerCase());
            }
        }

        int tamanho = s.getSize();
        char[][] sopa = s.getGrid();
        char[][] mostrarsopa = new char[tamanho][tamanho];

        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                mostrarsopa[i][j] = '.';
            }
        }

        for (word w1 : words) {
            if (w1.getStartRow()!=-1 && w1.getDir() != null) {
                int row = w1.getStartRow();
                int col = w1.getStartCol();
                direction dir = w1.getDir();
                String text = w1.getText();
    
                // adiciona-se as letras à medida que o k aumenta
                for (int k = 0; k < text.length(); k++){
                    int actrow = row + k*dir.getDx();
                    int actcol = col + k*dir.getDy();
    
                    if (actrow >= 0 && actcol >= 0 && actcol <tamanho && actrow < tamanho) {
                        mostrarsopa[actrow][actcol] = sopa[actrow][actcol];
                    }
                }
            }
        }

        for (int r = 0; r < tamanho; r++) {
            for (int c = 0; c < tamanho; c++) {
                System.out.print(mostrarsopa[r][c] + " ");
            }
            System.out.println();
        }

    }


    
}

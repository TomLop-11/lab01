package lab01;


import java.io.*;
import java.util.*;

public class WSGenerator {
    private List<String> words; // usar string para facilitar a comparação e para verificar os requisitos
    private char[][] puzzle;
    private int dim;
    private Random random;

    public WSGenerator() {
        this.words = new ArrayList<>();
        this.puzzle = null;
        this.dim = 0;
        this.random = new Random();
    }

    public void gerarsopa(String file, int dim, String outfile) {
        try {
            // lê a lista de palavras
            readlist(file);


            // gera a sopa com o tamanho pretendido
            this.dim = dim;
            gerarmatriz();


            // escreve no ficheiro de saída
            writefile(outfile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void readlist(String file) {
        Scanner sc = null;
        try {
            sc = new Scanner(new File(file));
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }
                String[] parts = line.split("[,\\s;]+");

                for (String part : parts) {
                    if (!part.isEmpty() && part.matches("[A-Za-z]+")) { // verifica se a linha é constituída por carateres alfabéticos
                        words.add(part.toUpperCase());
                    }
                }
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

    private void gerarmatriz() {
        puzzle = new char[dim][dim];

        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                puzzle[i][j] = '.';
            }
        }
        direction[] alldir = direction.values();

        for (String w : words) {
            boolean colocado = false;
            int tentativas = 0;
            int maxtent = 100;
            
            while (!colocado && tentativas < maxtent) {
                tentativas++;
                int row = random.nextInt(dim);
                int col = random.nextInt(dim);
                direction dir = alldir[random.nextInt(alldir.length)];

                // verifica os limites
                int len = w.length();
                int frow = row + (len-1)*dir.getDx();
                int fcol = col + (len-1)*dir.getDy();

                if (frow < 0 || fcol < 0 || frow >= dim || fcol >= dim) {
                    continue;
                }

                // verificar os lugares
                boolean disponivel = true;
                for (int i = 0; i < len; i++) {
                    int actrow = row + i*dir.getDx();
                    int actcol = col + i*dir.getDy();
                    char actchar = puzzle[actrow][actcol];
                    if (actchar != '.' && actchar != w.charAt(i)) {
                        disponivel = false;
                        break;
                    }
                }
                if (!disponivel) {
                    continue;
                }

                for (int i = 0; i < len; i++) {
                    int actrow = row + i*dir.getDx();
                    int actcol = col + i*dir.getDy();
                    puzzle[actrow][actcol] = w.charAt(i);
                }
                colocado = true;

            }
        }

        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (puzzle[i][j] == '.') {
                    puzzle[i][j] = (char) ('A' + random.nextInt(26));
                }
            }
        }


    }

    private void writefile(String file) {
        PrintWriter pw = null;
        StringBuilder sb = null;
        try {
            pw = new PrintWriter(new File(file));
            sb = new StringBuilder();
            for (int i = 0; i < dim; i++) {
                for (int j = 0; j < dim; j++) {
                    pw.print(puzzle[i][j]);
                }
                pw.println();
            }
            
            for (int k = 0; k < words.size(); k++) {
                if (k > 0) {
                    sb.append(";");
                }
                sb.append(words.get(k).toLowerCase());
            }
            pw.println(sb.toString());
            pw.println();
            
        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }

    private void printresult() {
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                System.out.print(puzzle[i][j]);
            }
            System.out.println();
        }
        
        
        StringBuilder sb = new StringBuilder();
        for (int k = 0; k < words.size(); k++) {
            if (k > 0) {
                sb.append(";");
            }
            sb.append(words.get(k).toLowerCase());
        }
        System.out.println(sb.toString());
        System.out.println();
    }

    public static void main(String[] args) {

        WSGenerator gen = new WSGenerator();
        gen.gerarsopa("wlist1.txt", 12, "sopa1.txt");
        gen.printresult();

        WSGenerator gen2 = new WSGenerator();
        gen2.gerarsopa("wlist2.txt", 12, "sopa2.txt");
        gen2.printresult();

        WSGenerator gen3 = new WSGenerator();
        gen3.gerarsopa("wlist3.txt", 12, "sopa3.txt");
        gen3.printresult();
    }
}
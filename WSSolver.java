package lab01;


public class WSSolver {
    public static void main(String[] args) {
        WSManager ws = new WSManager();
        ws.readfile("sdl_01.txt");
        ws.findWords();
        ws.printresult();
        ws.writefile("out1.txt");

        
        ws = new WSManager();
        ws.readfile("sdl_02.txt");
        ws.findWords();
        ws.printresult();
        ws.writefile("out2.txt");

        ws = new WSManager();
        ws.readfile("sdl_03.txt");
        ws.findWords();
        ws.printresult();
        ws.writefile("out3.txt");
    }
}

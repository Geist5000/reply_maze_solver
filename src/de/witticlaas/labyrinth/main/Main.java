package de.witticlaas.labyrinth.main;

import javafx.util.Pair;

import java.io.File;

public class Main {


    public static void main(String[] args) {
        String pathToFile = "C:/Users/Azubi/Downloads/jumpyrinth/2c464e58-9121-11e9-aec5-34415dec71f2.txt";
        File f = new File(pathToFile);

        Labyrint labyrint = new Labyrint(f);
        System.out.println(labyrint.getChar(35,7));
        Position start = null;
        int x = 0;
        int y = 0;
        char[] line = labyrint.getLine(y);
        while(true){
            char current = line[x];
            if(current == '$'){
                start = new Position(x,y);
                Path p = new Path(labyrint,start);
                System.out.println(p.getFlag());
            }
            if(x == line.length-1){
                x = 0;
                y++;
                line = labyrint.getLine(y);
            }else{
                x++;
            }
        }



    }
}

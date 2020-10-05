package de.witticlaas.labyrinth.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Labyrint {


    private List<char[]> field;

    public Labyrint(File f) {
        field = new ArrayList<>();
        try (Scanner sc = new Scanner(f)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                field.add(line.toCharArray());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public char getChar(int x, int y) {
        return field.get(y)[x];
    }

    public char[] getLine(int y){
        return field.get(y);
    }

    public char[] getColumn(int x){
        char[] column = new char[field.size()];
        int index = 0;
        for (char[] line: field) {
            column[index++] = line[x];
        }

        return column;
    }

    public int lineCount(){
        return field.size();
    }


}

package de.witticlaas.labyrinth.main;


import java.util.Stack;
import java.util.function.Consumer;

public class Path {


    private Labyrint labyrint;
    private Position position;
    private StringBuilder flag;
    private Stack<Character> stack;

    public Path(Labyrint labyrint, Position start) {
        this.labyrint = labyrint;
        this.position = start;
        stack = new Stack<>();
        flag = new StringBuilder();
        this.processInstruction();
    }

    public String getFlag(){
        while(position.getX()!= -1 & position.getY() != -1){
            processInstruction();
        }
        return flag.toString();
    }


    /**
     * @return new location or x:-1, y:-1 if path is finished
     */
    public void processInstruction() {
        char instruction = labyrint.getChar(position.getX(), position.getY());

        switch (instruction) {
            case '$':position.setY(position.getY()+1);break;
            case '(':processShift(-1, 0, new Runnable() {
                @Override
                public void run() {
                    flag.insert(0,stack.pop());
                }
            });break;
            case ')':processShift(1, 0, new Runnable() {
                @Override
                public void run() {
                    flag.append(stack.pop());
                }
            });break;
            case '[':moveAndDoAction(2, 0, new Runnable() {
                @Override
                public void run() {
                    stack.push(labyrint.getChar(position.getX()+1,position.getY()));
                }
            });break;
            case ']':moveAndDoAction(-2, 0, new Runnable() {
                @Override
                public void run() {
                    stack.push(labyrint.getChar(position.getX()-1,position.getY()));
                }
            });break;
            case '*':moveAndDoAction(0, -2, new Runnable() {
                @Override
                public void run() {
                    stack.push(labyrint.getChar(position.getX(),position.getY()-1));
                }
            });break;
            case '.':moveAndDoAction(0, 2, new Runnable() {
                @Override
                public void run() {
                    stack.push(labyrint.getChar(position.getX(),position.getY()+1));
                }
            });break;
            case '%':moveAndDoAction(0, 1, new Runnable() {
                @Override
                public void run() {
                    flag.reverse();
                }
            });break;
            case '+':processShift(0, 1, new Runnable() {
                @Override
                public void run() {
                    flag.deleteCharAt(flag.length()-1);
                }
            });break;
            case '-':processShift(0, -1, new Runnable() {
                @Override
                public void run() {
                    flag.deleteCharAt(0);
                }
            });break;
            case '<':processShift(-1,0);break;
            case '>':processShift(1,0);break;
            case '^':processShift(0,-1);break;
            case 'v':processShift(0,1);break;
            case '@':
                position.setXY(-1, -1);break;
            default:
                System.out.println("default");
        }
    }


    private void processShift(int dir, int upDown){
        this.processShift(dir, upDown, new Runnable() {
            @Override
            public void run() {

            }
        });
    }
    private void processShift(int dir, int upDown, Runnable action) {
        if (dir > 1 || dir < -1 || upDown > 1 || upDown < -1 || (!((dir == 0 && upDown != 0) || (dir != 0 && upDown == 0)))) {
            throw new IllegalArgumentException("direction to low or to high");
        }
        int x = position.getX();
        int y = position.getY();
        String number = "";
        char current = labyrint.getChar(x += dir*-1, y += upDown*-1);
        while (Character.isDigit(current)) {
            try {

                number += current;
                current = labyrint.getChar(x += dir*-1, y += upDown*-1);
            } catch (Exception e) {
                e.printStackTrace();
                throw new IllegalStateException("no number found");
            }
        }

        int shift = Integer.parseInt(number);
        moveAndDoAction(dir*shift,upDown*shift, action);
    }

    private void moveAndDoAction(int xOffset, int yOffset, Runnable action){
        action.run();
        position.setXY(position.getX()+xOffset,position.getY()+yOffset);
    }
}

package Game;


import java.util.Random;

public class MathGame {
    public int first;
    public int second;
    public int answer;
    public String example;
    public boolean isSolved = false;

    public int[] randomNumber(int min, int max, int min1, int max1) {
        Random random = new Random();
        int first = random.nextInt(max - min + 1) + min;  // [10; 20]
        int second = random.nextInt(max1 - min1 + 1) + min1;  // [10; 20]
        return new int[]{first, second};
    }

    public void generationNumber() {
        int[] nubmers;
        Random random = new Random();
        switch (random.nextInt(3)) {
            case (0):
                nubmers = this.randomNumber(1, 10000, 1, 10000);
                this.first = nubmers[0];
                this.second = nubmers[1];
                this.answer = first + second;
                this.example = first + " + " + second + " = ";
                break;
            case (1):
                nubmers = this.randomNumber(1000, 2000, 100, 999);
                this.first = nubmers[0];
                this.second = nubmers[1];
                this.answer = first - second;
                this.example = first + "  - " + second + " = ";
                break;
            case (2):
                nubmers = this.randomNumber(1, 100, 1, 100);
                this.first = nubmers[0];
                this.second = nubmers[1];
                this.answer = first * second;
                this.example = first  + " * " + second + " = ";
                break;

        }
        this.isSolved = false;
    }

}

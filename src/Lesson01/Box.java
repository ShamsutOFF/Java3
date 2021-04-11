package Lesson01;

import java.util.ArrayList;

public class Box<T extends Fruit> {
    private ArrayList<T> list = new ArrayList<> ( );
    float maxWeight = 10;

    public void addFruit(int count, T fruit) {
        for (int i = 0; i < count; i++) {
            float w = this.getWeight ( );
            if (w <= this.maxWeight - fruit.getWeight ( )) {
                list.add (fruit);
            }
        }
    }


    public float getWeight() {
        float weght = 0;
        for (T el : list) {
            weght += el.getWeight ( );
        }
        return weght;
    }

    public boolean compare(Box<?> box) {
        return Math.abs (this.getWeight ( ) - box.getWeight ( )) < 0.001;
    }

    public void pour(Box<T> box) {
        float a = list.get (0).getWeight ( );
        while (box.getWeight ( ) <= box.maxWeight - a && !list.isEmpty ( )) {
            box.addFruit (1, list.get (0));
            list.remove (0);
        }
    }
}

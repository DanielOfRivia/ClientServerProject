package Storage;

import java.util.concurrent.atomic.AtomicInteger;

public class Item {
    private final String name;
    private final AtomicInteger price;
    private final AtomicInteger number;

    Item(String name, int price, int number){
        this.name = name;
        this.price = new AtomicInteger(price);
        this.number = new AtomicInteger(number);
    }

    Item(String name){
        this.name = name;
        this.price = new AtomicInteger(0);
        this.number = new AtomicInteger(0);
    }


    public String getName() {
        return name;
    }

    public AtomicInteger getNumber() {
        return number;
    }

    public AtomicInteger getPrice() {
        return price;
    }

}

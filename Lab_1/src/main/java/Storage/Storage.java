package Storage;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
public class Storage {
    public enum command_types {
        GET_ITEM_NUMBER,
        DELETE_ITEM,
        ADD_ITEM,
        ADD_GROUP,
        ADD_ITEM_TO_GROUP,
        SET_ITEM_PRICE,
        EXCEPTION_FROM_SERVER
    }
    private final ConcurrentHashMap<String, ArrayList<Item>> items_storage;
    public Storage(){
        items_storage = new ConcurrentHashMap<>();
    }

    public AtomicInteger number_of_items(String name) throws Exception{
        for(ArrayList<Item> arr : items_storage.values()){
            for(Item item : arr){
                if(item.getName().equals(name)){
                    return item.getNumber();
                }
            }
        }
        throw new Exception("No items with name \"" + name + "\"" );
    }

    public void delete_items_number (String name, int number) throws Exception{
        for(ArrayList<Item> arr : items_storage.values()){
            for(Item item : arr){
                if(item.getName().equals(name)){
                    item.getNumber().addAndGet(-number);
                }
            }
        }
        throw new Exception("No items with name \"" + name + "\"" );
    }

    public void add_items_number (String name, int number) throws Exception{
        for(ArrayList<Item> arr : items_storage.values()){
            for(Item item : arr){
                if(item.getName().equals(name)){
                    item.getNumber().addAndGet(number);
                }
            }
        }
        throw new Exception("No items with name \"" + name + "\"" );
    }

    public void add_group (String name){
        items_storage.put(name, new ArrayList<>());
    }

    public void add_item_to_group (String item_name, String group_name){
        (items_storage.get(group_name)).add(new Item(item_name));
    }

    public void set_price(String name, int new_price) throws Exception{
        for(ArrayList<Item> arr : items_storage.values()){
            for(Item item : arr){
                if(item.getName().equals(name)){
                    item.getPrice().set(new_price);
                }
            }
        }
        throw new Exception("No items with name \"" + name + "\"" );

    }

    public void print_info(){
        for (String i : items_storage.keySet()) {
            System.out.println("Group: " + i);
            for (Item item : items_storage.get(i)){
                System.out.print("   ");
                System.out.println("name: " + item.getName());
                System.out.print("   ");
                System.out.println("price: " + item.getPrice().toString());
                System.out.print("   ");
                System.out.println("number of items: " + item.getNumber().toString());
                System.out.println();
            }
            System.out.println();
        }
    }
}

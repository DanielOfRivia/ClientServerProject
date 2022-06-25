package Storage;

import java.util.ArrayList;
import java.util.HashMap;

public class Storage {
    private static HashMap<String, ArrayList<String>> groups = new HashMap<>();
    private static HashMap<String, int[]> items = new HashMap<>();//1 - amount, 2 - price
    public Storage(){
        groups.put("drinks", new ArrayList<String>());
        items.put("milk", new int[2]);
        groups.get("drinks").add("milk");
        items.get("milk")[0] = 20;
        items.get("milk")[1] = 30;
    }

    public int number_of_items(String name) throws Exception{
        System.out.println("number_of_items " + name);
        try {
            return items.get(name)[0];
        }catch (Exception e){
            throw new Exception("No items with this name");
        }

    }

    public void delete_items_number (String name, int number) throws Exception{
        int [] new_arr;
        try {
            new_arr = items.get(name);
        } catch (Exception e){
            throw new Exception("No items with this name");
        }
        if(new_arr[0] < number){
            throw new Exception("Not enough items in storage");
        }else {
            new_arr[0] = new_arr[0] - number;
            System.out.println(new_arr[0]);
            items.put(name, new_arr);
            System.out.println(items.get(name)[0]);
        }
        System.out.println("delete_items_number " + name + ", " + number);
        print_info();
    }

    public void add_items_number (String name, int number) throws Exception{
        int [] new_arr;
        try {
            new_arr = items.get(name);
        } catch (Exception e){
            throw new Exception("No items with this name");
        }
        new_arr[0] += number;
        items.put(name, new_arr);
        System.out.println("add_items_number " + name + ", " + number);

    }

    public void add_group (String name) throws Exception{
        groups.put(name, new ArrayList<String>());
        System.out.println("add_group " + name);
    }

    public void add_item_to_group (String item_name, String group_name) throws Exception{
        try {
            (groups.get(group_name)).add(item_name);
        } catch (Exception e){
            throw new Exception("No group with this name");
        }
        items.put(item_name, new int[2]);
        System.out.println("add_item_to_group " + item_name + ", " + group_name);

    }

    public void set_price(String name, int new_price) throws Exception{
        int [] new_arr;
        try {
            new_arr = items.get(name);
        } catch (Exception e){
            throw new Exception("No items with this name");
        }
        new_arr[1] = new_price;
        items.put(name, new_arr);
        System.out.println("set_price " + name + ", " + new_price);

    }

    public void print_info(){
        System.out.println("Groups: ");
        for (String i : groups.keySet()) {
            System.out.print(i + " - ");
            for(int k = 0; k < groups.get(i).size(); k++){
                System.out.print(groups.get(i).get(k) + ", ");
            }
            System.out.println();
        }
        System.out.println("Items: ");
        for (String i : items.keySet()) {
            System.out.println(i + ", number of items - " + items.get(i)[0] + ", price - " + items.get(i)[1]);
        }
    }
}
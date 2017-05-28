package Data;

/**
 * Created by Clunctia on 5/27/2017.
 */

public class User {
    private String id;
    private String name;
    private double money;



    private Cart cart;

    private static User instance;

    public static User getInstance(){
        if(instance == null){
            instance = new User("10", "Gift");
        }
        return instance;
    }

    private User(String id, String name){
        this.id = id;
        this.name = name;
        money = 0;
        cart = new Cart();
    }

    public String getId() {return id; }

    public String getName() {return name;}

    public double getMoney() {return money;}

    public Cart getCart() {return cart;}

    public void addMoney(double amount) { money += amount; }

    public boolean pay(double amount){
        if(money - money < 0){
            return false;
        } else {
            money -= amount;
            return true;
        }
    }

    public void addToCart(Book b) { cart.addToCart(b); }

    public void removeFromCart(Book b) { cart.removeFromCart(b); }

    public void emptyCart() { cart.clearCart(); }


}

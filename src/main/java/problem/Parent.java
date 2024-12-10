package problem;

public class Parent {
    int x = 1000;

    public Parent() {
        this.x = 3000;
    }

    public Parent(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }
}


 class Child {
    int x = 4000;




     public int getX() {
         return x;
     }
 }

class Main123 {
    public static void main(String[] args) {
        Child child = new Child();
        System.out.println(child.getX());
    }
}

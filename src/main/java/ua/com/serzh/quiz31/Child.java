package ua.com.serzh.quiz31;

/**
 * Created by Serzh on 8/12/16.
 */
public class Child extends Parent {
    public static void main(String[] args) {
        Parent parent = new Child();
        Parent parent1 = new Parent();
        System.out.println(parent.getClass());
        System.out.println(parent1.getClass());
    }
}

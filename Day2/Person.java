package org.apache.flink;

public class Person {
    public int id;
    public String name;
    public int age;

    public Person() {}

    public Person(int id, String name,int age){
        this.id = id;
        this.name = name;
        this.age = age;
    }
    @Override
    public String toString(){
        return "Person{is=" + id + ", name='" + name + "', age=" + age + "}";
    }
}
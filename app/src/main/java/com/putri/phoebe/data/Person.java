package com.putri.phoebe.data;

/**
 * Created by putri on 12/7/16.
 */

public class Person {

    private int id;

    private String name;

    private String fileName;

    public Person() {}

    public Person(int id, String name, String fileName) {
        this.id = id;
        this.name = name;
        this.fileName = fileName;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFileName() {
        return fileName;
    }
}

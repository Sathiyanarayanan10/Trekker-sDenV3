package com.example.trekkersdenv3;

public class treks {
    private final String name;
    private final String state;
    private final String location;
    private final int diff;

    public treks(String name, String state,String location,int diff) {
        this.name = name;
        this.state = state;
        this.location = location;
        this.diff = diff;
    }

    public String getName(){
        return name;
    }
    public String getState(){
        return state;
    }

    public int getDiff() {
        return diff;
    }

    public String getLocation() {return location;}
}

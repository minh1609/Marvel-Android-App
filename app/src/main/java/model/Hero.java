package model;

import java.util.ArrayList;
import java.util.List;

public class Hero {
    public String name;
    public String description;
    public Thumbnail thumbnail;
    public String id;
    public SubCategory comics;
    public SubCategory events;

    public Hero(String name, String description) {
        this.name = name;
        this.description = description;
    }
}

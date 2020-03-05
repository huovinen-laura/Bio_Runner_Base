package com.mygdx.game;

import java.util.ArrayList;

public class ShitCollection {
    private ArrayList<Shit> allShit;

    public ShitCollection() {
        this.allShit = new ArrayList<>();
    }

    public void addStuff(String name) {
        boolean foundShit = false;
        for(int i = 0; i < this.allShit.size();i++) {
            if(this.allShit.get(i).getName().contentEquals(name)) {
                this.allShit.get(i).incrementCount();
                foundShit = true;
                break;
            }
        }

        if(!foundShit) {
            this.allShit.add(new Shit(name));
        }
    }

    public ArrayList<Shit> getAllShit() {
        return allShit;
    }

    public void setAllShit(ArrayList<Shit> allShit) {
        this.allShit = allShit;
    }


    public class Shit {
        String name;
        int count;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCount() {
            return count;
        }
        public void incrementCount() {
            this.count++;
        }
        public void setCount(int count) {
            this.count = count;
        }

        public Shit(String name) {
            this.name = name;
            this.count = 1;
        }
    }
}

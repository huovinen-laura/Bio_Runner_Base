package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class ShitCollection {
    private ArrayList<Shit> allShit;

    public ShitCollection() {
        this.allShit = new ArrayList<>();
        this.allShit.add(new Shit("banaani",TestCollectible.texture));
    }

    public void addStuff(GameObject object) {

        for(int i = 0; i < this.allShit.size();i++) {

            if(this.allShit.get(i).getName().contentEquals(object.getName())) {
                this.allShit.get(i).incrementCount();
                break;
            }
        }
    }

    public void clear() {
        for (int i = 0; i < this.allShit.size(); i++) {

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
        Texture texture;

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

        public Shit(String name, Texture newTexture) {
            this.name = name;
            this.count = 0;
            this.texture = newTexture;
        }

        public Texture getTexture() {
            return texture;
        }

        public void setTexture(Texture texture) {
            this.texture = texture;
        }
    }
}

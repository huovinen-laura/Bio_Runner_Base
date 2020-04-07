package com.mygdx.game;

import java.util.ArrayList;

public class PowerUpCollection {
    private ArrayList<GameAction> listOfNegativePowers;
    private ArrayList<GameAction> lisOfPositivePowers;
    private ArrayList<GameAction> listOfNeutralPowers;
    private final BioRunnerGame game;

    public PowerUpCollection(final BioRunnerGame game) {
        this.game = game;
        this.listOfNeutralPowers = new ArrayList<>();

        this.listOfNeutralPowers.add(new GameAction() {
            @Override
            public void doAction() {
                game.setPointsPerCollectable(2);
                game.lifeCounter.setLives(1);
            }

            @Override
            public String getName() {
                return ("doublePoints");
            }

        }
        );

        this.listOfNeutralPowers.add(new GameAction() {
            @Override
            public void doAction() {
                if(game.worldSpeed < -1) {
                    game.worldSpeed += 1;
                } else {
                    game.worldSpeed = -1;
                }

            }

            @Override
            public String getName() {
                return ("slowDown");
            }
        });
    }

    public GameAction[] getTwoRandomPowers() {
        GameAction[] powerUps = new GameAction[2];
        powerUps[0] = listOfNeutralPowers.get(0);
        powerUps[1] = listOfNeutralPowers.get(1);
        return powerUps;


    }

    public GameAction getRandomNegativePower() {
        int size = this.listOfNeutralPowers.size();
        int i = (int) Math.random() * (size +1);
        return listOfNegativePowers.get(i);
    }

    public GameAction getRandomPositivePower() {
        int size = this.listOfNeutralPowers.size();
        int i = (int) Math.random() * (size +1);
        return lisOfPositivePowers.get(i);
    }

    public GameAction getRandomNeutralPower() {
        int size = this.listOfNeutralPowers.size();
        int i = (int) Math.random() * (size +1);
        return listOfNeutralPowers.get(i);
    }
}

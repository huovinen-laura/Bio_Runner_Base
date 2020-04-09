package com.mygdx.game;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;

public class PowerUpCollection {
    private ArrayList<GameAction> listOfNegativePowers;
    private ArrayList<GameAction> lisOfPositivePowers;
    private ArrayList<GameAction> listOfNeutralPowers;
    private final BioRunnerGame game;
    private int[] takenPowers;

    public PowerUpCollection(final BioRunnerGame game) {
        this.game = game;
        this.listOfNeutralPowers = new ArrayList<>();
        this.lisOfPositivePowers = new ArrayList<>();
        this.listOfNegativePowers = new ArrayList<>();
        takenPowers = new int[]{99,99,99};

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

            @Override public void undoAction() {
                game.setPointsPerCollectable(1);
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

            @Override
            public void undoAction() {

            }
        });

        this.lisOfPositivePowers.add(new GameAction() {
            @Override
            public void doAction() {
                game.lifeCounter.gainLife();
            }

            @Override
            public String getName() {
                return "extraLife";
            }

            @Override
            public void undoAction() {

            }
        });
        this.lisOfPositivePowers.add(new GameAction() {
            @Override
            public void doAction() {
                game.lifeCounter.gainLife();
                game.lifeCounter.gainLife();
            }

            @Override
            public String getName() {
                return "fullLives";
            }

            @Override
            public void undoAction() {

            }
        });

        this.listOfNegativePowers.add(new GameAction() {
            @Override
            public void doAction() {
                game.lifeCounter.setLives(1);
            }

            @Override
            public String getName() {
                return "cowFart";
            }

            @Override
            public void undoAction() {

            }
        });

        this.listOfNegativePowers.add(new GameAction() {
            @Override
            public void doAction() {
                game.worldSpeed = -1f;
            }

            @Override
            public String getName() {
                return("likeSnail");
            }

            @Override
            public void undoAction() {

            }
        });

        this.listOfNeutralPowers.add(new GameAction() {
            @Override
            public void doAction() {
                game.playerScore += 50;
            }

            @Override
            public String getName() {
                return "funHappy";
            }

            @Override
            public void undoAction() {
            }

        });
    }

    public GameAction[] getTwoRandomPowers() {
        GameAction[] powerUps = new GameAction[2];
        double firstRoll = Math.random();
        double secondRoll = Math.random();
        Gdx.app.log("Game","Level number");

        if(firstRoll > 2/game.getLevelNumber()) {
            powerUps[0] = getRandomNegativePower();

        } else if(secondRoll > 1 / game.getLevelNumber() ){
            powerUps[0] = getRandomNeutralPower();
        } else {
            powerUps[0] = getRandomPositivePower();
        }

        if(secondRoll > 3/game.getLevelNumber()) {
            powerUps[1] = getRandomNegativePower();

        } else if(secondRoll > 1.5 / game.getLevelNumber() ){
            powerUps[1] = getRandomNeutralPower();
        } else {
            powerUps[1] = getRandomPositivePower();
        }

        this.takenPowers = new int[]{99,99,99};

        return powerUps;


    }

    public GameAction getRandomNegativePower() {
        int size = this.listOfNeutralPowers.size();
        int i = this.randomInt(0,size-1);

        while(this.takenPowers[0] == i) {

            i = this.randomInt(0,size-1);

        }

        takenPowers[0] = i;
        return listOfNegativePowers.get(i);
    }

    public GameAction getRandomPositivePower() {
        int size = this.listOfNeutralPowers.size();
        int i = this.randomInt(0,size-1);

        while(this.takenPowers[2] == i) {
            i = this.randomInt(0,size-1);

        }

        takenPowers[2] = i;
        return lisOfPositivePowers.get(i);
    }

    private int randomInt(int min, int max) {
        double roll = Math.random() * ((double) max - (double) min);
        return((int) (roll+min));
    }

    public GameAction getRandomNeutralPower() {
        int size = this.listOfNeutralPowers.size();
        int i = this.randomInt(0,size-1);

        while(this.takenPowers[1] == i) {
            i = this.randomInt(0,size-1);

        }

        takenPowers[1] = i;
        return listOfNeutralPowers.get(i);
    }
}

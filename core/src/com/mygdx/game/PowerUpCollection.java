package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

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
                game.playerScore += 50;
            }

            @Override
            public String getName() {
                return "funHappy";
            }

            @Override
            public void undoAction() {
            }

            @Override
            public String getDescription() {
                return game.getText("infoFunHappy");
            }

            @Override
            public Texture getButtonTexture() {
                return game.textureAssets.getExtraPoints();
            }

        });

        this.listOfNegativePowers.add(new GameAction() {
            @Override
            public void doAction() {
                game.worldSpeed -= 1;
            }

            @Override
            public String getName() {
                return "speedUp";
            }

            @Override
            public void undoAction() {
                game.worldSpeed += 1;
            }

            @Override
            public String getDescription() {
                return game.getText("infoSpeedUp");
            }

            @Override
            public Texture getButtonTexture() {
                return game.textureAssets.getFasterButton();
            }
        });

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

                                         @Override
                                         public String getDescription() {
                                             return game.getText("infoDouble");
                                         }

                                         @Override
                                         public Texture getButtonTexture() {
                                             return game.textureAssets.getDoublePoint();
                                         }


                                     }
        );

        this.listOfNeutralPowers.add(new GameAction() {
            @Override
            public void doAction() {
                if(game.worldSpeed < -1f) {
                    game.worldSpeed += 1f;
                } else {
                    game.worldSpeed = -1.5f;
                }

            }

            @Override
            public String getName() {
                return ("slowDown");
            }

            @Override
            public void undoAction() {

            }

            @Override
            public String getDescription() {
                return game.getText("infoSlower");
            }

            @Override
            public Texture getButtonTexture() {
                return game.textureAssets.getSlower();
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

            @Override
            public String getDescription() {
                return game.getText("infoExtraLife");
            }

            @Override
            public Texture getButtonTexture() {
                return game.textureAssets.getExtraLife();
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

            @Override
            public String getDescription() {
                return game.getText("infoFullLifes");
            }

            @Override
            public Texture getButtonTexture() {
                return game.textureAssets.getMaxHP();
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

            @Override
            public String getDescription() {
                return game.getText("infoCowFart");
            }

            @Override
            public Texture getButtonTexture() {
                return game.textureAssets.getOnlyOneHP();
            }
        });

        this.lisOfPositivePowers.add(new GameAction() {
            @Override
            public void doAction() {
                game.addFlowerPoints(10);
            }

            @Override
            public String getName() {
                return "flowers";
            }

            @Override
            public void undoAction() {

            }

            @Override
            public String getDescription() {
                return game.getText("infoFlowers");
            }

            @Override
            public Texture getButtonTexture() {
                return game.textureAssets.getFlowersPoints();
            }
        });

        this.listOfNegativePowers.add(new GameAction() {
            @Override
            public void doAction() {
                game.worldSpeed = -1.5f;
            }

            @Override
            public String getName() {
                return("likeSnail");
            }

            @Override
            public void undoAction() {

            }

            @Override
            public String getDescription() {
                return game.getText("infoSnail");
            }

            @Override
            public Texture getButtonTexture() {
                return game.textureAssets.getSnailSpeed();
            }
        });




    }

    public GameAction[] getTwoRandomPowers() {
        GameAction[] powerUps = new GameAction[2];
        double firstRoll = Math.random();
        double secondRoll = Math.random();
        double positiveChance = 0.50;
        double neutralChance = 0.30 ;
        double negativeChance = 0.05;

            positiveChance -= game.getLevelNumber()*2/100;
            neutralChance += game.getLevelNumber()/100;
            negativeChance += game.getLevelNumber()/200;

            if(firstRoll <= positiveChance) {
                powerUps[0] = getRandomPositivePower();
            } else if (firstRoll <= positiveChance + neutralChance) {
                powerUps[0] = getRandomNeutralPower();
            } else {
                powerUps[0] = getRandomNegativePower();
            }

            if(secondRoll <= positiveChance) {
                powerUps[1] = getRandomPositivePower();
            } else if (secondRoll <= positiveChance + neutralChance) {
                powerUps[1] = getRandomNeutralPower();
            } else {
                powerUps[1] = getRandomNegativePower();
            }

        Gdx.app.log("Game","Level number:" + game.getLevelNumber());
        Gdx.app.log("powers","PowerUp:" + powerUps[0].getName() + " "+ powerUps[1].getName());

        this.takenPowers = new int[]{99,99,99};

        return powerUps;


    }

    public GameAction getRandomNegativePower() {
        int size = this.listOfNegativePowers.size();
        int i = this.randomInt(0,size);

        while(this.takenPowers[0] == i) {

            i = this.randomInt(0,size);

        }

        takenPowers[0] = i;
        return listOfNegativePowers.get(i);
    }

    public GameAction getRandomPositivePower() {
        int size = this.lisOfPositivePowers.size();
        int i = this.randomInt(0,size);
        Gdx.app.log("RandomPositive","Roll: " + i + " of " + size );

        while(this.takenPowers[2] == i) {
            i = this.randomInt(0,size);

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
        int i = this.randomInt(0,size);

        while(this.takenPowers[1] == i) {
            i = this.randomInt(0,size);

        }

        takenPowers[1] = i;
        return listOfNeutralPowers.get(i);
    }
}

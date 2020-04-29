package com.mygdx.bioRunnerGame;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

/**
 * Stores the data for each power up and gives random powerups
 */
public class PowerUpCollection {
    private ArrayList<GameAction> listOfNegativePowers;
    private ArrayList<GameAction> lisOfPositivePowers;
    private ArrayList<GameAction> listOfNeutralPowers;
    private final BioRunnerGame game;
    private int[] takenPowers;

    /**
     * Constructs the power up collection.
     *
     * @param game BioRunner game.
     */
    public PowerUpCollection(final BioRunnerGame game) {
        this.game = game;
        this.listOfNeutralPowers = new ArrayList<>();
        this.lisOfPositivePowers = new ArrayList<>();
        this.listOfNegativePowers = new ArrayList<>();
        takenPowers = new int[]{99,99,99};

        this.listOfNeutralPowers.add(new GameAction() {

            /**
             * Executes the power up.
             */
            @Override
            public void doAction() {
                game.setPlayerScore(game.getPlayerScore() + 50);
            }

            /**
             * Gets the name of the power up.
             *
             * @return Gets the name of the power up
             */
            @Override
            public String getName() {
                return "funHappy";
            }

            /**
             * Undos the power up.
             */
            @Override
            public void undoAction() {
            }

            /**
             * Describes the power up.
             *
             * @return Returns the description of the power up.
             */
            @Override
            public String getDescription() {
                return game.getText("infoFunHappy");
            }

            /**
             * Returns the button for the power up.
             *
             * @return Returns the button for the power up.
             */
            @Override
            public Texture getButtonTexture() {
                return game.getTextureAssets().getExtraPoints();
            }

        });

        this.listOfNegativePowers.add(new GameAction() {

            /**
             * Executes the power up.
             */
            @Override
            public void doAction() {
                game.setWorldSpeed(game.getWorldSpeed() - 1);
            }

            /**
             * Gets the name of the power up.
             *
             * @return Gets the name of the power up
             */
            @Override
            public String getName() {
                return "speedUp";
            }

            /**
             * Undos the power up.
             */
            @Override
            public void undoAction() {
                game.setWorldSpeed(game.getWorldSpeed() + 1);
            }

            /**
             * Describes the power up.
             *
             * @return Returns the description of the power up.
             */
            @Override
            public String getDescription() {
                return game.getText("infoSpeedUp");
            }

            /**
             * Returns the button for the power up.
             *
             * @return Returns the button for the power up.
             */
            @Override
            public Texture getButtonTexture() {
                return game.getTextureAssets().getFasterButton();
            }
        });

        this.listOfNeutralPowers.add(new GameAction() {

            /**
             * Executes the power up.
             */
            @Override
            public void doAction() {
                game.setPointsPerCollectable(2);
                game.getLifeCounter().setLives(1);
            }

            /**
             * Gets the name of the power up.
             *
             * @return Gets the name of the power up
             */
            @Override
            public String getName() {
                return ("doublePoints");
            }

            /**
             * Undos the power up.
             */
            @Override public void undoAction() {
                game.setPointsPerCollectable(1);
            }

            /**
             * Describes the power up.
             *
             * @return Returns the description of the power up.
             */
             @Override
             public String getDescription() {
                 return game.getText("infoDouble");
             }

            /**
             * Returns the button for the power up.
             *
             * @return Returns the button for the power up.
             */
             @Override
             public Texture getButtonTexture() {
                 return game.getTextureAssets().getDoublePoint();
             }


         }
        );

        this.listOfNeutralPowers.add(new GameAction() {

            /**
             * Executes the power up.
             */
            @Override
            public void doAction() {
                if(game.getWorldSpeed() < -1f) {
                    game.setWorldSpeed(game.getWorldSpeed() + 1f);
                } else {
                    game.setWorldSpeed(-1.5f);
                }

            }

            /**
             * Gets the name of the power up.
             *
             * @return Gets the name of the power up
             */
            @Override
            public String getName() {
                return ("slowDown");
            }

            /**
             * Undos the power up.
             */
            @Override
            public void undoAction() {

            }

            /**
             * Describes the power up.
             *
             * @return Returns the description of the power up.
             */
            @Override
            public String getDescription() {
                return game.getText("infoSlower");
            }

            /**
             * Returns the button for the power up.
             *
             * @return Returns the button for the power up.
             */
            @Override
            public Texture getButtonTexture() {
                return game.getTextureAssets().getSlower();
            }
        });

        this.lisOfPositivePowers.add(new GameAction() {

            /**
             * Executes the power up.
             */
            @Override
            public void doAction() {
                game.getLifeCounter().gainLife();
            }

            /**
             * Gets the name of the power up.
             *
             * @return Gets the name of the power up
             */
            @Override
            public String getName() {
                return "extraLife";
            }

            /**
             * Undos the power up.
             */
            @Override
            public void undoAction() {

            }

            /**
             * Describes the power up.
             *
             * @return Returns the description of the power up.
             */
            @Override
            public String getDescription() {
                return game.getText("infoExtraLife");
            }

            /**
             * Returns the button for the power up.
             *
             * @return Returns the button for the power up.
             */
            @Override
            public Texture getButtonTexture() {
                return game.getTextureAssets().getExtraLife();
            }
        });
        this.lisOfPositivePowers.add(new GameAction() {

            /**
             * Executes the power up.
             */
            @Override
            public void doAction() {
                game.getLifeCounter().gainLife();
                game.getLifeCounter().gainLife();
            }

            /**
             * Gets the name of the power up.
             *
             * @return Gets the name of the power up
             */
            @Override
            public String getName() {
                return "fullLives";
            }

            /**
             * Undos the power up.
             */
            @Override
            public void undoAction() {

            }

            /**
             * Describes the power up.
             *
             * @return Returns the description of the power up.
             */
            @Override
            public String getDescription() {
                return game.getText("infoFullLifes");
            }

            /**
             * Returns the button for the power up.
             *
             * @return Returns the button for the power up.
             */
            @Override
            public Texture getButtonTexture() {
                return game.getTextureAssets().getMaxHP();
            }
        });

        this.listOfNegativePowers.add(new GameAction() {

            /**
             * Executes the power up.
             */
            @Override
            public void doAction() {
                game.getLifeCounter().setLives(1);
            }

            /**
             * Gets the name of the power up.
             *
             * @return Gets the name of the power up
             */
            @Override
            public String getName() {
                return "cowFart";
            }

            /**
             * Undos the power up.
             */
            @Override
            public void undoAction() {

            }

            /**
             * Describes the power up.
             *
             * @return Returns the description of the power up.
             */
            @Override
            public String getDescription() {
                return game.getText("infoCowFart");
            }

            /**
             * Returns the button for the power up.
             *
             * @return Returns the button for the power up.
             */
            @Override
            public Texture getButtonTexture() {
                return game.getTextureAssets().getOnlyOneHP();
            }
        });

        this.lisOfPositivePowers.add(new GameAction() {

            /**
             * Executes the power up.
             */
            @Override
            public void doAction() {
                game.addFlowerPoints(10);
            }

            /**
             * Gets the name of the power up.
             *
             * @return Gets the name of the power up
             */
            @Override
            public String getName() {
                return "flowers";
            }

            /**
             * Undos the power up.
             */
            @Override
            public void undoAction() {

            }

            /**
             * Describes the power up.
             *
             * @return Returns the description of the power up.
             */
            @Override
            public String getDescription() {
                return game.getText("infoFlowers");
            }

            /**
             * Returns the button for the power up.
             *
             * @return Returns the button for the power up.
             */
            @Override
            public Texture getButtonTexture() {
                return game.getTextureAssets().getFlowersPoints();
            }
        });

        this.listOfNegativePowers.add(new GameAction() {

            /**
             * Executes the power up.
             */
            @Override
            public void doAction() {
                game.setWorldSpeed(-1.5f);
            }

            /**
             * Gets the name of the power up.
             *
             * @return Gets the name of the power up
             */
            @Override
            public String getName() {
                return("likeSnail");
            }

            /**
             * Undos the power up.
             */
            @Override
            public void undoAction() {

            }

            /**
             * Describes the power up.
             *
             * @return Returns the description of the power up.
             */
            @Override
            public String getDescription() {
                return game.getText("infoSnail");
            }

            /**
             * Returns the button for the power up.
             *
             * @return Returns the button for the power up.
             */
            @Override
            public Texture getButtonTexture() {
                return game.getTextureAssets().getSnailSpeed();
            }
        });

    }

    /**
     * Gives 2 random power ups chances for easy power ups get slimmer when game progresses
     *
     * @return two different power ups in array of length 2
     */
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

        this.takenPowers = new int[]{99,99,99};

        return powerUps;


    }

    private GameAction getRandomNegativePower() {
        int size = this.listOfNegativePowers.size();
        int i = this.randomInt(0,size);

        while(this.takenPowers[0] == i) {

            i = this.randomInt(0,size);

        }

        takenPowers[0] = i;
        return listOfNegativePowers.get(i);
    }

    private GameAction getRandomPositivePower() {
        int size = this.lisOfPositivePowers.size();
        int i = this.randomInt(0,size);

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

    private GameAction getRandomNeutralPower() {
        int size = this.listOfNeutralPowers.size();
        int i = this.randomInt(0,size);

        while(this.takenPowers[1] == i) {
            i = this.randomInt(0,size);

        }

        takenPowers[1] = i;
        return listOfNeutralPowers.get(i);
    }
}

package com.mygdx.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class TextureAssets {


    AssetManager assets;
    private skins skinAssets = new skins();

    public TextureAssets() {
        assets = new AssetManager();

        assets.load("player2.png", Texture.class);
        assets.load("ball.png", Texture.class);
        assets.load("playerAnimation.png", Texture.class);
        assets.load("grass.png", Texture.class);
        assets.load("sky.png", Texture.class);
        assets.load("hp_player_ingame.png", Texture.class);
        assets.load("speaker_muted.png",Texture.class);
        assets.load("button_blank.png", Texture.class);
        assets.load("close_btn.png", Texture.class);
        assets.load("finland_flag.png", Texture.class);
        assets.load("british_flag.png", Texture.class);
        assets.load("speaker_on.png", Texture.class);
        assets.load("speaker_muted.png", Texture.class);
        assets.load("info_button_black.png", Texture.class);
        assets.load("general_button_normal.png", Texture.class);
        assets.load("store_hahmo_bg.png", Texture.class);
        assets.load("store_locked_layer.png", Texture.class);
        assets.load("double_points.png", Texture.class);
        assets.load("extra_life_button.png", Texture.class);
        assets.load("only_one_hp_button.png", Texture.class);
        assets.load("slower_button_rewind.png", Texture.class);
        assets.load("snail_speed_button.png", Texture.class);
        assets.load("max_hp_button.png", Texture.class);
        assets.load("10pts_flowers_button.png", Texture.class);
        assets.load("50pts_button.png", Texture.class);
        assets.load("faster_button.png", Texture.class);
        assets.load("LOGO.png", Texture.class);
        assets.load("progressbar.png", Texture.class);
        assets.load("menu_final.png", Texture.class);
        assets.load("yleinen_tausta_final.png", Texture.class);
        assets.load("recycle_final.png", Texture.class);
        assets.load("end_screen_final.png", Texture.class);
        assets.load("kijssa.png", Texture.class);
        assets.load("luu.png", Texture.class);
        assets.load("pilleri.png", Texture.class);
        assets.load("banaani.png", Texture.class);
        assets.load("tee.png", Texture.class);
        assets.load("kukkaKuollut.png", Texture.class);
        assets.load("mansikka.png", Texture.class);
        assets.load("omena.png", Texture.class);
        assets.load( "patteri.png", Texture.class);
        assets.load("pullo.png", Texture.class);
        assets.load("rööki.png", Texture.class);
        assets.load("leipa.png", Texture.class);
        assets.load("purkki.png", Texture.class);
        assets.load("pussi.png", Texture.class);
        assets.load("suklaa.png", Texture.class);
        assets.load("recycleGallHappy.png", Texture.class);
        assets.load("recycleGallSad.png", Texture.class);
        assets.load("hius.png", Texture.class);
        assets.load("laitossuomi.png", Texture.class);
        assets.load("tutorial1.png", Texture.class);
        assets.load("tutorial2.png", Texture.class);
        assets.load("tutorial3.png", Texture.class);
        assets.load("tutorial4.png", Texture.class);
        assets.load("lehti.png",Texture.class);
        assets.load("pallo.png",Texture.class);


    }

    public boolean update() {
        return(assets.update());

    }

    public Texture getLehti() {
        return assets.get("lehti.png");
    }

    public Texture getPallo() {
        return assets.get("pallo.png");
    }
    public Texture getKissa() {

        return assets.get("kjissa.png",Texture.class);
    }

    public Texture getLuu() {
        return assets.get("luu.png",Texture.class);
    }


    public Texture getPilleri() {
        return assets.get("pilleri.png",Texture.class);
    }

    public Texture getBanaani() {
        return assets.get("banaani.png",Texture.class);
    }

    public Texture getTee() {
        return assets.get("tee.png",Texture.class);
    }



    public Texture getLives() {
        return assets.get("hp_player_ingame.png",Texture.class);
    }

    public Texture getButtonBlue() {
        return assets.get("button_blank.png",Texture.class);
    }



    public Texture getCloseButton() {
        return assets.get("close_btn.png",Texture.class);
    }



    public Texture getFinnishButton() {
        return assets.get("finland_flag.png",Texture.class);
    }


    public Texture getEnglishButton() {
        return assets.get("british_flag.png",Texture.class);
    }


    public Texture getSpeakerOn() {
        return assets.get("speaker_on.png",Texture.class);
    }


    public Texture getSpeakerOff() {
        return assets.get("speaker_muted.png",Texture.class);
    }


    public Texture getInfoButton() {
        return assets.get("info_button_black.png",Texture.class);
    }

    public Texture getGeneralButton() {
        return assets.get("general_button_normal.png",Texture.class);
    }

    public Texture getStoreBG() {
        return assets.get("store_hahmo_bg.png",Texture.class);
    }

    public Texture getStoreLocked() {
        return assets.get("store_locked_layer.png",Texture.class);
    }

    public Texture getDoublePoint() {
        return assets.get("double_points.png",Texture.class);
    }

    public Texture getExtraLife() {
        return assets.get("extra_life_button.png",Texture.class);
    }

    public Texture getOnlyOneHP() {
        return assets.get("only_one_hp_button.png",Texture.class);
    }

    public Texture getSlower() {
        return assets.get("slower_button_rewind.png",Texture.class);
    }

    public Texture getSnailSpeed() {
        return assets.get("snail_speed_button.png",Texture.class);
    }

    public Texture getMaxHP() {
        return assets.get("max_hp_button.png",Texture.class);
    }

    public Texture getFlowersPoints() {
        return assets.get("10pts_flowers_button.png", Texture.class);
    }

    public Texture getExtraPoints() {
        return assets.get("50pts_button.png", Texture.class);
    }

    public Texture getFasterButton() {
        return assets.get("faster_button.png", Texture.class);
    }

    public Texture getLogo() {
        return assets.get("LOGO.png", Texture.class);
    }

    public Texture getPlayerChonky() {
        return assets.get("player2.png", Texture.class);
    }

    public Texture getPlayerOldFit() {
        return assets.get("ball.png", Texture.class);
    }

    public Texture getGrass() {
        return assets.get("grass.png", Texture.class);
    }

    public Texture getSky() {
        return assets.get("sky.png", Texture.class);
    }

    public void dispose() {
        assets.clear();
        this.skinAssets.dispose();
    }

    public String getPlayerChonkyAnimation() {
        return "vakio";
    }

    public Texture getProgressBar() {
        return  assets.get("progressbar.png", Texture.class);
    }


    public Texture getMenu() {
        return  assets.get("menu_final.png", Texture.class);
    }


    public Texture getCommon() {
        return assets.get("yleinen_tausta_final.png", Texture.class);
    }

    public Texture getRecycle() {
        return assets.get("recycle_final.png", Texture.class);
    }

    public Texture getEnd() {
        return  assets.get("end_screen_final.png", Texture.class);
    }

    public Texture getHappyGirl() {
        return  assets.get("recycleGallHappy.png", Texture.class);
    }
    public Texture getSadGirl() {
        return assets.get("recycleGallSad.png", Texture.class);
    }

    public Texture getKukka() {
        return assets.get("kukkaKuollut.png", Texture.class);
    }

    public Texture getMansikka() {
        return assets.get("mansikka.png", Texture.class);
    }

    public Texture getOmena() {
        return assets.get("omena.png", Texture.class);
    }

    public Texture getPatteri() {
        return assets.get( "patteri.png", Texture.class);
    }

    public Texture getPullo() {
        return assets.get("pullo.png", Texture.class);
    }

    public Texture getTupakka() {
        return assets.get("rööki.png", Texture.class);
    }

    public skins getSkinAssets() {
        return skinAssets;
    }

    public Texture getLeipa() {
        return assets.get("leipa.png", Texture.class);
    }


    public Texture getHius() {
        return assets.get("hius.png", Texture.class);
    }

    public Texture getPurkki() {
        return assets.get("purkki.png", Texture.class);
    }

    public Texture getPussi() {
        return assets.get("pussi.png", Texture.class);
    }

    public Texture getSuklaa() {
        return assets.get("suklaa.png", Texture.class);
    }

    public Texture getLaitos() {
        return assets.get("laitossuomi.png", Texture.class);
    }

    public Texture getTutorial1() {
        return assets.get("tutorial1.png", Texture.class);
    }

    public Texture getTutorial2() {
        return assets.get("tutorial2.png", Texture.class);
    }

    public Texture getTutorial3() {
        return assets.get("tutorial3.png", Texture.class);
    }
    public Texture getTutorial4() {
        return assets.get("tutorial4.png", Texture.class);
    }

    public int getLoadedAssets() {
        return assets.getLoadedAssets();
    }
}

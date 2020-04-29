package com.mygdx.bioRunnerGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;
import com.mygdx.bioRunnerGame.screens.HighScoreEntry;
import com.mygdx.bioRunnerGame.screens.HighScoreListener;
import java.util.ArrayList;

/**
 * To get this work on an android device add following line to
 * AndroidManifest.xml:
 * <uses-permission android:name="android.permission.INTERNET" />
 * above tag <application
 */
public class HighScoreServer {
    /**
     * url is the address of the highscore server.
     */
    private static String url;

    /**
     * If verbose is true, this class will print out messages to the Gdx log.
     */
    private static boolean verbose = false;

    /**
     * Password for the highscore host.
     */
    private static String password;

    /**
     * Username for the highscore host.
     */
    private static String user;

    /**
     * fetchHighScores gets high score entries from the server.
     *
     * This gets high score entries from a server, converts the json file to
     * List of HighScoreEntries and sends it back to the source.
     * @param source source class implementing HighScoreListener
     */
    public static void fetchHighScores(final HighScoreListener source) {
        Net.HttpRequest request = new Net.HttpRequest(HttpMethods.GET);
        request.setUrl("https://koti.tamk.fi/~ttjebi/highscore.py/get/");

        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {

            @Override
            public void handleHttpResponse (Net.HttpResponse httpResponse) {
                String r = httpResponse.getResultAsString();

                JsonValue jsonObject = (new JsonReader().parse(r));

                ArrayList<HighScoreEntry> highScores = new ArrayList<>();

                for (int i = 0; i <= 9; i++) {
                    HighScoreEntry score = new HighScoreEntry(
                            jsonObject.get(i).get(0).asString(),
                            jsonObject.get(i).get(1).asInt());
                    highScores.add(score);
                }

                source.receiveHighScore(highScores);
            }

            @Override
            public void failed (Throwable t) {

                source.failedToRetrieveHighScores(t);
            }

            @Override
            public void cancelled () {

            }

        });
    }

    /**
     * sendHighScore entry sends new high score data to the server
     *
     * It will then send a confirmation of success back to the source.
     * @param highScore The new HighScoreEntry data to be sent to the server.
     * @param source source class implementing HighScoreListener
     */
    public static void sendNewHighScore(HighScoreEntry highScore,
                                        final HighScoreListener source) {
        Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);

        String content = "name=" + highScore.getName() +
                "&score=" + highScore.getScore() +
                "&user=" + user +
                "&password=" + password;

        Net.HttpRequest request = new Net.HttpRequest(HttpMethods.POST);
        request.setUrl(url);
        request.setContent(content);

        if (user == null) {
            user = "DevJere";
            if (verbose)
                Gdx.app.error("HighSCoreServer", "user not set");
        } else if (password == null) {
            password="tampere";
            if (verbose)
                Gdx.app.error("HighSCoreServer", "password not set");
        } else if (url == null) {
            url= "http://koti.tamk.fi/~ttjebi/highscore.py/get/";
            if (verbose)
                Gdx.app.error("HighSCoreServer", "url not set");
        } else {
            Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
                @Override
                public void handleHttpResponse(Net.HttpResponse httpResponse) {
                    if (verbose)
                        Gdx.app.log("HighScoreServer", "Send: success");
                    source.receiveSendReply(httpResponse);
                }

                @Override
                public void failed(Throwable t) {
                    if (verbose)
                        Gdx.app.error("HighScoreServer",
                                "Send: failed", t);
                    source.failedToSendHighScore(t);
                }

                @Override
                public void cancelled() {
                    if (verbose)
                        Gdx.app.log("HighScoreServer", "Send: cancelled");
                }

            });
        }
    }

    public static void readConfig(String propFileName) {
        user = "DevJere";
        password = "tampere";
        url = "https://koti.tamk.fi/~ttjebi/highscore.py/get/";
    }

    public static String getGetUrl() {
        return url;
    }

    public static void setUrl(String getUrl) {
        HighScoreServer.url = getUrl;
    }

    public static boolean isVerbose() {
        return verbose;
    }

    public static void setVerbose(boolean verbose) {
        HighScoreServer.verbose = verbose;
    }


}


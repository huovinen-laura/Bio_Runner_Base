package com.mygdx.game.screens;

import com.badlogic.gdx.Net;
import com.mygdx.game.screens.HighScoreEntry;

import java.util.List;

public interface HighScoreListener {
    void receiveHighScore(List<HighScoreEntry> highScores);
    void failedToRetrieveHighScores(Throwable t);
    void receiveSendReply(Net.HttpResponse httpResponse);
    void failedToSendHighScore(Throwable t);
}
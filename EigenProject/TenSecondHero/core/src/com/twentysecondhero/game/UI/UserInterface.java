package com.twentysecondhero.game.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.sun.istack.internal.Nullable;
import com.twentysecondhero.game.Utility.GameData;

public class UserInterface
{
    private BitmapFont font = new BitmapFont();

    public UserInterface(@Nullable BitmapFont font){
        if(font != null) this.font = font;
    }

    public void render(){
        Batch batch = GameData.currentStage.getTileMapRenderer().getBatch();
        batch.begin();

        font.draw(batch, (GameData.stageTime - GameData.currentStage.getStageTime()) + "", Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);

        batch.end();
    }

    public void setFont(BitmapFont font){
        this.font = font;
    }
}

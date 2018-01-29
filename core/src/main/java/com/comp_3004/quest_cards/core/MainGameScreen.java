package com.comp_3004.quest_cards.core;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
//import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainGameScreen implements ApplicationListener {
    Texture texture;
    SpriteBatch batch;
    float elapsed;
    private Stage GameStage;
    private Table startTable;
    private Skin skin;
    public boolean active = false;



    @Override
    public void create () {
        active = true;
        skin = new Skin(Gdx.files.internal("buttonSkin.json"));
        GameStage = new Stage(new ScreenViewport());

        final Dialog gameDialog = new Dialog("GameScreen",skin);
        gameDialog.show(GameStage);
        Gdx.input.setInputProcessor(GameStage);

        

        //table.setDebug(true); // This is optional, but enables debug lines for tables.

        texture = new Texture(Gdx.files.internal("bg-castle.jpg"));
        batch = new SpriteBatch();

        MainGameLogic gamelogic;

        AdventureDeck advDeck = new AdventureDeck();
        StoryDeck storyDeck = new StoryDeck();
        advDeck.printDeck();
        storyDeck.printDeck();
    }

    @Override
    public void resize (int width, int height) {
        GameStage.getViewport().update(width, height, true);
    }

    @Override
    public void render () {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        GameStage.act(Gdx.graphics.getDeltaTime());
        GameStage.draw();
    }

    @Override
    public void dispose() {
        GameStage.dispose();

    }



    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }




    public static void main(String[] args) {
        System.out.println("GameStageScreen()"); // Display the string.

    }


}


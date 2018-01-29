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

public class MainMenuScreen implements ApplicationListener {
    Texture texture;
    SpriteBatch batch;
    float elapsed;
    private Stage mainMenu;
    private Table startTable;
    private Skin skin;
    private boolean exitStatus = false;
    private boolean gameStatus = false;



    @Override
    public void create () {
        skin = new Skin(Gdx.files.internal("buttonSkin.json"));
        mainMenu = new Stage(new ScreenViewport());

        final Dialog startDialog = new Dialog("Game Start",skin);
        final Dialog loadDialog = new Dialog("Loading...",skin);
        final Dialog rulesDialog = new Dialog("Rules",skin);
        Gdx.input.setInputProcessor(mainMenu);

        //START MENU SETUP
        final TextButton startButton = new TextButton("New Game",skin,"default");
        final TextButton loadButton = new TextButton("Load Game",skin,"default");
        final TextButton rulesButton = new TextButton("Rules",skin,"default");
        final TextButton exitButton = new TextButton("Exit",skin,"default");
        startTable = new Table();

        //SIZING ASSETS
        startTable.setWidth(mainMenu.getWidth());
        startTable.align(Align.left|Align.bottom);
        startTable.setPosition(20,10);
        startButton.setSize(200,50);
        //startButton.setPosition(0,150);
        loadButton.setSize(200,50);
        //loadButton.setPosition(0,100);
        rulesButton.setSize(200,50);
        //rulesButton.setPosition(0,50);
        exitButton.setSize(200,50);
        //exitButton.setPosition(0,0);


        //BUTTON LISTENERS
        //Placeholder Listeners, until proper implementation is introduced
        startButton.addListener(new ClickListener(){
                                    @Override
                                    public void clicked(InputEvent event, float x, float y) {
                                        setGameStatus(true);
                                        //startDialog.show(mainMenu);

                                    }
                                }
        );
        loadButton.addListener(new ClickListener(){
                                   @Override
                                   public void clicked(InputEvent event, float x, float y) {
                                       loadDialog.show(mainMenu);
                                   }
                               }
        );
        exitButton.addListener(new ClickListener(){
                                   @Override
                                   public void clicked(InputEvent event, float x, float y) {
                                       mainMenu.dispose();
                                       setExitStatus(true);
                                   }
                               }
        );
        rulesButton.addListener(new ClickListener(){
                                    @Override
                                    public void clicked(InputEvent event, float x, float y) {
                                        rulesDialog.show(mainMenu);
                                    }
                                }
        );

        startTable.add(startButton);
        startTable.row();
        startTable.add(loadButton);
        startTable.row();
        startTable.add(rulesButton);
        startTable.row();
        startTable.add(exitButton);
        startTable.row();
        mainMenu.addActor(startTable);
        startTable.setFillParent(true);

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
        mainMenu.getViewport().update(width, height, true);
    }

    @Override
    public void render () {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mainMenu.act(Gdx.graphics.getDeltaTime());
        mainMenu.draw();
    }

    @Override
    public void dispose() {
        mainMenu.dispose();

    }



    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }


    //ADDITIONAL METHODS
    public boolean getExitStatus() {
        return this.exitStatus;
    }

    public void setExitStatus(boolean status) {
        this.exitStatus = status;
    }


    public static void main(String[] args) {
        System.out.println("MainMenuScreen()"); // Display the string.

    }


    public void setGameStatus(boolean gameStatus) {
        this.gameStatus = gameStatus;
    }

    public boolean getGameStatus() {
        return gameStatus;
    }
}


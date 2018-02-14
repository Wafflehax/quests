package com.comp_3004.quest_cards.core;

import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.comp_3004.quest_cards.cards.QuestCard;
import com.comp_3004.quest_cards.gui.Assets;

import static com.comp_3004.quest_cards.gui.Config.VIRTUAL_HEIGHT;
import static com.comp_3004.quest_cards.gui.Config.VIRTUAL_WIDTH;


public class MenuPresenter extends Group {
    private Texture background;
    private Image bgImage;
    private QuestCards parent;
    private AssetManager manager;

    private Stage stage;
    private Table startTable;
    private Skin skin;
    private TextButton startButton,loadButton,rulesButton,exitButton;

    public MenuPresenter(QuestCards parent)
    {this.parent = parent;
        this.manager = parent.getAssetManager();
        loadAssets();
        Gdx.app.log("MenuState(gsm)","MenuState constructor runs");
    }


    public void loadAssets(){

        //Init
        stage = new Stage(new ScreenViewport());
        //Gdx.input.setInputProcessor(stage);

        background = new Texture("bg-castle.jpg");
        bgImage = new Image(background);
        skin = manager.get("skins/uiskin.json",Skin.class);

        bgImage.setBounds(0,0,VIRTUAL_WIDTH,VIRTUAL_HEIGHT);
        //play = new Texture("NewGame.png");

        //Init
        startButton = new TextButton("New Game",skin,"default");
        //loadButton = new TextButton("Load Game",skin,"default");
        rulesButton = new TextButton("Rules",skin,"default");
        exitButton = new TextButton("Exit",skin,"default");
        startTable = new Table();
        //Visual Configuration
        startTable.setWidth(250);
        startTable.align(Align.left|Align.bottom);
        startTable.setPosition(100,100);
        startButton.setSize(250,100);
        //loadButton.setSize(250,100);
        rulesButton.setSize(250,100);
        exitButton.setSize(250,100);
        //LISTENERS
        implementListeners();

        //Actor Allocation
        startTable.add(startButton);
        startTable.row();
        //startTable.add(loadButton); //TODO: Add when required
        //startTable.row();
        startTable.add(rulesButton);
        startTable.row();
        startTable.add(exitButton);
        startTable.row();

        stage.addActor(bgImage);
        stage.addActor(startTable);
        startTable.setFillParent(true);



    }

    private void implementListeners() { startButton.addListener(new ClickListener(){
                                                                    @Override
                                                                    public void clicked(InputEvent event, float x, float y) {
                                                                        Gdx.app.log("New Game","CLICKED!");
                                                                        ((QuestCards)Gdx.app.getApplicationListener()).ScreenAssign("mainGame");
                                                                        ((QuestCards)Gdx.app.getApplicationListener()).SetMenuMusic(false);
                                                                        dispose();

                                                                        //startDialog.show(mainMenu);

                                                                        /* added an instance of GameModel here just so I can run the app,
                                                                         * click new game and launch the model to devtest any changes while
                                                                         * working on a feature. this should should be fixed to going through
                                                                         * a presenter instead of creating a new model eventually
                                                                         */
                                                                        //GameModel game = new GameModel();

                                                                    }
                                                                }
    );

        /*loadButton.addListener(new ClickListener(){
                                   @Override
                                   public void clicked(InputEvent event, float x, float y) {

                                   }
                               }
        );*/

        exitButton.addListener(new ClickListener(){
                                   @Override
                                   public void clicked(InputEvent event, float x, float y) {
                                       //parent.dispose();
                                       Gdx.app.exit();
                                   }
                               }
        );

        rulesButton.addListener(new ClickListener(){
                                    @Override
                                    public void clicked(InputEvent event, float x, float y) {
                                        ((QuestCards)Gdx.app.getApplicationListener()).ScreenAssign("rulesDisplay");
                                        //dispose();
                                        //gsm.push(new RulesPresenter(gsm));
                                    }
                                }
        );
    }

    public void draw(Batch batch, float alpha) {
        Gdx.input.setInputProcessor(stage);
        this.stage.act(Gdx.graphics.getDeltaTime());
        //batch.draw(background,0,0,1920,1080);
        this.stage.draw();
    }

    public void dispose(){
        stage.dispose();
        background.dispose();
    }



}

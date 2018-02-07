package com.comp_3004.quest_cards.core;

import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


public class MenuState extends State {
    private Texture background;
    //private Music menuMusic;
    //private Texture play;
    private Stage stage;
    private Table startTable;
    private Texture fadeInText;
    private TweenManager tweenManager;
    private Skin skin;
    private TextButton startButton,loadButton,rulesButton,exitButton;
    //private Sound music;
    //private Sound click; //TODO


    public MenuState(GameStateManager gsm)
    {super(gsm);
    //menuMusic = Gdx.audio.newMusic(Gdx.files.internal("music/MainMenu.mp3"));
    tweenManager = new TweenManager();
    background = new Texture("bg-castle.jpg");
    stage = new Stage(new ScreenViewport());
    Gdx.input.setInputProcessor(stage);

    //play = new Texture("NewGame.png");

        //Init
        skin = new Skin(Gdx.files.internal("buttonSkin.json"));
        startButton = new TextButton("New Game",skin,"default");
        loadButton = new TextButton("Load Game",skin,"default");
        rulesButton = new TextButton("Rules",skin,"default");
        exitButton = new TextButton("Exit",skin,"default");
        startTable = new Table();
        //Visual Configuration
        startTable.setWidth(250);
        startTable.align(Align.left|Align.bottom);
        startTable.setPosition(100,100);
        startButton.setSize(250,100);
        loadButton.setSize(250,100);
        rulesButton.setSize(250,100);
        exitButton.setSize(250,100);
        //LISTENERS
        startButton.addListener(new ClickListener(){
                                    @Override
                                    public void clicked(InputEvent event, float x, float y) {
                                        ((Game)Gdx.app.getApplicationListener()).toPlay();
                                        Gdx.app.log("New Game","CLICKED!");
                                        //startDialog.show(mainMenu);
                                        
                                        /* added an instance of GameModel here just so I can run the app,
                                         * click new game and launch the model to devtest any changes while
                                         * working on a feature. this should should be fixed to going through 
                                         * a presenter instead of creating a new model eventually
                                         */
                                        GameModel game = new GameModel();

                                    }
                                }
        );
        loadButton.addListener(new ClickListener(){
                                   @Override
                                   public void clicked(InputEvent event, float x, float y) {

                                   }
                               }
        );
        exitButton.addListener(new ClickListener(){
                                   @Override
                                   public void clicked(InputEvent event, float x, float y) {
                                       dispose();
                                       Gdx.app.exit();
                                   }
                               }
        );
        rulesButton.addListener(new ClickListener(){
                                    @Override
                                    public void clicked(InputEvent event, float x, float y) {
                                        ((Game)Gdx.app.getApplicationListener()).toRules();
                                        dispose();
                                        //gsm.push(new RulesState(gsm));
                                    }
                                }
        );

        //Actor Allocation
        startTable.add(startButton);
        startTable.row();
        startTable.add(loadButton);
        startTable.row();
        startTable.add(rulesButton);
        startTable.row();
        startTable.add(exitButton);
        startTable.row();
        stage.addActor(startTable);
        startTable.setFillParent(true);

        //menuMusic.setVolume(0.5f);                 // sets the volume to half the maximum volume
        //menuMusic.setLooping(true);                // will repeat playback until music.stop() is called
        //menuMusic.play();
    Gdx.app.log("MenuState(gsm)","MenuState constructor runs");
    }


    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {
        stage.act(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        this.stage.act(Gdx.graphics.getDeltaTime());

        sb.begin();
        sb.draw(background, 0,0,Game.WIDTH,Game.HEIGHT);
        //sb.draw(play,(Game.WIDTH/2-500/2),(Game.HEIGHT/2-300/2),500,300);
        sb.end();

        this.stage.draw();
    }

    @Override
    public void dispose() {
        background.dispose();
        stage.dispose();
        //play.dispose();
    }



}

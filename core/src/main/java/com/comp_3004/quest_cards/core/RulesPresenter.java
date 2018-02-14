package com.comp_3004.quest_cards.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.comp_3004.quest_cards.gui.Config;

public class RulesPresenter extends Group {
    private QuestCards parent;
    private AssetManager manager;
    private Texture [] rules;
    private Image ruleImage;
    private Stage rulesStage;
    private Table rulesTable;
    private Skin skin;
    private TextButton nextButton,prevButton,toMenuButton;
    public int pageNum = 1000;

    protected RulesPresenter(QuestCards parent) {
        this.parent = parent;
       this.manager = parent.getAssetManager();
       loadAssets();
    }

    private void loadAssets(){
        skin = manager.get("skins/uiskin.json");
        rulesTable = new Table();
        rulesStage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(rulesStage);

        nextButton = new TextButton("Next Page",skin, "default");
        prevButton = new TextButton("Previous Page",skin,"default");
        toMenuButton = new TextButton("Back to Menu",skin,"default");
        //Load In the Rules
        rules = new Texture [10];
        rules[0] = new Texture("rules/0.png");
        rules[1] = new Texture("rules/1.png");
        rules[2] = new Texture("rules/2.png");
        rules[3] = new Texture("rules/3.png");
        rules[4] = new Texture("rules/4.png");
        rules[5] = new Texture("rules/5.png");
        rules[6] = new Texture("rules/6.png");
        rules[7] = new Texture("rules/7.png");
        rules[8] = new Texture("rules/8.png");
        rules[9] = new Texture("rules/9.png");
        ruleImage = new Image(rules[0]);
        //Visual Configuration
        ruleImage.setBounds(0,0,1920,1080);
        rulesTable.setWidth(250);
        rulesTable.align(Align.right|Align.bottom);
        rulesTable.setPosition(-100,50);
        nextButton.setSize(250,100);
        prevButton.setSize(250,100);
        toMenuButton.setSize(250,100);

        //Set the Listeners
        nextButton.addListener(new ClickListener(){
                                   @Override
                                   public void clicked(InputEvent event, float x, float y) {
                                       pageNum++;
                                       //startDialog.show(mainMenu);

                                   }
                               }
        );
        prevButton.addListener(new ClickListener(){
                                   @Override
                                   public void clicked(InputEvent event, float x, float y) {
                                       pageNum--;
                                   }
                               }
        );
        toMenuButton.addListener(new ClickListener(){
                                     @Override
                                     public void clicked(InputEvent event, float x, float y) {
                                         //dispose();
                                         ((QuestCards)Gdx.app.getApplicationListener()).ScreenAssign("mainMenu");
                                     }
                                 }
        );
        rulesTable.add(nextButton);
        rulesTable.row();
        rulesTable.add(prevButton);
        rulesTable.row();
        rulesTable.add(toMenuButton);
        rulesStage.addActor(ruleImage);
        rulesStage.addActor(rulesTable);
        rulesTable.setFillParent(true);
    }


    public void draw(Batch batch, float alpha) {
        Gdx.input.setInputProcessor(rulesStage);
        this.rulesStage.act(Gdx.graphics.getDeltaTime());
        ruleImage.setDrawable(new TextureRegionDrawable(new TextureRegion(rules[pageNum%10])));
        batch.draw(rules[pageNum%10], 0,0, Config.VIRTUAL_WIDTH,Config.VIRTUAL_HEIGHT);

        this.rulesStage.draw();

    }

    public void dispose() {
        for(int i=0;i<10;i++)
        {rules[i].dispose();}

        rulesStage.dispose();

    }
}

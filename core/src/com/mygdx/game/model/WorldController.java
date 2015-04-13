package com.mygdx.game.model;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.utils.ArrayMap;
import com.mygdx.game.*;
import com.mygdx.game.Entities.Dude;
import com.mygdx.game.maps.MainTileMap;
import com.mygdx.game.screens.gui.Display;
import com.mygdx.game.util.Constants;


/**
 * Created by Ian on 12/21/2014.
 *
 * This class is designed to hold the logic of the game elements and provide access
 * to the other modules as well as control input
 *
 */
public class WorldController extends InputAdapter {

    private static final String TAG = WorldController.class.getName();


    public int score;
    public Level level;
    public int lives;


    //not bein used
    public MainTileMap mainMap;


    /*
    * Creates the camera helper, a utililty class for camera manipulation
    * */
    public CameraHelper cameraHelper;

    /*
    * The class for displaying the UI
    * Is placed in controller so input has access
    * */
    public Display display;

    /*
    * Actor initialization
    * Dude is only active sprite 3/4/2015
    * */
    public Dude dude;

    public Sprite[] spriteGroup;
    public int selectedSprite;

    /*
    * Environment intitialization
    * Not being used
    * */
    public Sprite[] groundGroup;


    /*
    * Default constructor
    * */
    public WorldController () {
        init();
    }


    /*
    * Build class
    * */
    private void init() {
        Gdx.input.setInputProcessor(this);

        cameraHelper = new CameraHelper();
        cameraHelper.setPosition(Constants.GAME_WORLD / 2, Constants.GAME_WORLD/2);

        //Game info is set in Constants class
        lives = Constants.LIVES_START;

        /*Initiate everything*/
        initActors();
        initUI();
    }

    /*
    * Initiate Tiled Map
    * */
    public void initMap(){
        mainMap = new MainTileMap();
    }


    public void initUI(){
        display = new Display();
    }

    /*
    * Initiate level loader
    * TODO change to load tiled maps

    private void initLevel () {
        score = 0;
        level = new Level(Constants.LEVEL_01);
    }

    * */


    /*
    * Create actors, for now specifically the dude actor
    * */
    private void initActors(){
        dude = new Dude();
        dude.setRegion(Assets.instance.dudeAsset.body);
    }


     /*
    * Create model and needed resources
    * This is the earliest attemp at game stuff
    * Uncomment code in other classes to see what it does
    * */
    private void initTestObjects(){

        //array to hold actors
        spriteGroup = new Sprite[5];

        //array to hold environment
        groundGroup = new Sprite[20];

        //add textures
        ArrayMap<String, TextureRegion> regions = new ArrayMap<String, TextureRegion>();
        regions.put("face", Assets.instance.dudeAsset.body);
        regions.put("ground", Assets.instance.ground.ground);


        /*
        * Go through the ground objects
        * */
        for(int i = 0; i < groundGroup.length; i++){
            Sprite spr = new Sprite(regions.get("ground"));

            int x = 0;
            int y = 0;

            spr.setSize(1, 1);
            spr.setScale(1, 1);

            if(i < 5){
                y = 0;
            }

            if(i < 9){
                y = 1;
            }

            spr.setPosition(i, y);
            groundGroup[i] = spr;

        }


        /*
        * Go through sprites and make everything which is needed
        * */
        for (int i = 0; i < spriteGroup.length; i++) {
            Sprite spr = new Sprite(regions.get("face"));

            // Define sprite size to be 1m x 1m in game world
            //TODO learn more about using m as unit of space in game world
            spr.setSize(1, 1);

            spr.setScale(1, 1);

            // Set origin to sprite's center
            spr.setOrigin(spr.getWidth() / 2.0f, spr.getHeight() / 2.0f);

            // Calculate random position for sprite
            float randomX = MathUtils.random(-2.0f, 2.0f);
            float randomY = MathUtils.random(-2.0f, 2.0f);
            spr.setPosition(randomX, randomY);

            // Put new sprite into array
            spriteGroup[i] = spr;
        }



        // Set first sprite as selected one
        selectedSprite = 0;
     }






    /*
    * This method is called constantly and updates the model and view
    *
    * Important note about handleDebugInput()
    * this class is called with a helper class to ensure all
    * input has been processed before rendering this class needs to be
    * called first to ensure user input is handled BEFORE logic is executed
    * ORDER IS IMPORTANT
    * */
    public void update (float deltaTime) {

        //updateTestObjects(deltaTime);

        handleDebugInput(deltaTime);
        cameraHelper.update(deltaTime);
        display.update();
    }


    /*
    * Updates the local model from earlier code
    * */
    private void updateTestObjects(float deltaTime) {
        // Get current rotation from selected sprite
        //float rotation = spriteGroup[selectedSprite].getRotation();
        // Rotate sprite by 90 degrees per second
        //rotation += 90 * deltaTime;
        // Wrap around at 360 degrees
        // TODO ???
        //rotation %= 360;
        // Set new rotation value to selected sprite
        //spriteGroup[selectedSprite].setRotation(rotation);
    }



    /*
    * Enables all kinds of awesome things that we can control when
    * used in conjunction with the CameraHelper class
    * */
    private void handleDebugInput (float deltaTime) {
        if (Gdx.app.getType() != Application.ApplicationType.Desktop) return;

        // Selected Sprite Controls
        float sprMoveSpeed = 10 * deltaTime;
        if (Gdx.input.isKeyPressed(Keys.A)) dude.position.x -= sprMoveSpeed;
        if (Gdx.input.isKeyPressed(Keys.D)) dude.position.x += sprMoveSpeed;
        if (Gdx.input.isKeyPressed(Keys.W)) dude.position.y += sprMoveSpeed;
        if (Gdx.input.isKeyPressed(Keys.S)) dude.position.y -= sprMoveSpeed;

        if (Gdx.input.isKeyPressed(Keys.X)) cameraHelper.setPosition(10,10);

        // Camera Controls (move)
        float camMoveSpeed = 5 * deltaTime;
        float camMoveSpeedAccelerationFactor = 5;
        if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) camMoveSpeed *=
                camMoveSpeedAccelerationFactor;
        if (Gdx.input.isKeyPressed(Keys.LEFT)) moveCamera(-camMoveSpeed,
                0);
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) moveCamera(camMoveSpeed,
                0);
        if (Gdx.input.isKeyPressed(Keys.UP)) moveCamera(0, camMoveSpeed);

        if (Gdx.input.isKeyPressed(Keys.DOWN)) moveCamera(0,
                -camMoveSpeed);
        if (Gdx.input.isKeyPressed(Keys.BACKSPACE))
            cameraHelper.setPosition(0, 0);

        // Camera Controls (zoom)
        float camZoomSpeed = 1 * deltaTime;
        float camZoomSpeedAccelerationFactor = 5;
        if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) camZoomSpeed *=
                camZoomSpeedAccelerationFactor;
        if (Gdx.input.isKeyPressed(Keys.COMMA))
            cameraHelper.addZoom(camZoomSpeed);
        if (Gdx.input.isKeyPressed(Keys.PERIOD)) cameraHelper.addZoom(
                -camZoomSpeed);
        if (Gdx.input.isKeyPressed(Keys.SLASH)) cameraHelper.setZoom(1);
    }



    /*
    * Uses CameraHelper to control the camera position
    * */
    private void moveCamera (float x, float y) {
        x += cameraHelper.getPosition().x;
        y += cameraHelper.getPosition().y;



        cameraHelper.setPosition(x, y);
    }


    /*
    * Method to actually move a game object
    * TODO fold this and other sprite methods to a new class
    * */
    private void moveSelectedSprite (float x, float y) {
        spriteGroup[selectedSprite].translate(x, y);
    }


    /*
    * Inputs
    * */

    @Override
    public boolean keyUp (int keycode) {
        // Reset game world
        if (keycode == Keys.R) {
            init();
            Gdx.app.debug(TAG, "Game world resetted");
        }

        //change text
        if (keycode == Keys.B){
            display.setText(dude.randomText());

        }


        // Select next sprite
        else if (keycode == Keys.SPACE) {
            selectedSprite = (selectedSprite + 1) % spriteGroup.length;

            // Update camera's target to follow the currently
            // selected sprite
            if (cameraHelper.hasTarget()) {
                cameraHelper.setTarget(spriteGroup[selectedSprite]);
            }

            Gdx.app.debug(TAG, "Sprite #" + selectedSprite + " selected");
            }
            // Toggle camera follow
            else if (keycode == Keys.ENTER) {
                cameraHelper.setTargetAbstract(cameraHelper.hasTarget() ? null : dude);
                Gdx.app.debug(TAG, "Camera follow enabled: " +
                        cameraHelper.hasTargetAbstract());
            }

        return false;
    }
}

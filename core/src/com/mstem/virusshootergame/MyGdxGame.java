package com.mstem.virusshootergame;

//import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

/**
 * MainClass, creates, render
 */
public class MyGdxGame extends Game {
    public static final int VIEWPORT_WIDTH = 1000;
    public static final int VIEWPORT_HEIGHT = 558;
    SpriteBatch batch;
    private OrthographicCamera camera;
	Texture background, gunTexture;
    private Sprite gunSprite;
    private AnimatedSprite gunAnimated;
    private ShotManager shotManager;
    private Target target;
    private CollisionDetect collisionDetectGood;
    private CollisionDetect collisionDetectBad;
    private ArrayList<Target> goodTargets = new ArrayList<Target>();
    private ArrayList<Target> badTargets = new ArrayList<Target>();
    private String[] targetGoodNames = {"ad.png"} ;
    private String[] targetBadNames = {"adware.png", "spyware.png"};

    /**
     * create
     */
	@Override
	public void create () {

        //Set up the camera with a dimension;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);

        //for drawing texture and sprites on the screen
		batch = new SpriteBatch();

        //background texture
		background = new Texture(Gdx.files.internal("android/assets/background_mosaic.jpg"));

        //gun sprite and initial position
        gunTexture = new Texture(Gdx.files.internal("android/assets/data/gun1.png"));
        gunSprite = new Sprite(gunTexture);
        gunAnimated = new AnimatedSprite(gunSprite);
        gunAnimated.setPosition(background.getWidth()/2-(gunSprite.getWidth()/2),30);

        //bullet texture and initialization for action
        Texture shotTexture = new Texture(Gdx.files.internal("android/assets/data/bullet.png"));
        shotManager = new ShotManager(shotTexture);

        //Target setup
        for(int i = 0; i < targetGoodNames.length; i++) {
            Texture targetTexture = new Texture(Gdx.files.internal("android/assets/data/" + targetGoodNames[i]));
            target = new Target(targetTexture);
            goodTargets.add(target);
        }
        for(int a = 0; a < targetBadNames.length; a++) {
            Texture adwareTexture = new Texture(Gdx.files.internal("android/assets/data/" + targetBadNames[a]));
            target = new Target(adwareTexture);
            badTargets.add(target);
        }

        //Collision Detection
        collisionDetectGood = new CollisionDetect(gunAnimated,goodTargets,shotManager);
        collisionDetectBad = new CollisionDetect(gunAnimated, badTargets, shotManager);

	}

    @Override
    public void dispose() {
        batch.dispose();
    }

    /**
     * render the screen
     */
	@Override
	public void render () {
//        //initialize window
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //drawing objects to the screen
        batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(background, 0, 0);
        gunAnimated.draw(batch);
        for(Target good: goodTargets){
            good.draw(batch);
        }
        for(Target bad: badTargets) {
            bad.draw(batch);
        }
        shotManager.draw(batch);
		batch.end();

        //check user input
        handleInput();
        //actions in response to inputs
        update();

        //check collision
        collisionDetectGood.hasCollide();
        collisionDetectBad.hasCollide();
	}

    /**
     * update
     */
    public void update() {
        gunAnimated.move();
        for(Target good: goodTargets) {
            good.update();
        }
        for(Target bad: badTargets) {
            bad.update();
        }
//        target.update();
        shotManager.update();

    }

    /**
     * check for user input and reacts
     */
    private void handleInput() {
        //if left or a is pressed
        if (gunSprite.getX() > 0) {
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) ||
                    Gdx.input.isKeyPressed(Input.Keys.A)) {
                if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                    gunAnimated.moveLeft(500);
                }
                else {
                    gunAnimated.moveLeft(200);
                }
            }
        }
        //if right or d is pressed
        if(gunSprite.getX() < background.getWidth() - gunSprite.getWidth()){
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) ||
                    Gdx.input.isKeyPressed(Input.Keys.D)) {
                if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                    gunAnimated.moveRight(500);
                }
                else {
                    gunAnimated.moveRight(200);
                }
            }
        }
        //if up or space or w is pressed
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE) ||
                Gdx.input.isKeyPressed(Input.Keys.UP) ||
                Gdx.input.isKeyPressed(Input.Keys.W)) {
            shotManager.fire(gunAnimated.getX());
        }
    }

    @Override
    public void resize(int width, int height) {

    }

}//End of MyGdxGame.java

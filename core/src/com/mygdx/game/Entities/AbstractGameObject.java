package com.mygdx.game.Entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by Ian on 1/21/2015.
 */

public abstract class AbstractGameObject {

    /*
    * Fields for a basic 2d object on a flat plane
    * */
    public Vector2 position;
    public Vector2 dimension;
    public Vector2 origin;
    public Vector2 scale;
    public float rotation;

    /*
    * Fields for simulating a physical model on an object
    * info on page 168
    * */
    public Vector2 velocity;
    public Vector2 terminalVelocity;
    public Vector2 friction;
    public Vector2 acceleration;
    public Rectangle bounds;

    int id = 0;

    /*
    * Default constructor
    * */
    public AbstractGameObject() {

        position = new Vector2();
        dimension = new Vector2(1, 1);
        origin = new Vector2();
        scale = new Vector2(1, 1);
        rotation = 0;

        //from actor creation shit
        velocity = new Vector2();
        terminalVelocity = new Vector2(1, 1);
        friction = new Vector2();
        acceleration = new Vector2();
        bounds = new Rectangle();
    }

    /*
    * Moves an object based on physical bounds of the game world
    * */
    public void update(float deltaTime) {

        //updateMotionX(deltaTime);
        //updateMotionY(deltaTime);
        // Move to new position
        //position.x += velocity.x * deltaTime;
        //position.y += velocity.y * deltaTime;
    }


    public abstract void render(SpriteBatch batch);

    /*
    * the two update motion methods will move an object
    * an object with velocity applied also has friction applied eah time
    * period
    *
    * TODO this is the section on velocity
    * */
    protected void updateMotionX (float deltaTime) {
        if (velocity.x != 0) {
            // Apply friction
            if (velocity.x > 0) {
                velocity.x =
                        Math.max(velocity.x - friction.x * deltaTime, 0);
            } else {
                velocity.x =
                        Math.min(velocity.x + friction.x * deltaTime, 0);
            }
        }

        // Apply acceleration
        velocity.x += acceleration.x * deltaTime;

        // Make sure the object's velocity does not exceed the
        // positive or negative terminal velocity
        velocity.x = MathUtils.clamp(velocity.x,
                -terminalVelocity.x, terminalVelocity.x);
    }

    protected void updateMotionY (float deltaTime) {
        if (velocity.y != 0) {
            // Apply friction
            if (velocity.y > 0) {
                velocity.y =
                        Math.max(velocity.y - friction.y * deltaTime, 0);
            } else {
                velocity.y =
                        Math.min(velocity.y + friction.y * deltaTime, 0);
            }
        }

        // Apply acceleration
        velocity.y += acceleration.y * deltaTime;

        // Make sure the object's velocity does not exceed the
        // positive or negative terminal velocity
        velocity.y = MathUtils.clamp(velocity.y,
                -terminalVelocity.y, terminalVelocity.y);
    }

    public Vector2 getPosition(){
        return position;
    }

    public String toString(){
        return "ID: " + id + "\nPosition: " + position.toString();
    }
}
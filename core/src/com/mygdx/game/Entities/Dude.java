package com.mygdx.game.Entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.Entities.AbstractGameObject;
//import com.mygdx.util.Constants/;

/**
 * Created by Ian on 2/26/2015.
 */
public class Dude extends AbstractGameObject {


    /*
    * Dude is the little guy sprite being built
    * This is the player character class and has a long way to go
    * */


    /*
    * To hold a texture
    * */
    private TextureRegion dudeTexture;

    /*
    * Basic dialog tree proof, will cycle text
    * */
    String[] dialog = {"Careful Man",
        "The dude is not, in",
        "El Duderino",
        "The Kanootsins",
        "They say my work is..."};
    int index = 0;

    /*
    * set dude spawn point and make dude
    * */
    public Dude() {
        this.position.set(10, 10);
    }

    /*
    * Set dude current text
    * */
    public String randomText(){
        index += 1;
        return dialog[index];
    }


    //I HAVE NO IDEA WHAT I DID DOWN HERE

    /*
    //public void setRegion (TextureRegion region) {
    //    regCloud = region;
    //}



    /*private void init () {

        dudeTexture = Assets.instance.dude.body;
        dimension.set(1, 1);
        // position
        Vector2 pos = new Vector2();
        pos.x = Constants.GAME_WORLD/2; // postition at center of map
        pos.y = Constants.GAME_WORLD/2; //

    }*/

    /*private Dude spawnDude () {

        Dude dude = new Dude();




        return dude;
    }*/

    /*public Dude spawnDude(){



    }*/


    public void setRegion (TextureRegion region) {

        dudeTexture = region;
    }


    /*
    * note, the render has its own texture which is grabbed all the time
    * it comes from the spritebatch but it just checks the current texture all the time
    * */
    @Override
    public void render (SpriteBatch batch) {
        TextureRegion reg = null;


        reg = dudeTexture;
        batch.draw(reg.getTexture(),
                position.x, position.y,
                origin.x, origin.y,
                dimension.x, dimension.y,
                scale.x, scale.y,
                rotation,
                reg.getRegionX(), reg.getRegionY(),
                reg.getRegionWidth(), reg.getRegionHeight(),
                false, false);

        // Reset color to white
        batch.setColor(1, 1, 1, 1);

    }
}
package com.theironyard.superkoalio;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SuperKoalio extends ApplicationAdapter {
    SpriteBatch batch;
    TextureRegion stand, jump;
    Animation walk;

    static final int WIDTH = 18; //final makes it constant
    static final int HEIGHT = 26;
    static final int DRAW_WIDTH = WIDTH * 3;
    static final int DRAW_HEIGHT = HEIGHT * 3;
    static final float MAX_VELOCITY = 500;
    static final float MAX_JUMP_VELOCITY = 2000;
    static final int GRAVITY = -50;

    float x, y, xv, yv, time;
    //so can't hold down on up:
    boolean canJump, faceRight = true;



    @Override
    public void create() {
        batch = new SpriteBatch();
        Texture sheet = new Texture("koalio.png");
        TextureRegion[][] tiles = TextureRegion.split(sheet, WIDTH, HEIGHT);
        stand = tiles[0][0]; //first column in row, first aisle in row
        jump =tiles [0][1];
        walk = new Animation(0.1f, tiles [0][2], tiles [0][3], tiles [0][4]);
    }


    @Override
    public void render() {
        time += Gdx.graphics.getDeltaTime();

        move();

        TextureRegion img;
        if (y > 0) {
            img = jump;
        } else if (xv != 0) {
            img = walk.getKeyFrame(time, true);
        }   else {
            img = stand;
        }



        Gdx.gl.glClearColor(0.5f, 0.5f, 1, 1); //background color
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        if (faceRight) {
            batch.draw(img, x, y, DRAW_WIDTH, DRAW_HEIGHT);
        }
        else { //to go left
            batch.draw(img, x + DRAW_WIDTH, y, DRAW_WIDTH * -1, DRAW_HEIGHT);
        }


        batch.end();
    }

    float decelerate(float velocity, float deceleration) { //broke into separate method
        // dampening effect.the closer to 1 the slower the deceleration
        velocity = velocity * deceleration;
        if (Math.abs(velocity) < 1) {
            velocity = 0;

        }
        return velocity;
    }

    void move() {
        if (Gdx.input.isKeyPressed(Input.Keys.UP) && canJump) {
            yv = MAX_JUMP_VELOCITY;
            canJump = false; //once jump can't jump (if hold key down)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            yv = MAX_VELOCITY * -1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            xv = MAX_VELOCITY;
            faceRight = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            xv = MAX_VELOCITY * -1;
            faceRight = false;
        }

        yv += GRAVITY;

        y += yv * Gdx.graphics.getDeltaTime(); //delta time - amt of time since last frame ran. yv= # pixels movein 1 second
        x += xv * Gdx.graphics.getDeltaTime();

        if (y < 0) { //so doesn'tmove past bottom
            y=0;
            canJump = true;
        }

        yv = decelerate(yv, 0.9f);
        xv = decelerate(xv, 0.8f);


    }
}



package com.theironyard.superkoalio;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SuperKoalio extends ApplicationAdapter {
    SpriteBatch batch;

    @Override
    public void create () {
        batch = new SpriteBatch();
    }

    @Override
    public void render () {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        // draw here
        batch.end();
    }
}
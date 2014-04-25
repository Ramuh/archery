package de.ramuh.game.engine.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextRenderer {

	private BitmapFont font;
	private SpriteBatch batch;
	private float transX;
	private float transY;
	private int tilesize;
	
	public TextRenderer(SpriteBatch batch, String fontFileName, String fontTextureName, int tilesize) {
		this.batch = batch;
		
		Texture texture = new Texture(Gdx.files.internal(fontTextureName), false);
		
		this.font = new BitmapFont(Gdx.files.internal(fontFileName), new TextureRegion(texture));
		
		transX = Gdx.graphics.getWidth()/2f;
		transY = Gdx.graphics.getHeight()/2f;
		
		this.tilesize = tilesize;
	}
	
	public void render(CharSequence seq, float x, float y) {				
		font.draw(batch, seq, x-transX, -y+transY);
	}
	
	public void renderTileAdjusted(CharSequence seq, float tx, float ty) {				
		render(seq, tx*tilesize, ty*tilesize);
	}

	public void start() {
		batch.begin();		
	}

	public void done() {
		batch.end();
	}
}

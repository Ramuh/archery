package de.ramuh.game.engine;

import java.util.Stack;

import javax.swing.JFrame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;

import de.ramuh.game.engine.input.DebugInputHandler;
import de.ramuh.game.engine.input.InputHandler;
import de.ramuh.game.engine.scene.Constants;
import de.ramuh.game.engine.scene.Scene;
import de.ramuh.game.engine.scene.SceneFactory;
import de.ramuh.game.engine.systems.EventManager;
import de.ramuh.game.engine.systems.TextRenderer;

public class Engine implements ApplicationListener {
		
	// graphics
	private OrthographicCamera camera;
	public static SpriteBatch batch;
	
	public static int widthFactor;
	public static int heightFactor;
	
	// FPS and timers
	private long currTime;
	private long elapsedTime;
	private long lastFps;
	private boolean showFpsConsole = false;
	private boolean showFpsText = true;	
	
	// scene mgmt
	public static Scene curr;
	private Stack<Scene> sceneStack;

	private Scene startScene;

	public static int w;
	public static int h;

	// managers
	public static SceneFactory sf;
	public static TweenManager tweenManager;
	public static TextRenderer font;
	public static JFrame frame;
	
	protected static final Logger LOG = LoggerFactory.getLogger(EventManager.class);
	
	public Engine(Scene startScene) {
		this.startScene = startScene;
	}
		
	@Override
	public void create() {
				
		// Setup Logging		
		LOG.info("Starting Game");
		
		// init gfx
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();
				
		Engine.resizeWindow(w, h);
		
		camera = new OrthographicCamera(w, h);
		batch = new SpriteBatch();
		
		Gdx.input.setInputProcessor(new GestureDetector(new InputHandler()));
		
		// Init Times and FPS
		this.currTime = System.nanoTime();
		this.elapsedTime = this.currTime;
		this.lastFps = this.currTime;

		// Init Managers & Factories
		tweenManager = new TweenManager();
		sf = new SceneFactory();
		//font = new TextRenderer(batch, "data/fonts/hiero1.fnt", "data/fonts/hiero1.png", 32);
		font = new TextRenderer(batch, "data/fonts/f48.fnt", "data/fonts/f48.png", 48);
		// Init Scene Stack
		this.sceneStack = new Stack<Scene>();
		this.push(startScene); // we put a StartScene in, hopefully nobody pushes out too far ;)
		
		// DEBUG
		if(Constants.DEBUG) {
			Gdx.input.setInputProcessor(new DebugInputHandler());
		}
	}

	
	@Override
	public void dispose() {
		batch.dispose();
	}

	////
	
	@Override
	public void render() {		
		this.elapsedTime = System.nanoTime();
		if (this.elapsedTime - this.currTime > Constants.DELTA_NANO)
			tick();
		this.currTime = this.elapsedTime;
		
		if (showFpsConsole && (this.currTime - this.lastFps) > 1000000000L) {
			System.out.println("FPS: " + Gdx.graphics.getFramesPerSecond());
			this.lastFps = this.currTime;
		}
		
		Gdx.gl.glClearColor(166/255f, 225/255f, 255/255f, 255/255f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		// set up stuff and draw
		batch.setProjectionMatrix(camera.combined);
		batch.enableBlending();
		batch.begin();		
		draw();	

		if (showFpsText) {
			Engine.font.renderTileAdjusted("FPS: " + Gdx.graphics.getFramesPerSecond(), 35, 0);
		}

		batch.end();
	}

	////
	private void tick() {
		tweenManager.update(Constants.DELTA); // update 60 times a sec, roughly
		this.sceneStack.peek().tick();
	}
	private void draw() {
		this.sceneStack.peek().render();
	}
	
	////
	
	public void push(Scene scene) {
		this.sceneStack.push(scene);
		Engine.curr = scene;
		scene.init();
	}
	
	////
	
	@Override
	public void resize(int width, int height) {
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();
		widthFactor = w / width;
		heightFactor = h / height;
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	public static void resizeWindow(int width, int height) {
		if(frame != null) {
			frame.setVisible(false);
			frame.setSize(width, height);
		}
		Gdx.graphics.setDisplayMode(width, height, false);
		if(frame != null) {
			frame.setVisible(true);
		}
	}
}

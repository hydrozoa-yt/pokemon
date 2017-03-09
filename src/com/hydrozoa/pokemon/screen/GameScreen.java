package com.hydrozoa.pokemon.screen;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.hydrozoa.pokemon.PokemonGame;
import com.hydrozoa.pokemon.controller.DialogueController;
import com.hydrozoa.pokemon.controller.PlayerController;
import com.hydrozoa.pokemon.dialogue.Dialogue;
import com.hydrozoa.pokemon.dialogue.DialogueNode;
import com.hydrozoa.pokemon.model.Camera;
import com.hydrozoa.pokemon.model.DIRECTION;
import com.hydrozoa.pokemon.model.actor.PlayerActor;
import com.hydrozoa.pokemon.model.world.World;
import com.hydrozoa.pokemon.model.world.script.WorldInterface;
import com.hydrozoa.pokemon.screen.renderer.WorldRenderer;
import com.hydrozoa.pokemon.screen.transition.Action;
import com.hydrozoa.pokemon.screen.transition.BattleBlinkTransition;
import com.hydrozoa.pokemon.screen.transition.BattleTransition;
import com.hydrozoa.pokemon.screen.transition.FadeInTransition;
import com.hydrozoa.pokemon.screen.transition.FadeOutTransition;
import com.hydrozoa.pokemon.ui.DialogueBox;
import com.hydrozoa.pokemon.ui.OptionBox;
import com.hydrozoa.pokemon.util.AnimationSet;
import com.hydrozoa.pokemon.util.MapUtil;

/**
 * @author hydrozoa
 */
public class GameScreen extends AbstractScreen implements WorldInterface {
	
	private InputMultiplexer multiplexer;
	private DialogueController dialogueController;
	private PlayerController playerController;
	
	private HashMap<String, World> worlds = new HashMap<String, World>();
	private World world;
	private PlayerActor player;
	private Camera camera;
	private MapUtil mapUtil;
	
	private SpriteBatch batch;
	
	private Viewport gameViewport;
	
	private WorldRenderer worldRenderer;
	
	private int uiScale = 2;
	
	private Stage uiStage;
	private Table root;
	private DialogueBox dialogueBox;
	private OptionBox optionsBox;
	
	private Dialogue dialogue;

	public GameScreen(PokemonGame app) {
		super(app);
		gameViewport = new ScreenViewport();
		batch = new SpriteBatch();
		
		TextureAtlas atlas = app.getAssetManager().get("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);
		
		AnimationSet animations = new AnimationSet(
				new Animation(0.3f/2f, atlas.findRegions("brendan_walk_north"), PlayMode.LOOP_PINGPONG),
				new Animation(0.3f/2f, atlas.findRegions("brendan_walk_south"), PlayMode.LOOP_PINGPONG),
				new Animation(0.3f/2f, atlas.findRegions("brendan_walk_east"), PlayMode.LOOP_PINGPONG),
				new Animation(0.3f/2f, atlas.findRegions("brendan_walk_west"), PlayMode.LOOP_PINGPONG),
				atlas.findRegion("brendan_stand_north"),
				atlas.findRegion("brendan_stand_south"),
				atlas.findRegion("brendan_stand_east"),
				atlas.findRegion("brendan_stand_west")
		);
		
		mapUtil = new MapUtil(app.getAssetManager(), this, animations);
		worlds.put("test_level", mapUtil.loadWorld1());
		worlds.put("test_indoors", mapUtil.loadWorld2());
		
		world = worlds.get("test_level");
		
		camera = new Camera();
		player = new PlayerActor(world, 4, 4, animations);
		world.addActor(player);
		
		initUI();
		
		multiplexer = new InputMultiplexer();
		
		playerController = new PlayerController(player);
		dialogueController = new DialogueController(dialogueBox, optionsBox);
		multiplexer.addProcessor(0, dialogueController);
		multiplexer.addProcessor(1, playerController);
		
		worldRenderer = new WorldRenderer(getApp().getAssetManager(), world);
		
		dialogue = new Dialogue();
		
		DialogueNode node1 = new DialogueNode("Hello!\nNice to meet you.", 0);
		DialogueNode node2 = new DialogueNode("Are you a boy or a girl?", 1);
		DialogueNode node3 = new DialogueNode("I knew you were boy all along.", 2);
		DialogueNode node4 = new DialogueNode("I knew you were girl all along.", 3);
		
		node1.makeLinear(node2.getID());
		node2.addChoice("Boy", 2);
		node2.addChoice("Girl", 3);
		
		dialogue.addNode(node1);
		dialogue.addNode(node2);
		dialogue.addNode(node3);
		dialogue.addNode(node4);
		
		//dialogueController.startDialogue(dialogue);
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void pause() {
		
	}
	
	@Override
	public void update(float delta) {
		/* DEBUG */
		if (Gdx.input.isKeyJustPressed(Keys.F9)) {
			getApp().startTransition(
					this, 
					getApp().getBattleScreen(), 
					new BattleBlinkTransition(4f, 4, Color.GRAY, getApp().getTransitionShader(), getApp().getTweenManager(), getApp().getAssetManager()), 
					new BattleTransition(2f, 10, true, getApp().getTransitionShader(), getApp().getTweenManager(), getApp().getAssetManager()),
					new Action() {
						@Override
						public void action() {
							
						}
					});
		}
		
		playerController.update(delta);
		dialogueController.update(delta);
		
		camera.update(player.getWorldX()+0.5f, player.getWorldY()+0.5f);
		world.update(delta);
		uiStage.act(delta);
	}

	@Override
	public void render(float delta) {
		gameViewport.apply();
		batch.begin();
		worldRenderer.render(batch, camera);
		batch.end();
		
		uiStage.draw();
	}

	@Override
	public void resize(int width, int height) {
		batch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
		uiStage.getViewport().update(width/uiScale, height/uiScale, true);
		gameViewport.update(width, height);
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(multiplexer);
	}
	
	private void initUI() {
		uiStage = new Stage(new ScreenViewport());
		uiStage.getViewport().update(Gdx.graphics.getWidth()/uiScale, Gdx.graphics.getHeight()/uiScale, true);
		//uiStage.setDebugAll(true);
		
		root = new Table();
		root.setFillParent(true);
		uiStage.addActor(root);
		
		dialogueBox = new DialogueBox(getApp().getSkin());
		dialogueBox.setVisible(false);
		
		optionsBox = new OptionBox(getApp().getSkin());
		optionsBox.setVisible(false);
		
		Table dialogTable = new Table();
		dialogTable.add(optionsBox)
						.expand()
						.align(Align.right)
						.space(8f)
						.row();
		dialogTable.add(dialogueBox)
						.expand()
						.align(Align.bottom)
						.space(8f)
						.row();
		
		
		root.add(dialogTable).expand().align(Align.bottom);
	}
	
	public void changeWorld(World newWorld, int x, int y, DIRECTION face) {
		player.changeWorld(newWorld);
		this.world = newWorld;
		player.teleport(x, y);
		player.refaceWithoutAnimation(face);
		this.worldRenderer.setWorld(newWorld);
		this.camera.update(player.getWorldX()+0.5f, player.getWorldY()+0.5f);
	}

	@Override
	public void changeLocation(World newWorld, int x, int y, DIRECTION facing, Color color) {
		getApp().startTransition(
				this, 
				this, 
				new FadeOutTransition(0.8f, color, getApp().getTweenManager(), getApp().getAssetManager()), 
				new FadeInTransition(0.8f, color, getApp().getTweenManager(), getApp().getAssetManager()), 
				new Action() {
					@Override
					public void action() {
						changeWorld(newWorld, x, y, facing);
						System.out.println("jup");
					}
				});
	}

	@Override
	public World getWorld(String worldName) {
		return worlds.get(worldName);
	}
}

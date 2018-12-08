package com.twentysecondhero.game.Stage;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.TimeUtils;
import com.twentysecondhero.game.Player.Player;
import com.twentysecondhero.game.Utility.GameData;
import com.twentysecondhero.game.Utility.TileLayerName;

public class GameStage {

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private Pool<Rectangle> rectPool = new Pool<Rectangle>() {
        @Override
        protected Rectangle newObject () {
            return new Rectangle(0, 0, 0, 0);
        }
    };
    private long stageMaxTime;
    private long stageTime = 0L;
    private boolean pickedUpCollectible = false;
    private boolean stopCounting = false;
    private Player player;
    private int width, height;

    public GameStage(String stagePath, float unitScale) throws Exception {
        map = new TmxMapLoader().load(stagePath);
        renderer = new OrthogonalTiledMapRenderer(map, 1 / unitScale);
        GameData.currentStage = this;

        MapProperties prop = map.getProperties();
        width = prop.get("width", Integer.class);
        height = prop.get("height", Integer.class);

        spawnPlayer();

        stageMaxTime = GameData.secondsToHundreds(GameData.stageTime);
    }

    public void update(OrthographicCamera camera){
        // Count up the stage timer and normalize it to hundreds for precision
        if(!stopCounting)
            stageTime += GameData.nanoToHundreds(TimeUtils.nanoTime());

        // System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>> STAGE TIME: " + stageTime + "    |    " + stageMaxTime);
        if(stageTime >= stageMaxTime && !stopCounting){
            lose();
            stopCounting = true;
        }

        renderer.setView(camera);
        renderer.render();
    }

    // Spawns the player on
    public void spawnPlayer() throws Exception {
        Array<Rectangle> tiles = getTiles(0, 0, width, height, getRectPool(), TileLayerName.Player);
        for (Rectangle tile : tiles) {
            player = new Player(tile.getPosition(new Vector2(tile.x, tile.y)), GameData.blockSize, GameData.blockSize * 2f);
            TiledMapTileLayer layer = (TiledMapTileLayer)GameData.currentStage.getMap().getLayers().get(TileLayerName.Player.toString());
            layer.setCell((int)tile.x, (int)tile.y, null);
        }
    }

    public Array<Rectangle> getTiles (int startX, int startY, int endX, int endY, Pool<Rectangle> rectPool, TileLayerName tileLayerName) throws Exception {
        if(GameData.currentStage != null) {
            TiledMapTileLayer layer = (TiledMapTileLayer) getMap().getLayers().get(tileLayerName.toString());
            Array<Rectangle> tiles = new Array<Rectangle>();
            rectPool.freeAll(tiles);
            tiles.clear();
            for (int y = startY; y <= endY; y++) {
                for (int x = startX; x <= endX; x++) {
                    TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                    if (cell != null) {
                        Rectangle rect = rectPool.obtain();
                        rect.set(x, y, 1, 1);
                        tiles.add(rect);
                    }
                }
            }
            return tiles;
        } else {
            throw new Exception("EXCEPTION > > > GameData.currentStage == null");
        }
    }

    public void win(){
        // TODO win the level
        System.out.println("> > > > > > . . . . . . . . YOU WIN!");
    }

    public void lose(){
        // TODO lose the level
        System.out.println("> > > > > > . . . . . . . . YOU WIN!");
    }

    public void touchCollectible(){
        pickedUpCollectible = true;
    }

    public TiledMap getMap(){
        return map;
    }

    public OrthogonalTiledMapRenderer getTileMapRenderer(){
        return renderer;
    }

    public long getStageTime() { return stageTime; }

    public Pool<Rectangle> getRectPool() {
        return rectPool;
    }

    public boolean isPickedUpCollectible() {
        return pickedUpCollectible;
    }

    public Player getPlayer(){
        return player;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}

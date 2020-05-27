package licence.projet.oblika.engine;

import java.util.List;

import licence.projet.oblika.engine.utils.LevelLoader;
import licence.projet.oblika.graphic.MasterRenderer;
import licence.projet.oblika.model.Camera;
import licence.projet.oblika.model.game_objects.drawable.hitboxed.platforms.MovingPlatform;
import licence.projet.oblika.model.level.LevelStructure;

public class Game {
    private MasterRenderer renderer;

    private Camera camera;

    private List<MovingPlatform> movingPlatforms;

    public Game() {
        renderer = new MasterRenderer();
        LevelStructure level = LevelLoader.parseLevel("level1");
        movingPlatforms = level.getMovingPlatformList();

        // camera = new ???();


    }

    public MasterRenderer getRenderer() {
        return renderer;
    }

    public void update() {
        for(MovingPlatform movingPlatform : movingPlatforms){
            movingPlatform.update();
        }
        // calcule de la physique toussa toussa
    }

    public void draw() {
        renderer.prepare();

        // renderer.camera(camera);
        renderer.movingPlatforms(movingPlatforms);

        //renderer.fixedPlatforms(...);

        renderer.background();

        renderer.finish();
    }
}

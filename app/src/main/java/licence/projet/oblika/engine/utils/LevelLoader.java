package licence.projet.oblika.engine.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import licence.projet.oblika.model.Point2D;
import licence.projet.oblika.model.drawable.hitboxed.platforms.FixedPlatform;
import licence.projet.oblika.model.drawable.hitboxed.platforms.MovingPlatform;
import licence.projet.oblika.model.level.LevelStructure;


public class LevelLoader {

    private static Context context;

    private enum Type {MOV_PLAT, FIX_PLAT, ENNEMY, STARTPOINT, ENDPOINT}

    private final static String splitter = "/";

    public static void init(Context context) {
        LevelLoader.context = context;
    }

    static LevelStructure parseLevel(String levelName) {

        LevelStructure level = new LevelStructure();

        int id = context.getResources().getIdentifier(levelName, "levels", context.getPackageName());
        InputStream is = context.getResources().openRawResource(id);

        InputStreamReader isReader = new InputStreamReader(is);
        BufferedReader reader = new BufferedReader(isReader);

        Type type = null;

        level.setLevelName(levelName);

        try {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals(splitter)) {
                    line = reader.readLine().toUpperCase().trim();
                    type = Type.valueOf(line);
                    continue;
                }

                String[] args = line.split(splitter);
                if (type != null) {
                    switch (type) {
                        case FIX_PLAT:
                            generateFixedPlateform(args, level);
                            break;
                        case ENNEMY:
                            generateEnemy(args, level);
                            break;
                        case ENDPOINT:
                            generateEndpoint(args, level);
                            break;
                        case MOV_PLAT:
                            generateMovingPlateform(args, level);
                            break;
                        case STARTPOINT:
                            generateStartpoint(args, level);
                            break;
                        default:

                    }
                } else {
                    throw new Exception("Invalid file level format");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return level;
    }

    private static void generateFixedPlateform(String[] args, LevelStructure level) throws Exception {
        float posX = Float.parseFloat(args[0]);
        float posY = Float.parseFloat(args[1]);
        verifyFloat(posX);
        verifyFloat(posY);
        level.addFixedPlatformList(new FixedPlatform(args[2], new Point2D(posX, posY)));
    }

    private static void generateEnemy(String[] args, LevelStructure level) {
        //TODO ajouter la génération d'ennemis selon leur type
    }

    private static void generateEndpoint(String[] args, LevelStructure level) {
        //TODO le point de sortie n'existe pas encore, il s'agit d'un rectangle avec une hitbox à atteindre
    }

    private static void generateMovingPlateform(String[] args, LevelStructure level) throws Exception {
        float posX = Float.parseFloat(args[0]);
        float posY = Float.parseFloat(args[1]);
        verifyFloat(posX);
        verifyFloat(posY);
        boolean isVertical = Boolean.parseBoolean(args[3]);
        level.addMovingPlatformList(new MovingPlatform(args[2], new Point2D(posX, posY), isVertical));
    }

    private static void generateStartpoint(String[] args, LevelStructure level) throws Exception {
        float posX = Float.parseFloat(args[0]);
        float posY = Float.parseFloat(args[1]);
        verifyFloat(posX);
        verifyFloat(posY);
        level.setStart(new Point2D(posX, posY));
    }

    private static void verifyFloat(float pos) throws Exception {
        if (pos < 0 || pos > 10) {
            throw new Exception("Invalid object placement in the level");
        }
    }

}
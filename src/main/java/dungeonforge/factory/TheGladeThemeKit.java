package dungeonforge.factory;

import dungeonforge.config.RandomSource;
import dungeonforge.core.Monster;
import dungeonforge.items.*;

public class TheGladeThemeKit implements ThemeKit{
    private final MonsterFactory factory;

    private static final String[] FLAVORS = {
           "Sirens blare out within the confined cage.",
            "You shoot up to the sky with alarming speed. Then you come to a sudden halt.",
            "The roof of the cage opens to reveal a bright stinging light.",
            "Once your vision focuses people from above huddle around the open cage to get a look at you, the new guy."
    };

    public TheGladeThemeKit(MonsterFactory factory) {
        this.factory = factory;
    }

    @Override
    public String themeName() {
        return "TheGlade";
    }

    @Override
    public Monster createMonster(int depth) {
        String id = RandomSource.getInstance().pick(factory.idsForTheme("theglade"));
        return factory.create(id, depth);
    }

    @Override
    public Monster createBoss(int depth) {
        return factory.create("griever", depth);
    }

    @Override
    public Item createLoot(int depth) {
        switch(RandomSource.getInstance().nextInt(4)) {
            case 0: return new Weapon("Wooden Javalin", 3.0, 35 + depth*10, 4 + depth);
            case 1: return new Armor("Runners Survival Pack ", 2.0, 55 + depth*8, 3 + depth);
            case 2: return new Potion("The Grief Serum", 0.4, 20, 25 + depth * 3);
            default: return new Treasure("Heart of Immunity", 1.5, 95 + depth*20);
        }
    }

    @Override
    public String createRoomFlavor() {
        return RandomSource.getInstance().pick(FLAVORS);
    }
}

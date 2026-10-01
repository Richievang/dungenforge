package dungeonforge.factory;

import dungeonforge.config.RandomSource;
import dungeonforge.core.Monster;
import dungeonforge.items.*;

public class FrostThemeKit implements ThemeKit{
    private final MonsterFactory factory;

    private static final String[] FLAVORS = {
            "Every inhale is as sharp as a nail.",
            "The walls glisten with sleet.",
            "The air blows in low howls.",
            "You hear a deep bellowing roar in the distance"
    };

    public FrostThemeKit(MonsterFactory factory) {
        this.factory = factory;
    }

    @Override
    public String themeName() {
        return "Frost";
    }

    @Override
    public Monster createMonster(int depth) {
        String id = RandomSource.getInstance().pick(factory.idsForTheme("frost"));
        return factory.create(id, depth);
    }

    @Override
    public Monster createBoss(int depth) {
        return factory.create("rime_tyrant", depth);
    }

    @Override
    public Item createLoot(int depth) {
        switch(RandomSource.getInstance().nextInt(4)) {
            case 0: return new Weapon("Frosted Hidden Blade", 1.5, 60 + depth*11, 9 + depth);
            case 1: return new Armor("Cloak of Stealth ", 2.0, 40 + depth*8, 4 + depth);
            case 2: return new Potion("Chilled Essences of Life ", 1.0, 50,  21 + depth * 3);
            default: return new Treasure("Eye of the Unseen", 1.0, 90 + depth*20);
        }
    }

    @Override
    public String createRoomFlavor() {
        return RandomSource.getInstance().pick(FLAVORS);
    }
}

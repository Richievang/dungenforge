package dungeonforge.factory;

import dungeonforge.config.RandomSource;
import dungeonforge.core.Monster;
import dungeonforge.items.*;

public class ForgeThemeKit implements ThemeKit{
    private final MonsterFactory factory;

    private static final String[] FLAVORS = {
            "Heat rolls off the walls in slow waves.",
            "Cinders drift upwards from grate in the floor.",
            "Lava roars as if it was alive.",
            "Stones are engulfed in black mold."
    };

    public ForgeThemeKit(MonsterFactory factory) {
        this.factory = factory;
    }

    @Override
    public String themeName() {
        return "Forge";
    }

    @Override
    public Monster createMonster(int depth) {
        String id = RandomSource.getInstance().pick(factory.idsForTheme("forge"));
        return factory.create(id, depth);
    }

    @Override
    public Monster createBoss(int depth) {
        return factory.create("forge_tyrant", depth);
    }

    @Override
    public Item createLoot(int depth) {
        switch(RandomSource.getInstance().nextInt(4)) {
            case 0: return new Weapon("Blazing Scythe", 5.0, 55 + depth*12, 7 + depth);
            case 1: return new Armor("Molten Armor ", 6.0, 60 + depth*10, 6 + depth);
            case 2: return new Potion("Elixir of Painless ", 0.4, 45, 22 + depth * 3);
            default: return new Treasure("Necklace of The Damned", 2.0, 80 + depth*18);
        }
    }

    @Override
    public String createRoomFlavor() {
        return RandomSource.getInstance().pick(FLAVORS);
    }
}

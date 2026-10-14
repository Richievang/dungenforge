package dungeonforge.events;

import java.util.LinkedHashSet;
import java.util.Set;

public class AchievementSystem implements GameEventListener {

    private final Set<String> unlocked = new LinkedHashSet<>();
    private final EventBus bus;

    private int kills;
    private int fled;
    private int deepestLevel;

    public AchievementSystem(EventBus bus) {
        this.bus = bus;
    }

    @Override
    public void onEvent(GameEvent event) {
        switch (event.getType()) {
            case MONSTER_DIED -> {
                kills++;
                if(kills == 1) { unlock("First Blood", "Defeat your first monster"); }
                if(kills == 10) { unlock("Exterminator", "Defeat 10 monsters"); }
            }
            case MONSTER_FLED -> {
                fled++;
                if(kills == 1) { unlock("I Am The Monster", "Scare least 5 monsters"); }
            }
            case STRATEGY_CHANGE -> {
                unlock("Cornered", "Force and Enemie to change tactics");

            }
            case LEVEL_ENTERED -> {
                deepestLevel = Math.max(deepestLevel, event.getInt("depth"));
                if(deepestLevel >= 3) unlock("Deeper Delver", "Reach dungeon level 3");
            }
            case DELVE_SURVIVED -> {
                unlock("Survivor", "Walk away from the dungeon with low health");
            }
            default -> { }
        }
    }

    private void unlock(String name, String description) {
        if(unlocked.add(name) ) {
            bus.message("ACHIEVEMENT: " + name + " -- " + description);
        }
    }

    public Set<String> getUnlocked() { return unlocked; }
}

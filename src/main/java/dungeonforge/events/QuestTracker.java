package dungeonforge.events;

import java.util.ArrayList;
import java.util.List;

public class QuestTracker implements GameEventListener{

    private final List<Quest> quests = new ArrayList<>();
    private final EventBus bus;

    public QuestTracker(EventBus bus) {
        this.bus = bus;
        quests.add(new Quest("q1", "Slaughter 5 Creatures withing the dungeon", EventType.MONSTER_DIED, 5));
        quests.add(new Quest("q2", "Stealth kill 7 Creatures of the dungeon", EventType.MONSTER_DIED, 5));
        quests.add(new Quest("q3", "Scare off 4 Enemies", EventType.MONSTER_FLED, 4));
        quests.add(new Quest("q4", "Clear 6 rooms", EventType.ROOM_CLEARED, 6));

    }

    @Override
   public void onEvent(GameEvent event) {
        for (Quest q : quests) {
            if(q.getTrigger() != event.getType()) continue;
            if(q.advance()) {
                bus.message("QUEST COMPLETE: " + q.getDescription());
            }
        }
    }

    public List<Quest> getQuests() { return quests; }

    public int completedCount() {
        int n = 0;
        for (Quest q : quests) if(q.isComplete()) n++;
        return n;
    }
}

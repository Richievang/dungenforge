package dungeonforge.events;

import java.util.ArrayDeque;
import java.util.Deque;

public class CombatLog implements GameEventListener{

    private final Deque<String> lines = new ArrayDeque<>();
    private final int maxLines;

    public CombatLog(int maxLines) { this.maxLines = maxLines; }

    @Override
    public void onEvent(GameEvent event) {
        String line = format(event);
        if(line == null) return;
        lines.addLast(line);
        while( lines.size() > maxLines ) lines.removeFirst();
    }

    private String format(GameEvent e) {
        return switch (e.getType())  {
            case MESSAGE -> e.getString("text");
            case DAMAGE_DEALT -> "You hit" + e.getString("target") + "for" + e.getInt("amount");
            case DAMAGE_TAKEN -> e.getString("source") + "Hits you for" + e.getInt("amount");
            case MONSTER_DIED -> e.getString("name") + "dies." + e.getInt("xp");
            case MONSTER_FLED -> e.getString("name") + "flees into the darkness";
            case MONSTER_HEALED -> e.getString("healer") + "mends" + e.getString("target");
            case STRATEGY_CHANGE -> e.getString("name")
                    + "Changes tactics" + e.getString("from" + "->" +
                    e.getString("to"));
            case ROOM_CLEARED -> e.getString("room") + "is eerily quiet";
            case LEVEL_ENTERED -> " == level " + e.getString("depth") + " : " + e.getInt("theme");
            case PLAYER_DIED -> "You fall into the eternal abyss";
            case DELVE_SURVIVED -> "You reach the end of the dungeon and watch as the sun hits your face";
            default -> null;
        };
    }

    public Deque<String> getLines() { return lines; }
    public int size() { return lines.size(); }
}

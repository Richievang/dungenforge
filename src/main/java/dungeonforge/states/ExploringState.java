package dungeonforge.states;

import dungeonforge.commands.Command;

import java.util.List;

public class ExploringState implements GameState {
    private final List<String> VERBS = List.of(
            "north", "south", "down", "look", "loot", "drop", "use",
            "inventory", "pack", "status", "quest", "history", "undo", "help", "quit" );

    @Override
    public List<String> availableCommand() {
        return VERBS;
    }

    @Override
    public String name() { return "Exploring"; }
}



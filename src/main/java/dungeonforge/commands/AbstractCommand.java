package dungeonforge.commands;

import dungeonforge.core.Player;
import dungeonforge.core.Room;
import dungeonforge.events.EventBus;

public abstract class AbstractCommand implements Command {

    protected GameContext ctx;
    protected final String args;
    private boolean actuallyRan;

    private TurnSnapShot before;

    public AbstractCommand(GameContext ctx, String args) {
        this.ctx = ctx;
        this.args = args == null ? "" : args.trim();
    }
    public Player player() { return ctx.getPlayer(); }
    public EventBus bus() { return ctx.getBus(); }
    public Room room() { return ctx.getCurrentRoom(); }

    @Override
    public void execute() {
        if (endsTurn() && ctx.getCurrentRoom() != null) {
            before = TurnSnapShot.capture(ctx.getPlayer(), ctx.getCurrentRoom());

        } else {
            before = null;
        }
        actuallyRan = doExecute();
        if (!actuallyRan) {
            before = null;
            return;
        }
        if (endsTurn()) {
            ctx.runMonsterTurn();
        }
    }

    protected abstract boolean doExecute();

    @Override
    public void undo() {
        if (before != null) {
            before.restore();
            ctx.setCurrentRoom(before.getRoom());
        }
    }

    @Override
    public String getDescription() {
        return "";
    }

    public boolean isUndoable () { return actuallyRan;}
    public boolean didRun() { return actuallyRan;}

}
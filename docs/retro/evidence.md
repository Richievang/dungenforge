# Week 7 Evidence

## 1. BEFORE

```bash
mvn -q exec:java
```

Play a few turns, then answer:

**Paste five consecutive `else if` branches from `gameLoop()`:**

```java
if (verb.isEmpty()) {
} else if (verb.equals("north") || verb.equals("south") || verb.equals("down")) {
} else if (verb.equals("attack")) {
} else if (verb.equals("look")) {
} else if (verb.equals("take")) {
}
```

**You want to add `equip`. List every edit you would make:**

1. Main.java import dungeonforge.items.weapons;
2. Main.java a 12th else if *verb.equals("equip"))inside gameLoop()
3. Main.java set tookATurn = true in the new branch inside #2
4. Main.java the two hardcoded help printlns, lines 219-220
5. Player.java import dungeonforge.items.Weapeon;
6. Player.java private Weapon euippedWeapon; + equip(Weapon) + getEquippedWeapon()
7. Player.java make the weapon count toward damage
8. Player.java describe() line 59 reads the field attackPower, not the getter
9. Combat.java int damage = player.getAttackPower();
10. src/test/java/dungeonforge/EquipTest.java
11. README.WEEK7.md and any verb list in docs

**You want `e` to mean `equip`. List every *additional* edit:**

            Starter Referance
1. Functional edit || verb.equals("e")
2. Documentation 2 lines edited by hand, or it silently drifts
3. Can it come from config? No
4. Can you list every alias? No
5. Collision safety silent
6. You would have to modify the conditional block to test for "e" OR "equip" in a 
7. case-sensitive way.

There is no good place in gameLoop() 
because undo requires storing and restoring previous game states, 
which should be handled by a Command or Memento design rather than 
imply adding a line to the game loop.

**You want undo. Where in `gameLoop()` would the code go?** *(There is no good answer. Say
why in one sentence.)*


## 2. AFTER — US-5.1

**Paste your new `gameLoop()` in full. It should fit in a dozen lines:**

```java
    private static void gameLoop(CommandParser parser, GameContext ctx) {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));

        while (ctx.isRunning() && ctx.getPlayer().isAlive()) {
            System.out.print("\n[HP " + ctx.getPlayer().getHp() + "/" + ctx.getPlayer().getMaxHp() + "] > ");
            System.out.flush();

            String line;
            try { line = in.readLine(); } catch (Exception e) { break; }
            if (line == null) break;
            System.out.println();

            Command command = parser.parse(line, ctx);
            command.execute();
        }
    }
```

**Paste the registry lines that add `drop` and its alias:**

```java
        register("drop", DropCommand::new);

```

## 3. AFTER — US-5.2

**The naive-undo failure table from Part D2 step 2:**

| | HP        | XP       | Gold       | Monster HP |
|---|-----------|----------|------------|------------|
| Before attack | [HP 80/80 | XP 0/20  | Gold 0/40  | HP 18/18   |
| After attack | [HP 78/80 | XP 12/20 | Gold 30/30 | HP 8/18    |
| After naive undo | HP 80/80  | XP 12/20 | Gold 30/30 | HP 18/18   |

**The same table after `TurnSnapshot`:**

| | HP       | XP       | Gold       | Monster HP |
|---|----------|----------|------------|------------|
| Before attack | HP 80/80 | XP 0/20  | Gold 30/30 | HP 18/18   |
| After attack | HP 80/80 | XP 12/20 | Gold 30/30 | HP 8/18    |
| After snapshot undo | HP 80/80 | XP 0/20  | Gold 30/30 | HP 18/18   |

## 4. AFTER — US-5.3 and US-5.4

You have to replay backwards because if you undid the children forward then you'd end up only going
back in history 1 command.

**Paste a `loot` that picks up three things, then an `undo` that puts them all back:**

```
look
[HP 76/80] > 
  [L1R1Level1: Crypt
    Iron-bound Chest: 
    - Bone Dagger [dmg 6] (1.0kg, 50g)
    - Bone Dagger [dmg 6] (1.0kg, 50g)
    - Crypt Guard Armor [def 3] (4.0kg, 43g)
   Exits: south,north

loot
[HP 76/80] > 
   You take Bone Dagger.
   You take Bone Dagger.
   You take Crypt Guard Armor.

```

**Paste the hourglass rewinding a turn, with `inventory` before and after:**

```
inventory
[HP 76/80] > 
  You are carrying:
      - Small Healing Draught [heals 22] (0.3kg, 20g)
      - Chronomaster's Hourglass[2charges] (rewinds one turn)
      - Bone Dagger [dmg 6] (1.0kg, 50g)
      - Bone Dagger [dmg 6] (1.0kg, 50g)
      - Crypt Guard Armor [def 3] (4.0kg, 43g)

use Chronomaster's Hourglass
[HP 76/80] > 
  Sand runs backwards. The last moment unhappens.
  ... but there was nothing to take back.

inventory
[HP 76/80] > 
  You are not carry anything
```

**How long did US-5.4 actually take you?** __21__ minutes

## 5. The replay

**Paste your `history` output and the first ten lines of replaying it with `--script=`:**

```

```

## 6. Tests and CI

**`mvn test` summary:**

```

```

**Green CI URL:**

## 7. Sprint review — one sentence

> What can the project do now that it could not do last week?


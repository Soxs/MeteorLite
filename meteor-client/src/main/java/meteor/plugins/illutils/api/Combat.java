package meteor.plugins.illutils.api;

import meteor.plugins.illutils.osrs.wrappers.IllInventoryItem;
import net.runelite.api.Skill;
import meteor.plugins.illutils.osrs.OSRSUtils;
import meteor.plugins.illutils.osrs.wrappers.IllNPC;
import meteor.plugins.illutils.ui.Prayer;

import javax.inject.Inject;

public class Combat {
    private final OSRSUtils game;
    private final Prayers prayers;

    @Inject
    public Combat(OSRSUtils game) {
        this.game = game;
        this.prayers = new Prayers(game);
    }

    public void kill(IllNPC npc, Prayer... prayers) {
        for (Prayer prayer : prayers) {
            this.prayers.setEnabled(prayer, true);
        }

        npc.interact("Attack");

        try {
            while (game.npcs().withIndex(npc.index()).withAction("Attack").exists()) {
                heal();
                restorePrayer();
                restoreStats();
                attack(npc);
                game.tick();
            }
        } finally {
            for (Prayer prayer : prayers) {
                this.prayers.setEnabled(prayer, false);
            }
        }
    }

    public void attack(IllNPC npc) {
        IllNPC target = (IllNPC) game.localPlayer().target();

        if (target == null || target.index() != npc.index()) {
            if (game.localPlayer().target() != null)
                npc.interact("Attack");
        }
    }

    private boolean needsStatRestore() {
        Skill[] matters = new Skill[]{Skill.ATTACK, Skill.DEFENCE, Skill.STRENGTH};
        for (Skill skill : matters) {
            if (game.modifiedLevel(skill) < game.baseLevel(skill)) {
                return true;
            }
        }
        return false;
    }

    public void restoreStats() {
        if (game.inventory().withNamePart("restore").exists() && needsStatRestore()) {
            game.inventory().withNamePart("restore").first().interact("Drink");
        }
    }

    public void restorePrayer() {
        if (game.modifiedLevel(Skill.PRAYER) < game.baseLevel(Skill.PRAYER) / 2) {
            //todo add super restores?
            if (game.inventory().withNamePart("Prayer potion(").exists()) {
                game.inventory().withNamePart("Prayer potion(").first().interact("Drink");
            }
        }
    }

    public void heal() {
        if (game.modifiedLevel(Skill.HITPOINTS) < game.baseLevel(Skill.HITPOINTS) / 2) {
            IllInventoryItem food = game.inventory().withAction("Eat").first();
            if (food != null) {
                food.interact("Eat");
                game.tick();
            }
        }
    }

    public void setAutoRetaliate(boolean autoRetaliate) {
        if (autoRetaliate() != autoRetaliate) {
            game.widget(593, 30).interact("Auto retaliate");
            game.waitUntil(() -> autoRetaliate() == autoRetaliate);
        }
    }

    public boolean autoRetaliate() {
        return game.varp(172) == 0;
    }
}
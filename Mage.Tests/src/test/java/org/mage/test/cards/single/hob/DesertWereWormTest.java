package org.mage.test.cards.single.hob;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author anonymous
 */
public class DesertWereWormTest extends CardTestPlayerBase {

    /*
    Desert Were-Worm
    {4}{R}{R}
    Creature - Dragon Wurm
    This creature gets +2/+0 for each Mountain you control.
    Whenever you attack with creatures with total power 12 or greater for the first time each turn, untap all attacking creatures. After this phase, there is an additional combat phase.
    */
    private static final String desertWereWorm = "Desert Were-Worm";
    private static final String colossalDreadmaw = "Colossal Dreadmaw";
    private static final String mountain = "Mountain";


    @Test
    public void testDesertWereWormAdditionalCombat() {
        setStrictChooseMode(false);
        addCard(Zone.BATTLEFIELD, playerA, mountain, 6);
        addCard(Zone.BATTLEFIELD, playerA, desertWereWorm);
        addCard(Zone.BATTLEFIELD, playerB, colossalDreadmaw, 2);
        setLife(playerB, 49);

        attack(1, playerA, desertWereWorm);
        attack(1, playerA, desertWereWorm);

        attack(2, playerB, colossalDreadmaw);

        attack(3, playerA, desertWereWorm);
        attack(3, playerA, desertWereWorm);

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertTapped(colossalDreadmaw, true);
        assertTapped(desertWereWorm, true);
        assertLife(playerB, 1);
    }

    @Test
    public void testDesertWereWormDoesNotTrigger() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, desertWereWorm);
        addCard(Zone.BATTLEFIELD, playerA, colossalDreadmaw, 2);

        attack(1, playerA, colossalDreadmaw);

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertTapped(colossalDreadmaw, true);
    }

    @Test
    public void testDesertWereWormPower() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, desertWereWorm);
        addCard(Zone.BATTLEFIELD, playerA, mountain, 5);
        addCard(Zone.BATTLEFIELD, playerB, mountain, 5);

        execute();

        assertPowerToughness(playerA, desertWereWorm, 10,5);
    }
}
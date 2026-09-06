package mage.cards.d;

import java.util.*;

import mage.MageInt;
import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.TriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.AdditionalCombatPhaseEffect;
import mage.abilities.effects.common.UntapAllEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

/**
 * @author Meowcelia
 */
public final class DesertWereWorm extends CardImpl {
    //mountains you control
    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Mountain you control");
    //+2/+0 for each mountain you control
    private static final DynamicValue power =
            new PermanentsOnBattlefieldCount(filter, 2);

    static {
        filter.add(SubType.MOUNTAIN.getPredicate());
    }

    public DesertWereWorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");

        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.WURM);
        this.toughness = new MageInt(5);

        // This creature gets +2/+0 for each Mountain you control.
        this.addAbility(new SimpleStaticAbility(new BoostSourceEffect(power, StaticValue.get(0), Duration.WhileOnBattlefield)));
        // Whenever you attack with creatures with total power 12 or greater for the first time each turn, untap all attacking creatures. After this phase, there is an additional combat phase.
        this.addAbility(new DesertWereWormTriggeredAbility());
    }

    private DesertWereWorm(final DesertWereWorm card) {
        super(card);
    }

    @Override
    public DesertWereWorm copy() {
        return new DesertWereWorm(this);
    }
}

class DesertWereWormTriggeredAbility extends TriggeredAbilityImpl {

    protected DesertWereWormTriggeredAbility() {
        super(Zone.BATTLEFIELD, new UntapAllEffect(StaticFilters.FILTER_ATTACKING_CREATURES));
//        super(Zone.BATTLEFIELD, new AdditionalCombatPhaseEffect().setText("After this combat phase, there is an additional combat phase"));
        this.addEffect(new AdditionalCombatPhaseEffect().setText("After this combat phase, there is an additional combat phase"));

        this.setTriggerPhrase("Whenever you attack with creatures with total power 12 or greater for the first time each turn, ");
        this.addWatcher(new DesertWereWormWatcher());
    }

    private DesertWereWormTriggeredAbility(TriggeredAbilityImpl ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return (event.getType() == GameEvent.EventType.DECLARED_ATTACKERS) && isControlledBy(event.getPlayerId());
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return DesertWereWormWatcher.checkEvent(event, game);
    }

    @Override
    public TriggeredAbility copy() {
        return new DesertWereWormTriggeredAbility(this);
    }
}

class DesertWereWormWatcher extends Watcher {

    private final Map<MageObjectReference, UUID> map = new HashMap<>();

    public DesertWereWormWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DECLARED_ATTACKERS && checkPower(game)) {
            game
                    .getCombat()
                    .getAttackers()
                    .forEach(uuid -> map.putIfAbsent(new MageObjectReference(uuid, game), event.getId()));
        }
    }

    static boolean checkEvent(GameEvent event, Game game) {
        return game.getCombat()
                       .getAttackers()
                       .stream()
                       .filter(uuid -> checkAttackedFirstTimeThisTurn(uuid, event, game))
                       .map(game::getPermanent)
                       .filter(Objects::nonNull)
                       .map(MageObject::getPower)
                       .mapToInt(MageInt::getValue)
                       .sum() >= 12;
    }

    @Override
    public void reset() {
        super.reset();
        this.map.clear();
    }

    private static boolean checkAttackedFirstTimeThisTurn(UUID uuid, GameEvent event, Game game) {
        final Map<MageObjectReference, UUID> map = game.getState().getWatcher(DesertWereWormWatcher.class).map;
        return Objects.equals(map.get(new MageObjectReference(uuid, game)), event.getId());

    }

    private static boolean checkPower(Game game) {
        return game
                       .getCombat()
                       .getAttackers()
                       .stream()
                       .map(game::getPermanent)
                       .filter(Objects::nonNull)
                       .map(MageObject::getPower)
                       .mapToInt(MageInt::getValue)
                       .sum() >= 12;
    }
}

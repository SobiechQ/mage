package mage.abilities.hint.common;

import mage.abilities.Ability;
import mage.abilities.condition.common.CountersOnPermanentsCondition;
import mage.abilities.hint.Hint;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

/**
 * A hint which keeps track of how many counters of a specific type there are
 * among some type of permanents
 *
 * @author alexander-novo
 */
public class CountersOnPermanentsHint implements Hint {

    // Which permanents to consider counters on
    public final FilterPermanent filter;
    // Which counter type to count
    public final CounterType counterType;

    public CountersOnPermanentsHint(CountersOnPermanentsCondition condition) {
        this(condition.filter, condition.counterType);
    }

    /**
     * @param filter      Which permanents to consider counters on
     * @param counterType Which counter type to count
     */
    public CountersOnPermanentsHint(FilterPermanent filter, CounterType counterType) {
        this.filter = filter;
        this.counterType = counterType;
    }

    public CountersOnPermanentsHint(final CountersOnPermanentsHint hint) {
        this.filter = hint.filter.copy();
        this.counterType = hint.counterType;
    }

    @Override
    public String getText(Game game, Ability ability) {
        int totalCounters = 0;
        for (Permanent permanent : game.getBattlefield().getActivePermanents(this.filter,
                ability.getControllerId(), ability, game)) {
            for (Counter counter : permanent.getCounters(game).values()) {
                if (counter.getName().equals(this.counterType.getName())) {
                    totalCounters += counter.getCount();
                }

            }
        }
        return CardUtil.getTextWithFirstCharUpperCase(this.counterType.getName()) + " counters among " + this.filter.getMessage() + ": " + totalCounters;
    }

    @Override
    public CountersOnPermanentsHint copy() {
        return new CountersOnPermanentsHint(this);
    }
}

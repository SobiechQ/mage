package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.condition.common.CitysBlessingCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.hint.common.CitysBlessingHint;
import mage.abilities.keyword.AscendAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class OrazcaRelic extends CardImpl {

    public OrazcaRelic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Ascend
        this.addAbility(new AscendAbility());

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {T}, Sacrifice Orazca Relic: You gain 3 life and draw a card. Activate this ability only if you have the city's blessing.
        Ability ability = new ActivateIfConditionActivatedAbility(
                new GainLifeEffect(3), new TapSourceCost(), CitysBlessingCondition.instance
        );
        ability.addCost(new SacrificeSourceCost());
        ability.addEffect(new DrawCardSourceControllerEffect(1).concatBy("and"));
        this.addAbility(ability.addHint(CitysBlessingHint.instance));
    }

    private OrazcaRelic(final OrazcaRelic card) {
        super(card);
    }

    @Override
    public OrazcaRelic copy() {
        return new OrazcaRelic(this);
    }
}

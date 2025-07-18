package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.condition.common.ThresholdCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.DrawDiscardTargetEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class CephalidColiseum extends CardImpl {

    public CephalidColiseum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {tap}: Add {U}. Cephalid Coliseum deals 1 damage to you.
        Ability manaAbility = new BlueManaAbility();
        manaAbility.addEffect(new DamageControllerEffect(1));
        this.addAbility(manaAbility);

        // Threshold - {U}, {tap}, Sacrifice Cephalid Coliseum: Target player draws three cards, then discards three cards. Activate this ability only if seven or more cards are in your graveyard.
        Ability thresholdAbility = new ActivateIfConditionActivatedAbility(
                new DrawDiscardTargetEffect(3, 3),
                new ManaCostsImpl<>("{U}"), ThresholdCondition.instance
        );
        thresholdAbility.addCost(new TapSourceCost());
        thresholdAbility.addCost(new SacrificeSourceCost());
        thresholdAbility.addTarget(new TargetPlayer());
        thresholdAbility.setAbilityWord(AbilityWord.THRESHOLD);
        this.addAbility(thresholdAbility);
    }

    private CephalidColiseum(final CephalidColiseum card) {
        super(card);
    }

    @Override
    public CephalidColiseum copy() {
        return new CephalidColiseum(this);
    }
}

package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author sobiech
 */
public final class NissaWorldsoulSpeaker extends CardImpl {

    public NissaWorldsoulSpeaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Landfall -- Whenever a land you control enters, you get {E}{E}.
        // You may pay eight {E} rather than pay the mana cost for permanent spells you cast.
    }

    private NissaWorldsoulSpeaker(final NissaWorldsoulSpeaker card) {
        super(card);
    }

    @Override
    public NissaWorldsoulSpeaker copy() {
        return new NissaWorldsoulSpeaker(this);
    }
}

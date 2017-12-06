package GameLogic.cards;

class laboratory {

    public int cost = 3;

    public Enum CardType = cardType.ActionCard;
    public Enum CardName = GameLogic.cards.CardName.laboratory;

    public laboratory(int cost, Enum cardType, Enum cardName) {
        this.cost = cost;
        this.CardType = cardType;
        this.CardName = cardName;
    }


}
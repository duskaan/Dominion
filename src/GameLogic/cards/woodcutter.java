package GameLogic.cards;

class woodcutter {

    public int cost = 3;
    public Enum CardType = cardType.ActionCard;
    public Enum CardName = GameLogic.cards.CardName.woodcutter;

    public woodcutter(int cost, Enum cardType, Enum cardName) {
        this.cost = cost;
        this.CardType = cardType;
        this.CardName = cardName;
    }


}
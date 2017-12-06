package GameLogic.cards;

class witch {

    public int cost = 5;
    public Enum CardType = cardType.ActionCard;
    public Enum CardName = GameLogic.cards.CardName.witch;

    public witch(int cost, Enum cardType, Enum cardName) {
        this.cost = cost;
        this.CardType = cardType;
        this.CardName = cardName;
    }


}
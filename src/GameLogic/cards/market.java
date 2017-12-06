package GameLogic.cards;

class market  {

    public int cost = 3;
    public Enum CardType = cardType.ActionCard;
    public Enum CardName = GameLogic.cards.CardName.market;

    public market(int cost, Enum cardType, Enum cardName) {
        this.cost = cost;
        this.CardType = cardType;
        this.CardName = cardName;
    }


}
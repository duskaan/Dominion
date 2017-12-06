package GameLogic.cards;

class festival  {

    public int cost = 5;
    public Enum CardType = cardType.ActionCard;
    public Enum CardName = GameLogic.cards.CardName.festival;

    public festival(int cost, Enum cardType, Enum cardName) {
        this.cost = cost;
        this.CardType = cardType;
        this.CardName = cardName;
    }


}
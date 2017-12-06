package GameLogic.cards;

class workshop  {

    public int cost = 3;
    public Enum CardType = cardType.ActionCard;
    public Enum CardName = GameLogic.cards.CardName.workshop;

    public workshop(int cost, Enum cardType, Enum cardName) {
        this.cost = cost;
        this.CardType = cardType;
        this.CardName = cardName;
    }


}
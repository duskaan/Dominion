package GameLogic.cards;

class chancellor  {

    public int cost = 5;
    public Enum CardType = cardType.ActionCard;
    public Enum CardName = GameLogic.cards.CardName.chancellor;

    public chancellor(int cost, Enum cardType, Enum cardName) {
        this.cost = cost;
        this.CardType = cardType;
        this.CardName = cardName;
    }


}
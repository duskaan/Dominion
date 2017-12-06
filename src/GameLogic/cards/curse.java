package GameLogic.cards;

class curse {

    public int cost = 0;
    public int realValue = -1;
    public Enum CardType = cardType.VictoryCard;
    public Enum CardName = GameLogic.cards.CardName.curse;

    public curse(int cost, int realValue, Enum cardType, Enum cardName) {
        this.cost = cost;
        this.realValue = realValue;
        this.CardType = cardType;
        this.CardName = cardName;
    }


}
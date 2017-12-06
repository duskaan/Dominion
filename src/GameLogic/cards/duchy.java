package GameLogic.cards;

class duchy {

    public int cost = 5;
    public int realValue = 3;
    public Enum CardType = cardType.VictoryCard;
    public Enum CardName = GameLogic.cards.CardName.duchy;

    public duchy(int cost, int realValue, Enum cardType, Enum cardName) {
        this.cost = cost;
        this.realValue = realValue;
        this.CardType = cardType;
        this.CardName = cardName;
    }


}
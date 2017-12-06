package GameLogic.cards;

class copper {

    public int cost = 0;
    public int coinValue = 1;
    public Enum CardType = cardType.CoinCard;
    public Enum CardName = GameLogic.cards.CardName.copper;

    public copper(int cost, int coinValue, Enum cardType, Enum cardName) {
        this.cost = cost;
        this.coinValue = coinValue;
        this.CardType = cardType;
        this.CardName = cardName;
    }


}
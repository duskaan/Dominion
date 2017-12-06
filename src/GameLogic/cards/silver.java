package GameLogic.cards;

class silver {

    public int cost = 3;
    public int coinValue = 2;
    public Enum CardType = cardType.CoinCard;
    public Enum CardName = GameLogic.cards.CardName.silver;

    public silver(int cost, int coinValue, Enum cardType, Enum cardName) {
        this.cost = cost;
        this.coinValue = coinValue;
        this.CardType = cardType;
        this.CardName = cardName;
    }


}
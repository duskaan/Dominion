package GameLogic.cards;

class gold {

    public int cost = 6;
    public int coinValue = 3;
    public Enum CardType = cardType.CoinCard;
    public Enum CardName = GameLogic.cards.CardName.gold;

    public gold(int cost, int coinValue, Enum cardType, Enum cardName) {
        this.cost = cost;
        this.coinValue = coinValue;
        this.CardType = cardType;
        this.CardName = cardName;
    }


}
package GameLogic.cards;


class smithy  {

    public int cost = 4;
    public Enum CardType = cardType.ActionCard;
    public Enum CardName = GameLogic.cards.CardName.smithy;

    public smithy(int cost, Enum cardType, Enum cardName) {
        this.cost = cost;
        this.CardType = cardType;
        this.CardName = cardName;
    }


}
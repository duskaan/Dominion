package GameLogic.cards;

class village  {

    public int cost = 3;
    public Enum CardType = cardType.ActionCard;
    public Enum CardName = GameLogic.cards.CardName.village;

    public village(int cost, Enum cardType, Enum cardName) {
        this.cost = cost;
       this.CardType = cardType;
       this.CardName = cardName;
    }


}

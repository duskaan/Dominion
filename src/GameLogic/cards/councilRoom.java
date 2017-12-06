package GameLogic.cards;

class councilRoom  {

    public int cost = 5;
    public Enum CardType = cardType.ActionCard;
    public Enum CardName = GameLogic.cards.CardName.councilRoom;

    public councilRoom(int cost, Enum cardType, Enum cardName) {
        this.cost = cost;
        this.CardType = cardType;
        this.CardName = cardName;
    }


}
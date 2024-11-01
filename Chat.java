public class Chat {

   private User userOne;
   private User userTwo;

    public Chat( User userOne, User userTwo) {

        this.userOne = userOne;
        this.userTwo = userTwo;
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Chat chat = (Chat) obj;
        return (userOne.equals(chat.userOne) && userTwo.equals(chat.userTwo)) ||
                (userOne.equals(chat.userTwo) && userTwo.equals(chat.userOne));
    }
}


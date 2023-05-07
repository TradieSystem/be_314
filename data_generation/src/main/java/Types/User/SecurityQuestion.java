package Types.User;

public enum SecurityQuestion {
    
    CAR("What was your first car?", 1),
    STREET("What was the name of the first street you lived on?", 2),
    PET("What was the name of your first pet?", 3),
    CITY("What city were you born in?", 4),
    NICKNAME("What was your childhood nickname?", 5);
    
    private SecurityQuestion(String text, int id) {
        question = text;
        this.id = id;
    }
    
    public final String question;
    public final int id;
}

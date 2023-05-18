package Types.Service;

public enum Suburb {

    BARTON("Barton", 2600),
    CANBERRA("Canberra", 2600),
    CAPITALHILL("Capital Hill", 2600),
    DEAKIN("Deakin", 2600),
    DUNTROON("Duntroon", 2600),
    FAIRBAIRNRAAF("Fairbairn Raaf", 2600),
    HARMAN("Harman", 2600),
    HMASHARMAN("Hmas Harman", 2600),
    PARKES("Parkes", 2600),
    PARLIAMENTHOUSE("Parliament House", 2600),
    RUSSELL("Russell", 2600),
    RUSSELLHILL("Russell Hill", 2600),
    YARRALUMLA("Yarralumla", 2600),
    ACTON("Acton", 2601),
    BLACKMOUNTAIN("Black Mountain", 2601),
    CITY("City", 2601),
    AINSLIE("Ainslie", 2602),
    DICKSON("Dickson", 2602),
    DOWNER("Downer", 2602),
    HACKETT("Hackett", 2602),
    LYNEHAM("Lyneham", 2602),
    OCONNOR("O'connor", 2602),
    WATSON("Watson", 2602);
    
    private Suburb(String name, int code) {
        this.postcode =  Integer.toString(code);
        suburbName = name;
    }
    
    public final String postcode;
    public final String suburbName;
}

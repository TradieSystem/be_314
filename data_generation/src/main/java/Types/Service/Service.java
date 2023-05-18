package Types.Service;

public enum Service {
    
    TREE("Tree Removal", 1),
    ROOF("Roof Cleaning",2),
    FENCE("Fence Installation",3),
    PLUMBING("Plumbing",4),
    OVEN("Oven Repairs",5);
    
    private Service(String text, int id) {
        this.id = id;
        this.service= text;
    }
    
    public final int id;
    public final String service;
}



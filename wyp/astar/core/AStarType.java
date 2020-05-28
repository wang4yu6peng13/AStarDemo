package astar.core;

public enum AStarType {
    ASTAR_4("4方向"),
    ASTAR_8_NO_CORNER("8方向不穿墙角"),
    ASTAR_8_WITH_CORNER("8方向可穿墙角"),
    ;

    private final String desc;

    AStarType(final String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}

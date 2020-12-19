package net.dreamingworld.core.vanity;

public enum VanitySlot {

    RING0(0),
    RING1(1),
    CLOAK(2),
    BELT(3),
    NECKLACE(4),
    REDHAT_ENTERPRISE_LINUX(5);

    private int id;

    VanitySlot(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}

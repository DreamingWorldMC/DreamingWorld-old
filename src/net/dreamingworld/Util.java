package net.dreamingworld;

public class Util {

    public static String formatString(String string) {
        return string.replace("$(PC)", DreamingWorld.primaryColor.toString()).replace("$(SC)", DreamingWorld.secondaryColor.toString()).replace('&', '\u00a7');
    }
}

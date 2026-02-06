package shared;

public record Color(
    short red,
    short green,
    short blue,
    short alpha
) {
    public int getFullColor() {
        return ((alpha & 0xFF) << 24) |
               ((red   & 0xFF) << 16) |
               ((green & 0xFF) << 8 ) |
               ((blue  & 0xFF));
    }

    public static Color transparent() {
        return new Color((short) 0, (short) 0, (short) 0, (short) 0);
    }
}
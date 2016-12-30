package ethan.chess;

/**
 * Created by Ethan on 12/29/2016.
 */
public enum Side {
    WHITE(true), BLACK(false);

    private boolean isWhite;

    Side(boolean isWhite) {
        this.isWhite = isWhite;
    }

    public boolean isWhite() {
        return isWhite;
    }
}

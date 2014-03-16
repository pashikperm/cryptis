package usr.pashik.securd.platform.access.mode;

/**
 * Created by pashik on 15.03.14 15:25.
 */
public enum AccessMode {
    ALLOW {
        @Override
        public AccessMode invert() {
            return DENY;
        }
    }, DENY {
        @Override
        public AccessMode invert() {
            return ALLOW;
        }
    };

    public abstract AccessMode invert();
}

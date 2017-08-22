package panels;

/**
 * Interface to mark tabs, so there could
 * be called event to update states
 */
public interface ChangeTabListener {

    /**
     * Triggered when tab is selected
     */
    void onTabChange();
}

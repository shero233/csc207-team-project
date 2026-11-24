package use_case.expire_food_event;

import entity.FoodEvent;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Interactor responsible for removing expired food events from active listings.
  */
public class ExpireFoodEventInteractor implements ExpireFoodEventInputBoundary {

    private final ExpireFoodEventDataAccessInterface dataAccess;
    private final ExpireFoodEventOutputBoundary presenter;

    public ExpireFoodEventInteractor(
            ExpireFoodEventDataAccessInterface dataAccess,
            ExpireFoodEventOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(ExpireFoodEventInputData inputData) {
        LocalDateTime now = LocalDateTime.now();
        List<FoodEvent> activeEvents = dataAccess.getAllActiveEvents();

        for (FoodEvent event : activeEvents) {
            if (event.getExpiresAt() != null
                    && !event.getExpiresAt().isAfter(now)) {
                dataAccess.removeFromActiveListings(event.getEventId());
            }
        }

        presenter.prepareSuccessView(new ExpireFoodEventOutputData());
    }
}

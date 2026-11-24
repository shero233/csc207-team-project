package use_case.expire_food_event;

/**
 * Output boundary for the expired food event use case.
 */
public interface ExpireFoodEventOutputBoundary {

    void prepareSuccessView(ExpireFoodEventOutputData outputData);
}
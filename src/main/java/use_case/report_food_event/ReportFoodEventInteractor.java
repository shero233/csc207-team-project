
package use_case.report_food_event;

import entity.Report;
import java.time.LocalDateTime;
import java.util.UUID;
import entity.ReportReason;

/**
 * Interactor for reporting a food event.
 */
public class ReportFoodEventInteractor implements ReportFoodEventInputBoundary {

    private final ReportFoodEventDataAccessInterface reportDataAccessObject;
    private final ReportFoodEventOutputBoundary reportPresenter;

    public ReportFoodEventInteractor(
            ReportFoodEventDataAccessInterface reportDataAccessObject,
            ReportFoodEventOutputBoundary reportPresenter) {
        this.reportDataAccessObject = reportDataAccessObject;
        this.reportPresenter = reportPresenter;
    }

    @Override
    public void execute(ReportFoodEventInputData inputData) {

        // Check whether the food event exists.
        if (reportDataAccessObject.getFoodEventById(inputData.getEventId()) == null) {
            reportPresenter.prepareFailView("Food Alert does not exist.");
            return;
        }

        // Validate the report reason.
        if (inputData.getReason() == null) {
            reportPresenter.prepareFailView("Please select a report reason.");
            return;
        }

        // Validate custom reason when OTHER is selected.
        if (inputData.getReason() == ReportReason.OTHER
                && (inputData.getCustomReason() == null
                || inputData.getCustomReason().isBlank())) {
            reportPresenter.prepareFailView("Please provide a custom reason.");
            return;
        }

        // Create and save the report.
        Report report = new Report(
                UUID.randomUUID().toString(),
                inputData.getEventId(),
                inputData.getReason(),
                inputData.getCustomReason(),
                LocalDateTime.now()
        );

        reportDataAccessObject.saveReport(report);

        // Prepare success output.
        ReportFoodEventOutputData outputData =
                new ReportFoodEventOutputData(
                        report.getReportId(),
                        report.getEventId()
                );

        reportPresenter.prepareSuccessView(outputData);
    }
}

package net.nemerosa.ontrack.boot.ui;

import net.nemerosa.ontrack.model.form.Form;
import net.nemerosa.ontrack.model.form.Selection;
import net.nemerosa.ontrack.model.security.SecurityService;
import net.nemerosa.ontrack.model.structure.*;
import net.nemerosa.ontrack.ui.controller.AbstractResourceController;
import net.nemerosa.ontrack.ui.resource.Resources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

@RestController
@RequestMapping("/structure")
public class ValidationRunController extends AbstractResourceController {

    private final StructureService structureService;
    private final ValidationRunStatusService validationRunStatusService;
    private final SecurityService securityService;

    @Autowired
    public ValidationRunController(StructureService structureService, ValidationRunStatusService validationRunStatusService, SecurityService securityService) {
        this.structureService = structureService;
        this.validationRunStatusService = validationRunStatusService;
        this.securityService = securityService;
    }

    @RequestMapping(value = "builds/{buildId}/validationRuns/view", method = RequestMethod.GET)
    public Resources<ValidationStampRunView> getValidationStampRunViews(@PathVariable ID buildId) {
        // Build
        Build build = structureService.getBuild(buildId);
        // Gets the views
        List<ValidationStampRunView> views = structureService.getValidationStampRunViewsForBuild(build);
        // Converts into a view
        URI uri = uri(on(getClass()).getValidationStampRunViews(buildId));
        return Resources.of(
                views,
                uri
        ).forView(ValidationStampRunView.class);
    }

    @RequestMapping(value = "builds/{buildId}/validationRuns", method = RequestMethod.GET)
    public Resources<ValidationRun> getValidationRuns(@PathVariable ID buildId) {
        return Resources.of(
                structureService.getValidationRunsForBuild(buildId),
                uri(on(BuildController.class).getLastPromotionRuns(buildId))
        ).forView(Build.class);
    }

    @RequestMapping(value = "builds/{buildId}/validationRuns/create", method = RequestMethod.GET)
    public Form newValidationRunForm(@PathVariable ID buildId) {
        Build build = structureService.getBuild(buildId);
        return Form.create()
                .with(
                        Selection.of("validationStamp")
                                .label("Validation stamp")
                                .items(structureService.getValidationStampListForBranch(build.getBranch().getId()))
                )
                .with(
                        Selection.of("validationRunStatusId")
                                .label("Status")
                                .items(validationRunStatusService.getValidationRunStatusRoots())
                )
                .description();
    }

    @RequestMapping(value = "builds/{buildId}/validationRuns/create", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ValidationRun newValidationRun(@PathVariable ID buildId, @RequestBody ValidationRunRequest validationRunRequest) {
        // Gets the build
        Build build = structureService.getBuild(buildId);
        // Gets the validation stamp
        ValidationStamp validationStamp = structureService.getValidationStamp(ID.of(validationRunRequest.getValidationStamp()));
        // Gets the validation run status
        ValidationRunStatusID validationRunStatusID = validationRunStatusService.getValidationRunStatus(validationRunRequest.getValidationRunStatusId());
        // Validation run to create
        ValidationRun validationRun = ValidationRun.of(
                build,
                validationStamp,
                securityService.getCurrentSignature(),
                validationRunStatusID,
                validationRunRequest.getDescription()
        );
        // Creation
        return structureService.newValidationRun(validationRun);
    }

    @RequestMapping(value = "validationRuns/{validationRunId}", method = RequestMethod.GET)
    public ValidationRun getValidationRun(@PathVariable ID validationRunId) {
        return structureService.getValidationRun(validationRunId);
    }

    // Validation run status

    @RequestMapping(value = "validationRuns/{validationRunId}/status/change", method = RequestMethod.GET)
    public Form getValidationRunStatusChangeForm(@PathVariable ID validationRunId) {
        ValidationRun validationRun = structureService.getValidationRun(validationRunId);
        return Form.create()
                .with(
                        Selection.of("validationRunStatusId")
                                .label("Status")
                                .items(
                                        validationRunStatusService.getNextValidationRunStatusList(validationRun.getLastStatus().getStatusID().getId())
                                )
                )
                .description()
                ;
    }

    @RequestMapping(value = "validationRuns/{validationRunId}/status/change", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ValidationRun validationRunStatusChange(@PathVariable ID validationRunId, @RequestBody ValidationRunStatusChangeRequest request) {
        // Gets the current run
        ValidationRun run = structureService.getValidationRun(validationRunId);
        // Gets the new validation run status
        ValidationRunStatus runStatus = ValidationRunStatus.of(
                securityService.getCurrentSignature(),
                validationRunStatusService.getValidationRunStatus(request.getValidationRunStatusId()),
                request.getDescription()
        );
        // Updates the validation run
        return structureService.newValidationRunStatus(run, runStatus);
    }

}

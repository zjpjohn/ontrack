package net.nemerosa.ontrack.model.security;

/**
 * List of predefined roles
 */
public interface Roles {
    /**
     * The project owner is allowed to all functions in a project, but for its deletion.
     */
    String PROJECT_OWNER = "OWNER";
    /**
     * A participant in a project is allowed to change statuses in validation runs.
     */
    String PROJECT_PARTICIPANT = "PARTICIPANT";
    /**
     * The validation manager can manage the validation stamps.
     */
    String PROJECT_VALIDATION_MANAGER = "VALIDATION_MANAGER";
    /**
     * The promoter can promote existing builds.
     */
    String PROJECT_PROMOTER = "PROMOTER";
    /**
     * The project manager can promote existing builds, manage the validation stamps,
     * manage the shared build filters and edit some properties.
     */
    String PROJECT_MANAGER = "PROJECT_MANAGER";
    /**
     * This role grants a read-only access to all components of the projects.
     */
    String PROJECT_READ_ONLY = "READ_ONLY";
    String GLOBAL_ADMINISTRATOR = "ADMINISTRATOR";
    String GLOBAL_CREATOR = "CREATOR";
    String GLOBAL_AUTOMATION = "AUTOMATION";
    String GLOBAL_CONTROLLER = "CONTROLLER";
}

package net.amcintosh.freshbooks.resources.api;

/**
 * Used internally to determine the style of FreshBooks API the resource conforms to.
 * Resources found under '<code>/accounting</code>' follow a particular style whereas those under
 * '<code>/projects</code>', '<code>/comments</code>', and '<code>/timetracking</code>' follow another.
 */
public enum ResourceType {
    ACCOUNTING_LIKE,
    ACCOUNTING_REPORT,
    AUTH,
    PROJECT_LIKE
}

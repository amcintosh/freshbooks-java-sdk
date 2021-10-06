package net.amcintosh.freshbooks.resources.api;

/**
 * Used internally to determine the style of FreshBooks API the resource conforms to.
 * Resources found under `/accounting` follow a particular style whereas those under
 * `/projects`, `/comments`, and `/timetracking` follow another.
 */
public enum ResourceType {
    ACCOUNTING_LIKE,
    AUTH,
    PROJECT_LIKE
}

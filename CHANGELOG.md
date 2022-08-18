# FreshBooks Java SDK Changelog

## Unreleased

## 0.5.0

- Remove deprecated, duplicated fields form Tasks resource model
- Change Items, Invoice LineItems quantities to Decimal type
- Added invoice banner image field and better documentation to invoice presentation
- Added tax fields to Tasks
- Fixed some incorrect types in Projects
- Add sort builder for List() calls

## 0.4.0

- Add search filter queries builder for list calls
- Upgrade dependencies 

## 0.3.0

- Add automatic retries on 429 and 5xx errors with exponential backoff

## 0.2.0

- Added Payments and Other Income resources
- Added Services, Service Rates, Tasks resources

## 0.1.0

Basic Invoicing workflow functionality

- Supports clients, invoices, line items, expenses

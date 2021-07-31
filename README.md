# FreshBooks Java SDK

A FreshBooks Java SDK to allow you to more easily utilize the [FreshBooks API](https://www.freshbooks.com/api).

## Installation

Using Maven:

```xml
<dependency>
  <groupId>net.amcintosh</groupId>
  <artifactId>freshbooks-sdk</artifactId>
  <version>0.0.2</version>
</dependency>
```

or Gradle:

```groovy
implementation 'net.amcintosh:freshbooks-sdk:0.0.2'
```

## Usage

See [https://amcintosh.github.io/freshbooks-java-sdk/](https://amcintosh.github.io/freshbooks-java-sdk/) for Javadocs.

### Configuring the API freshBooksClient

You can create an instance of the API client in one of two ways:

- By providing your application's OAuth2 `clientId` and `clientSecret` and following through the auth flow, which
  when complete will return an access token.
- Or if you already have a valid access token, you can instantiate the client with that token, however token refresh
  flows will not function without the application id and secret.

```java
import net.amcintosh.freshbooks.FreshBooksClient;

FreshBooksClient freshBooksClient = new FreshBooksClient.FreshBooksClientBuilder(
        "your application id", "your secret", "https://some-redirect")
        .build();
```

and then proceed with the auth flow (see below).

Or

```java
import net.amcintosh.freshbooks.FreshBooksClient;

FreshBooksClient freshBooksClient = new FreshBooksClient.FreshBooksClientBuilder(
        "your application id")
    .withToken("a valid token")
    .build();
```

#### Authoization flow

_This is a brief summary of the OAuth2 authorization flow and the methods in the FreshBooks API Client
around them. See the [FreshBooks API - Authentication](https://www.freshbooks.com/api/authentication) documentation._

First, instantiate your FreshBooksClient with `clientId`, `clientSecret`, and `redirectUri` as above.

To get an access token, the user must first authorize your application. This can be done by sending
the user to the FreshBooks authorization page. The authorization URL can be obtained by calling
`freshBooksClient.getAuthRequestUri()`. Once the user has clicked accept there, they will be
redirected to your `redirectUri` with an access grant code.

When the user has been redirected to your `redirectUri` and you have obtained the access grant code from the URL
parameters, you can exchange that code for a valid access token.

```java
AuthorizationToken token = freshBooksClient.getAccessToken(accessGrantCode);
```

This call stores the Authorization token in the FreshBooksClient for use, and returns the results to you
for later usage. Eg.

```java
FreshBooksClient freshBooksClient = new FreshBooksClient.FreshBooksClientBuilder(
        "808c90fa4f722ddd984ab8d88afc13427d3f6aad0898ec929cb2cb982835d5c7")
    .withAuthorizationToken(token);
```

The `AuthorizationToken` contains all of the relevant details from the token response including the `accessToken`,
`refreshToken`, `createdAt`, `expiresIn`, and the `scopes` the token is authorized for.

```java
assertEquals("my_access_token", token.getAccessToken());
assertEquals("my_refresh_token", token.getRefreshToken());
assertEquals(ZonedDateTime.of(2021, 7, 26, 16, 57, 6, 0, ZoneId.of("UTC")), token.getCreatedAt());
assertEquals(100, token.getExpiresIn());
assertEquals(ZonedDateTime.of(2021, 7, 26, 16, 58, 46, 0, ZoneId.of("UTC")), token.getExpiresAt());
assertEquals(ImmutableList.of("some:scope", "some:other:scope"), token.getScopes());
```

When the token expires, it can be refreshed with the `refreshToken` value in the `FreshBooksClient`:

```java
AuthorizationToken refreshedToken = freshBooksClient.refreshAccessToken();
assertEquals("my_new_access_token", refreshedToken.getAccessToken());
```

### Current User

FreshBooks users are uniquely identified by their email across our entire product. One user may act on several
Businesses in different ways, and our Identity model is how we keep track of it. Each unique user has an Identity,
and each Identity has Business Memberships which define the permissions they have.

See [FreshBooks API - Business, Roles, and Identity](https://www.freshbooks.com/api/me_endpoint) and
[FreshBooks API - The Identity Model](https://www.freshbooks.com/api/identity_model).

The current user can be accessed by:

```java
Identity currentUser = freshBooksClient.currentUser();

String userEmail = currentUser.getEmail();

List<BusinessMembership> businesses = currentUser.getBusinessMemberships();
BusinessRole businessRole = businesses.get(0).getRole();
```

### Making API Calls

Each resource in the client has provides calls for `get`, `list`, `create`, `update` and `delete` calls. Please note that some API resources are scoped to a FreshBooks `account_id` while others are scoped to a `business_id`. In general these fall along the lines of accounting resources vs projects/time tracking resources, but that is not precise.

Not all resources have full CRUD functions available. For example expense categories have `list` and `get` calls, but
are not deletable.

```java
import net.amcintosh.freshbooks.models.Client;
import net.amcintosh.freshbooks.models.Project;

Client client = freshBooksClient.clients().get(accountId, clientUserId);
Project project = freshBooksClient.projects().get(businessId, projectId);
```

Alternatively, resources can be instantiated directly.

```java
import net.amcintosh.freshbooks.resources.Clients;
import net.amcintosh.freshbooks.resources.Projects;

Clients clients = new Clients(freshBooksClient);
Client client = clients.get(accountId, clientUserId);

Project project = new Projects(freshBooksClient).get(businessId, projectId);
```

#### Get and List

API calls with a single resource return a model Class of the appropriate resource.
For active vs deleted resources, see [FreshBooks API - Active and Deleted Objects](https://www.freshbooks.com/api/active_deleted).

```java
import net.amcintosh.freshbooks.models.Client;

Client client = freshBooksClient.clients().get(accountId, clientUserId);

assertEquals(clientUserId, client.getId());
assertEquals("FreshBooks", client.getOrganization());
assertEquals(VisState.ACTIVE, client.getVisState());
```

API calls with returning a list of resources return a Class extending `ListResult` containing a `List` of the resource model
and pagination data (see Pagination below).

```java
import net.amcintosh.freshbooks.models.Client;
import net.amcintosh.freshbooks.models.ClientList;

ClientList clientListResponse = freshBooksClient.clients().list(accountId);
List<Client> clients = clientListResponse.getClients();

assertEquals("FreshBooks", clients.get(0).getOrganization());

for (Client client: clientListResponse.getClients()) {
    assertEquals("FreshBooks", client.getOrganization());
}
```

Response objects implements Map so the `get()` method can be used to access the JSON content. This can be useful if
the raw response data of a field is needed, or in the case where a field does not have a corresponding field in the
reponse object. This is most common in the case of 'includes' which can have special cases or undocumented
additional fields.

```java
assertEquals("FreshBooks", client.getOrganization());
assertEquals("FreshBooks", client.get("organization"));

assertEquals(VisState.ACTIVE, client.getVisState());
assertEquals(0, client.get("vis_state"));

assertEquals("Unexpected Data", client.get("some_undocumented_field"));
```

#### Create, Update, and Delete

API calls to create and update can either be called with a `Map` of the resource data,
or a model Class with the data populated. A successful call will return a model Class
as if a `get` call.

Create:

```java
// Create from object
Client clientToCreateOne = new Client();
clientToCreateOne.setEmail("john.doe@abcorp.com");

Client createdClientOne = freshBooksClient.clients().create(accountId, clientToCreateOne);
long createdClientOneId = createdClientOne.getId();

// Update from data map
HashMap<String, Object> clientToCreateTwo = new HashMap();
clientToCreateTwo.put("email", "john.doe@abcorp.com");

Client createdClientTwo = freshBooksClient.clients().create(accountId, clientToCreateTwo);
long createdClientTwoId = createdClientTwo.getId();
```

Update:

```java
Client existingClient = freshBooksClient.clients().get(accountId, clientUserId);
assertEquals("john.doe@abcorp.com", existingClient.getEmail());

// Update from object
existingClient.setEmail("new.email@abcorp.ca");
existingClient = freshBooksClient.clients().update(accountId, clientUserId, existingClient);
assertEquals("new.email@abcorp.ca", existingClient.getEmail());

// Update from data map
HashMap<String, Object> updateData = new HashMap();
updateData.put("email", "newer.email@abcorp.ca");
existingClient = freshBooksClient.clients().update(accountId, clientUserId, updateData);
assertEquals("new.email@abcorp.ca", existingClient.getEmail());
```

Delete:

```java
Client client = clients.delete(accountId, clientUserId);

assertEquals(VisState.DELETED, client.getVisState());
```

#### Error Handling

Calls made to the FreshBooks API with a non-2xx response result in a `FreshBooksException`.
This contains the error message, HTTP response code, FreshBooks-specific error number if one exists,
and in the case of invalid values, the offending field, value, and a validation message.

Example:

```java
try {
    clientResponse = freshBooksClient.clients().get(accountId, 12345); //652471);
} catch (FreshBooksException e) {
    assertEquals("Client not found.", e.getMessage());
    assertEquals("NOT FOUND", e.statusMessage);
    assertEquals(404, e.statusCode);
    assertEquals(1012, e.errorNo);
    assertEquals("ValidationError in client. userid='12345'.", e.getValidationError());
```

#### Pagination, Filters, and Includes

`list` calls can take a List of QueryBuilder objects that can be used to paginate, filter, and include
optional data in the response. See [FreshBooks API - Parameters](https://www.freshbooks.com/api/parameters) documentation.

##### Pagination

Pagination results are included in `ListResult` responses:

```java
import net.amcintosh.freshbooks.models.Pages;

ClientList clientListResponse = freshBooksClient.clients().list(accountId);
Pages pageResult = clientListResponse.getPages();

assertEquals("Pages{page=1, pages=1, perPage=30, total=6}", pageResult.toString());
assertEquals(1, pageResult.getPage());
assertEquals(1, pageResult.getPages());
assertEquals(30, pageResult.getPerPage());
assertEquals(6, pageResult.getTotal());
```

To make change a paginated call, pass a `PaginationQueryBuilder` object into the `list` call.

```java
import net.amcintosh.freshbooks.models.builders.PaginationQueryBuilder;

PaginationQueryBuilder paginator = new PaginationQueryBuilder(2, 4);
ArrayList<QueryBuilder> builders = new ArrayList();
builders.add(paginator);

ClientList clientListResponse = freshBooksClient.clients().list(accountId, builders);

assertEquals("Pages{page=2, pages=3, perPage=4, total=9}", clientListResponse.getPages().toString());
```

`PaginationQueryBuilder` can also be updated with the `page` and `perPage` functions.

```java
PaginationQueryBuilder paginator = new PaginationQueryBuilder(2, 4);
assertEquals("PaginationQueryBuilder{page=2, perPage=4}", paginator.toString());

paginator.page(3).perPage(5);
assertEquals("PaginationQueryBuilder{page=3, perPage=5}", paginator.toString());
```

##### Filters

TODO

##### Includes

To include additional relationships, sub-resources, or data in a list or get response, a `IncludesQueryBuilder`
can be constructed.

```java
import net.amcintosh.freshbooks.models.builders.IncludesQueryBuilder;

IncludesQueryBuilder includes = new IncludesQueryBuilder().include("outstanding_balance");
assertEquals("IncludesQueryBuilder{includes=[outstanding_balance]}", query.toString());
```

Which can then be passed into `get` or `list` calls:

```java
// Get call
Client client = freshBooksClient.clients().get(accountId, clientId, includes);
assertEquals("USD", client.getOutstandingBalance().getCode());

// List call
ArrayList<QueryBuilder> builders = new ArrayList();
builders.add(includes);

ClientList clientListResponse = freshBooksClient.clients().list(accountId, builders);
List<Client> clients = clientListResponse.getClients();
assertEquals("USD", clients.get(0).getOutstandingBalance().getCode());
```

##### Sorting

## Development

### Testing

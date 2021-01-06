# FreshBooks Java SDK

The FreshBooks Java SDK allows you to more easily utilize the [FreshBooks API](https://www.freshbooks.com/api).

## Installation

## Usage

TODO: See for module documentation.

### Configuring the API freshBooksClient

You can create an instance of the API client in one of two ways:

- By providing your application's OAuth2 `clientId` and `clientSecret` and following through the auth flow, which 
  when complete will return an access token
- Or if you already have a valid access token, you can instantiate the client with that token, however token refresh 
  flows will not function without the application id and secret.

```java
import net.amcintosh.freshbooks.FreshBooksClient;

FreshBooksClient freshBooksClient = new FreshBooksClient.FreshBooksClientBuilder(
        "your application id", "your secret", "https://some-redirect")
        .withToken(<a valid token>)
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

### Current User

### Making API Calls

#### Get and List

#### Create, Update, and Delete

#### Error Handling

#### Pagination, Filters, and Includes

##### Filters

##### Includes

##### Sorting

## Development

### Testing


### Documentations

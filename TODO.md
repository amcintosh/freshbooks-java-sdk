## Work to do

- Add includes objects to models


## ExponentialBackOff

https://googleapis.github.io/google-http-java-client/exponential-backoff.html

```java
ExponentialBackOff backoff = new ExponentialBackOff.Builder()
  .setInitialIntervalMillis(500)
  .setMaxElapsedTimeMillis(900000)
  .setMaxIntervalMillis(6000)
  .setMultiplier(1.5)
  .setRandomizationFactor(0.5)
  .build();
request.setUnsuccessfulResponseHandler(
  new HttpBackOffUnsuccessfulResponseHandler(backoff));
```

## Output content

```java
content = new JsonHttpContent(JSON_FACTORY, data);
ByteArrayOutputStream out = new ByteArrayOutputStream();
content.writeTo(out);
System.out.println(new String(out.toByteArray()));
```

## Mocks

```java
TestedClass tc = spy(new TestedClass());
LoginContext lcMock = mock(LoginContext.class);
when(tc.login(anyString(), anyString())).thenReturn(lcMock);
```

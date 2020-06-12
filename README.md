# embedded-toxiproxy
Toxiproxy embedded server for Java integration testing

[![Build Status](https://travis-ci.org/coffee-break/embedded-toxiproxy.svg?branch=master)](https://travis-ci.org/coffee-break/embedded-toxiproxy)
[![codecov.io](https://codecov.io/github/coffee-break/embedded-toxiproxy/coverage.svg?branch=master)](https://codecov.io/github/coffee-break/embedded-toxiproxy?branch=master)

Maven dependency
==============

Maven Central:
```xml
<dependency>
  <groupId>io.github.coffee-break</groupId>
  <artifactId>embedded-toxiproxy-junit4</artifactId>
  <version>0.1.0</version>
</dependency>
```

Usage
==============

Running ToxiproxyServer is as simple as:
```java
ToxiproxyServer toxiproxyServer = new ToxiproxyServer();
toxiproxyServer.start();
// do some work
toxiproxyServer.stop();
```

Or you can use junit rule as:
```java
@ClassRule
public static ToxiproxyServerRule toxiproxyServerRule = new ToxiproxyServerRule(ToxiproxyServerConfiguration.builder().timeout(5000));
```

Also a client junit rule has been provided to use as:
```java
    private final static ProxyConfiguration proxyConfiguration = ProxyConfiguration.builder()
            .name("database")
            .host("localhost")
            .listenPort(9124)
            .upstreamPort(9123)
            .build();

    @ClassRule
    public static final ToxiproxyRule databaseWithToxiproxy = new ToxiproxyRule(proxyConfiguration, new SqlDatabaseServerRule());


```


Toxiproxy version
==============
Currently only 2.1.4 is provided


License
==============
Licensed under the Apache License, Version 2.0


Contributors
==============
 * Fatih I. ([@fincefid](http://github.com/fincefid))


Changelog
==============

### 0.1.0
 * Initial release
 
 ### 0.2.0
 * Add ToxiproxyRule for simplified setup
  
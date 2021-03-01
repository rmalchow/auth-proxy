# AUTH

this is a reverse proxy with authentication check based on zuul. it will check incoming requests for authentication info, and, if none are found, redirect to a login page. If valid authentication information _is_ found, the request is passed through. 

To do full custom configuration, a authentication.yaml file is needed, in docker this would be:

```
docker run -v path/to/application.yml:/app/application.yml [image]
```

#### docker image

the latest docker image is at:

> harbor.rand0m.me/public/auth

#### general config

here is the core part of an example application yaml file:

```

## basic standard stuff ##
server.servlet.contextPath: /
spring.resources.cache.period: 1
spring.thymeleaf.cache: false
security.basic.enabled: false
spring.mvc.static-path-pattern: /**
spring.resources.static-locations: classpath:/META-INF/resources/,classpath:/resources/,classpath:/static/,classpath:/public/

## the url that is publicly visible. used for redirects 
host: "https://www.example.com/"   

zuul:
  ignoredHeaders: []

  # needed if you want to use the login page:
  ignoredPatterns:
   - /__auth__/**
  sensitiveHeaders: []
  host.socket-timeout-millis: 60000
  
  # backend route config. if you use eureka, this
  # can also dynamic
  routes:
    static:
      path: /**
      url: "${BACKEND_URL:http://192.168.71.220:8096}/"
```

#### Path Authenticator

to completely deny or allow certain paths, you can use the path authenticator. This checks the request URI against allow- and deny patterns (regex):

```
path:
  enabled: true
  allowPatterns:
  - /dist/.*
  - /public/assets/.*
  - .*.js
  - /login
  denyPatterns:
  - /api/secret/.*
  - /foobar/not_for_you/.*
```

#### JWT Authenticator

This checks for the presence of a valid JWT cookie (issued after a successful login through the login page). No further configuration. If someone feels like it, this could be extended to allow checking against other public keys so that JWTs can be issued and signed somewhere else.

#### LDAP Authenticator

This authenticator kicks in after the login page and checks a user against an LDAP server:

```
ldap:
   enabled: true
   url: "ldap://10.0.0.2:389"
   base: o=foobar,dc=example.com
   binduser: uid=binduser,ou=system
   bindpassword: s3cr3t
   filter: "(|(uid={{username}})(mail={{username}})(userPrincipalName={{username}}))"
```

#### ClientIpAuthenticator

This authenticator looks at a x-forwarded-for header and checks it against a list of CIDR ranges

```
clientIp:
   enabled: true
   forwardedForHeader: "X-FORWARDED-FOR"
   base: o=foobar,dc=example.com
   binduser: uid=binduser,ou=system
   bindpassword: s3cr3t
   filter: "(|(uid={{username}})(mail={{username}})(userPrincipalName={{username}}))"
```


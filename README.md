# AUTH

this is an auth proxy. if your browser is coming in with a valid JWT token in a cookie, we pass the requests to the backend. if not, you get redirected to a login page.

# general config

- BACKEND_URL - the URL of the backend. e.g. http://192.168.0.22:8080
- HOST - the URL visible on the outside. e.g. https://foobar.example.com/   --- note that this _must_ include the trailing slash


# authentication modules:

- none so far, but planned are: 
	- BEARER token:
		- public key to verify JWT signed by someone else
		- fixed API keys
	- basic auth against ldap 
	- login page agains

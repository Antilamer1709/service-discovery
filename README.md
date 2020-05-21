# Service discovery

### Gateways

There are 2 gateway projects. Each of them uses EurekaServer and ZuulProxy modules.

To run this example you have to edit your hosts file with:
````
127.0.0.1 gateway1
127.0.0.1 gateway2
````

Alternatively you can edit all of the config files to use your host.

### Clients
                                        
There are 2 client projects. They register themselves in the Gateways.

There are several examples
- call a gateway with a `api/client1` or `api/client2` prefix to be proxied to an appropriate microservice
- call a `api/client2/{userId}` which gets a `client1` address from a `discoveryClient`
- upload files to `client2` which gets a `client1` address from a `discoveryClient` and passes files to the `client1` to be processed
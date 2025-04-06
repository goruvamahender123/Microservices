### Good Practices
1. Don't return the Entity class directly from the controller instead create a Model response class which can be returned from the controller instead of returning the fetched data directly from the DB.
2. From Spring 5 resttemplate is deprecated.(Recommended not to use it by Spring in the future) as it is deprecated spring removed auto configuration so you need to setup autoconfiguration manually.


#### Context path in Spring
1. In the context of Spring Framework, the term "context path" typically refers to the base URL path under which your Spring application is deployed on the server. It's not directly related to Java itself but rather how your Spring application is configured and accessed.
2. When you deploy a Spring application (typically a web application), it gets mapped to a context path on the server. For example, if your application is named "myapp", and you deploy it to a server, it might be accessed via a URL like http://localhost:8080/myapp. Here, /myapp is the context path.

# Understanding RestTemplate and Non-Blocking Alternatives in Spring Boot

## Blocking Nature in RestTemplate
When you use **RestTemplate** in Spring Boot to make HTTP requests, it operates in a synchronous and blocking manner by default. This means that when your application makes an HTTP request using RestTemplate, it waits (or blocks) until it receives a response from the server. During this time, the thread handling the request is occupied and cannot be used for handling other requests.

## Using Different Tomcat Server Instances with Dynamic Port Allocation
To address the blocking nature of RestTemplate calls, one approach is to configure multiple instances of the embedded Tomcat server in Spring Boot, each running on a different port. This can be achieved by setting `server.port = 0` in your `application.properties` or `application.yml` file, which tells Spring Boot to choose a random available port for each instance.

### Example Configuration
```properties
server.port = 0
```

This setup theoretically allows multiple RestTemplate calls to different instances to run concurrently without blocking each other, as they use different threads and ports.

## Why This Approach Might Be Costly
However, this approach can be costly in terms of resource utilization. Running multiple instances of Tomcat means increased memory and CPU usage, especially in production environments where scalability and resource efficiency are critical. Each instance requires its own set of resources, which might not be optimal for all applications, especially those with limited resource budgets.

## Thread Pool Concept in Client-Server Communication
When a client (your application using RestTemplate) sends a request to a server (REST API endpoint), a thread from the client's thread pool is used to handle that request. Here’s a deeper look into how this works:

1. **Client Request**: When your application sends an HTTP request using RestTemplate, it borrows a thread from its thread pool to handle the request.
2. **Thread Blocking**: The thread remains blocked (or in a waiting state) until the server responds. During this time, the thread cannot handle any other requests.
3. **Response Handling**: Once the server responds, the thread processes the response and becomes available again for handling new requests.

### Impact of Blocking
- **Resource Utilization**: Blocking threads consume system resources (CPU, memory) while waiting for responses, limiting the overall scalability of your application.
- **Concurrency**: With blocking I/O, a limited number of threads can handle a limited number of concurrent requests. If too many threads are blocked, new requests may be delayed or rejected.

## Non-Blocking Alternatives
To mitigate the blocking nature of RestTemplate, especially in scenarios requiring high concurrency and responsiveness, consider using:

- **AsyncRestTemplate**: Deprecated in newer Spring versions in favor of WebClient, but provides asynchronous capabilities.
- **WebClient**: The preferred non-blocking HTTP client in Spring WebFlux, which supports reactive programming and is designed for high concurrency.

## Conclusion
Understanding the blocking nature of RestTemplate calls and the implications of using multiple Tomcat instances with dynamic port allocation is crucial for designing scalable and efficient applications. While this approach can theoretically increase concurrency, it may not be cost-effective due to increased resource usage. Implementing non-blocking alternatives like WebClient in reactive programming models can provide better scalability and performance in modern applications.


# 📄 Blocking Behavior in Spring Boot: DB and REST Calls

## 1. Overview

This document explains how **blocking operations** occur in Spring Boot when using traditional JDBC-based DB calls and `RestTemplate` for REST calls. Examples are provided along with a recommended non-blocking alternative.

---

## 2. Blocking with Database Calls

### Scenario

A controller fetches user data from a database using Spring Data JPA.

### 🔴 Blocking Example

```java
@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        // BLOCKING call to DB
        User user = userRepository.findById(id).orElseThrow();
        return ResponseEntity.ok(user);
    }
}
```

### What Happens:
- Thread is **allocated**.
- JDBC call **blocks the thread** until DB responds.
- If DB is slow, the thread **sits idle** and can't serve other requests.

---

## 3. Blocking with RestTemplate

### Scenario

Calling another microservice via HTTP using `RestTemplate`.

### 🔴 Blocking Example

```java
@RestController
public class ExternalUserController {

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/external-user/{id}")
    public ResponseEntity<String> getUserFromService(@PathVariable Long id) {
        // BLOCKING call to another service
        String response = restTemplate.getForObject("http://user-service/api/user/" + id, String.class);
        return ResponseEntity.ok(response);
    }
}
```

### What Happens:
- A thread is picked from the pool.
- External call via `RestTemplate` blocks the thread.
- Response time of external service directly impacts server capacity.

---

## 4. Blocking with Both DB and REST Call

```java
@GetMapping("/user-details/{id}")
public ResponseEntity<UserDetailsDto> getUserDetails(@PathVariable Long id) {
    User user = userRepository.findById(id).orElseThrow(); // BLOCKING
    String rating = restTemplate.getForObject("http://rating-service/api/rating/" + id, String.class); // BLOCKING
    return ResponseEntity.ok(new UserDetailsDto(user, rating));
}
```

- Both operations block the same thread.
- Under load, threads pile up leading to resource exhaustion.

---

## 5. Non-blocking Alternative: WebClient + R2DBC

```java
@GetMapping("/user-details/{id}")
public Mono<UserDetailsDto> getUserDetails(@PathVariable Long id) {
    Mono<User> userMono = userRepository.findById(id); // Non-blocking (R2DBC)
    Mono<String> ratingMono = webClient.get()
                                       .uri("http://rating-service/api/rating/" + id)
                                       .retrieve()
                                       .bodyToMono(String.class); // Non-blocking

    return Mono.zip(userMono, ratingMono)
               .map(tuple -> new UserDetailsDto(tuple.getT1(), tuple.getT2()));
}
```

✅ No thread blocking  
✅ Better resource utilization  
✅ Highly scalable

---

## 6. Summary Table

| Call Type            | Blocking? | Causes Thread to Wait? | Scalable? |
|----------------------|-----------|-------------------------|-----------|
| JDBC (JPA)           | ✅ Yes     | ✅ Yes                   | ❌ No      |
| RestTemplate         | ✅ Yes     | ✅ Yes                   | ❌ No      |
| R2DBC (Reactive DB)  | ❌ No      | ❌ No                    | ✅ Yes     |
| WebClient            | ❌ No      | ❌ No                    | ✅ Yes     |

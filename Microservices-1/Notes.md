### Good Practices
1. Don't return the Entity class directly from the controller instead create a Model response class which can be returned from the controller instead of returning the fetched data directly from the DB.
2. From Spring 5 resttemplate is deprecated.(Recommended not to use it by Spring in the future) as it is deprecated spring removed auto configuration so you need to setup autoconfiguration manually.


#### Context path in Spring
1. In the context of Spring Framework, the term "context path" typically refers to the base URL path under which your Spring application is deployed on the server. It's not directly related to Java itself but rather how your Spring application is configured and accessed.
2. When you deploy a Spring application (typically a web application), it gets mapped to a context path on the server. For example, if your application is named "myapp", and you deploy it to a server, it might be accessed via a URL like http://localhost:8080/myapp. Here, /myapp is the context path.
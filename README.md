### How to Use
To run our project, the only technology required is an IDE such as Intellij and MySQL. First, a user will need to
execute our data dump in their local connection, done through an application like MySQL. To configure the connection
from our Java project to their local MySQL connection, a user must change the dbUrl, dbUsername, and dbPassword fields
in the `ConnectionUtil.java` class located in `src/main/java.com.dishianerifkinj.util`. From there, our application is
usable after running the main method located in `CommandLineApplication.java` file, which is located in
`src/main/java.com.dishianerifkinj`, runnable in the user’s desired IDE. With our intuitive design, the user is guided
through their interaction in the console with prompts, which will inquire the user to make actions based off of 
numerically ordered options, and text based queries.



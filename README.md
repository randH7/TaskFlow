# TaskFlow Pro
TaskFlow Pro is a practical project that offers hands-on experience in developing a web application for task and project management with a strong focus on collaboration. By using CRUD operations, managing user profiles, and integrating real-time updates through the Spring framework, TaskFlow Pro empowers users to streamline their work and personal projects efficiently.

<br>

## 📝 Class Diagram
![TaskFlowClass](https://github.com/randH7/TaskFlow/assets/107724456/1bd15627-7ccb-414e-a227-11c460f7ced1)

<br>

## 📦 Prerequisites
- JDK Java 17 
- Any IDE support Java and Spring Boot
- MySQL 8.0
- MySQL Workbench 8.0

  <br>
  
## 🔧 Setup
1. Clone the repository to your local machine.
2. Install the necessary dependencies.  
3. Set up your MySQL database and update the `application.properties` file with your database credentials:
   
   ```
    spring.datasource.url=jdbc:mysql://localhost:3306/task_flow_schema
    spring.datasource.username=root
    spring.datasource.password=root
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true
    server.error.include-stacktrace=never
   ```
4. Import the Postman collection from [HERE](https://restless-desert-450152.postman.co/workspace/Team-Workspace~520315af-b391-468e-8ef1-10c57a0ce45b/collection/26776231-ee28c9ce-85a9-454f-b7dd-96ab438c65c9?action=share&creator=26776231) its have all the endpoints.
5. Run the application.

<br> 

## 💻 Technology used
- Backend Development: Spring Web with JDK Java 17
- Data Persistence: Spring Data JPA
- Managing Database: MySQL Workbench
- Authentication and Authorization: Spring Security
- Testing the API endpoints: Postman

<br>

## 🌐 API Endpoints

### User Authentication and Management:
- `POST` `http://localhost:8082/taskflow/sign-up?userType={userType}` : Register a new user (both manager and team member). The request body should include the user's registration details in JSON format and `{userType}` could be manger or teamMember only.

- `GET` `http://localhost:8082/taskflow/signin?usernameOrEmail={usernameOrEmail}&password={password}` : Login an existing user (both manager and team member). The query params should include the user's username or email and the user's password.

  
### Project Management:
- `POST` `http://localhost:8082/taskflow/projects/create-project` : Create a new project (for any manager). The request body should include the project details in JSON format.
  
- `PATCH` `http://localhost:8082/taskflow/projects/edit-project/{projectId}` : Update a project (only for the manager who created the project). The request body should include the updated project details in JSON format and `{projectId}` for the specific project.
  
- `DELETE` `http://localhost:8082/taskflow/projects/delete-project/{projectId}` : Delete a project (only for the manager who created the project). Replace `{projectId}` for the specific project.
  
- `GET` `http://localhost:8082/taskflow/projects` : This endpoint should return a list of projects created by the manager or that the team member is assigned to.


### Task Management:
- `POST` `http://localhost:8082/taskflow/projects/{projectId}/add-task` : Add a new task to a specific project (for any team member who works on the project). The request body should include the task details in JSON format and `{projectId}` for the specific project.
  
- `PATCH` `http://localhost:8082/taskflow/projects/{projectId}/edit-tasks/{taskId}` : Update a task (for any team member). The request body should include the updated task details in JSON format. The path variables should replace `{taskId}` with the specific task ID and `{projectId}` with the specific project that has this task. 

- `DELETE` `http://localhost:8082/taskflow/projects/{projectId}/delete-tasks/{taskId}` : Delete a task (for any team member). The path variables should replace `{taskId}` with the specific task ID and `{projectId}` with the specific project that has this task.
  
- `GET` `http://localhost:8082/taskflow/my-tasks` : Retrieve a list of the tasks that the team member who works on in all projects.

<br>

## 🗃️ Future Work
- Users can update their profiles, view their task history, and log out.
- Users can comment on tasks, share files, and have discussions.
- Users can view the recent Activity for each project.
- Frontend framework (Angular) for building the user interface.
- WebSocket for real-time task updates and notifications.
- Implement email or in-app notifications for task assignments, updates, and reminders.

<br>

## 🔗 Extra links
- [My Trello](https://trello.com/invite/b/tBnAEQNb/ATTI7d2d50d1771e2b1b9eb9f3e6f867bfbcA4259FB3/taskflow)
- [My presentation](https://www.canva.com/design/DAFyVn0L2aU/UFnpNoBAmP4_N0LAnmyuBQ/edit?utm_content=DAFyVn0L2aU&utm_campaign=designshare&utm_medium=link2&utm_source=sharebutton)

<br>

## 📚 Resources
Spring Security:
- [Spring Security 6 | How to Create a Login System with Spring Data JPA and JWTs - NEW 2023](]https://youtu.be/TeBt0Ike_Tk?si=nEiw7xYIbIqYcijW)
- [Spring Security Tutorial - NEW 2023](https://youtu.be/b9O9NI-RJ3o?si=EynGsrSMx6fFay0e)
- [Spring Security – Configuring Different URLs](https://www.baeldung.com/spring-security-configuring-urls)
- [Stack Overflow - resolve method 'antMatchers()'](https://stackoverflow.com/questions/74753700/cannot-resolve-method-antmatchers-in-authorizationmanagerrequestmatcherregis)
- [Stack Overflow - unidirectional many-to-one and cascading delete](https://stackoverflow.com/questions/7197181/jpa-unidirectional-many-to-one-and-cascading-delete)

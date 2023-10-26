# TaskFlow Pro
TaskFlow Pro is a practical project that offers hands-on experience in developing a web application for task and project management with a strong focus on collaboration. By using CRUD operations, managing user profiles, and integrating real-time updates through the Spring framework, TaskFlow Pro empowers users to streamline their work and personal projects efficiently.

<br>

## 📝 Class Diagram
![TaskFlowClass](https://github.com/randH7/TaskFlow/assets/107724456/1bd15627-7ccb-414e-a227-11c460f7ced1)

<br>

## 📦 Prerequisites
- JDK Java 17 
- Any IDE support Java nad Spring Boot
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
4. Import postman file from the project its have all the endpoints
5. Run the application

<br> 

## 💻 Technology used
- Backend Development: Spring Web with JDK Java 17
- Data Persistence: Spring Data JPA
- Manging Database: MySQL Workbench
- Authentication and Authorization: Spring Security
- Testing the API endpoints: Postman

<br>

## 🌐 API Endpoints
![image](https://github.com/randH7/TaskFlow/assets/107724456/4255ef33-256f-4067-adf3-c21ed50a3679)

<br>

## 🗃️ Future Work
- Users can update their profiles, view their task history, and log-out.
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
- [Stack OverFlow - resolve method 'antMatchers()'](https://stackoverflow.com/questions/74753700/cannot-resolve-method-antmatchers-in-authorizationmanagerrequestmatcherregis)

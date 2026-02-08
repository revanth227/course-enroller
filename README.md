---

# CourseEnroller - Backend API

A robust Spring Boot Learning Management System (LMS) backend designed to manage student enrollments, content discovery, and granular progress tracking.

## 🚀 Features

* **Authentication & Security**: Integrated with Spring Security to handle user sessions and secure endpoints.
* **Intelligent Search**: A unified search API that scans through Course titles, Topic descriptions, and even the deep markdown content of Subtopics.
* **Enrollment Management**: Prevents double-enrollment and manages the relationship between students and their chosen courses.
* **Granular Progress Tracking**: Track completion at the **SubTopic** level. The system automatically recalculates the overall course completion percentage every time a student marks a lesson as done.
* **No-Stream Logic**: Core business logic is implemented using reliable, readable nested loops for maximum transparency in data processing.

---

## 🛠 Tech Stack

* **Java 17+**
* **Spring Boot 3.x**
* **Spring Data JPA** (Hibernate)
* **PostgreSQL** (Recommended)
* **Lombok** (For boilerplate-free code)

---

## 📊 Database Schema

The system uses a hierarchical data model to organize content and a relational mapping for user activity.

* **Course**: The top-level entity containing a list of Topics.
* **Topic**: Groups related SubTopics together.
* **SubTopic**: The actual lesson content (Markdown supported).
* **Enrollment**: Maps a User to a Course.
* **Progress**: Maps a User to a SubTopic to track completion status.

---

## 📈 Key Logic: Progress Calculation

The completion percentage is calculated dynamically to ensure real-time accuracy on the student dashboard.

The formula used is:


This is achieved by iterating through the `TopicList` and `SubTopicList` of a course and verifying completion against the `Progress` table for the specific user.

---

## 🛣 API Endpoints

### Search

`GET /api/search?q={query}`

* Returns a list of courses matching the query.
* *Note:* Searching "Newton" will return Physics (Dynamics) as well as Mathematics (Derivatives) due to cross-topic content references.

### Enrollment

`POST /api/enroll/{courseId}`

* Enrolls the authenticated user into a specific course.
* Validation: Checks if the user is already enrolled.

### Progress

`POST /api/subtopics/{subtopicId}/complete`

* Marks a subtopic as finished.
* Validation: Ensures the user is enrolled in the parent course first.

`GET /api/progress/{enrollmentId}`

* Returns the `ProgressDto2` containing total subtopics, completed count, and the calculated percentage.

---

## ⚙️ Setup & Installation

1. **Clone the repository**:
```bash
git clone https://github.com/your-username/CourseEnroller.git

```


2. **Configure Database**: Update `src/main/resources/application.properties` with your PostgreSQL credentials.
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/course_db
spring.datasource.username=postgres
spring.datasource.password=yourpassword

```


3. **Run the Application**:
```bash
mvn spring-boot:run

```



---

## 📝 Development Notes

* **Case Sensitivity**: All search queries are normalized using `LOWER()` at the database level to ensure "physics" and "Physics" return the same results.
* **Loop Architecture**: The service layer avoids the Stream API to maintain a strictly imperative style, making it easier to debug complex object hierarchies.

---


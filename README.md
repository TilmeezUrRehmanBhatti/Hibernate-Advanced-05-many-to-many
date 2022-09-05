# ManyToMany
**Many-to_Many Mapping**
+ A course can have many students
+ A student can have many courses

<img src="https://user-images.githubusercontent.com/80107049/188494776-dc28e112-0acb-4d07-a4dd-325eef4afebe.png" width=300 />




+ Need to track which student is in which course and vice-versa

<img src="https://user-images.githubusercontent.com/80107049/188494840-8cf44e6a-1d9f-4824-8120-533728664394.png" width = 400 />




**Join Table**
A table that provides a mapping between two tables.

It has foreign keys for each table to define the mapping relationship.

**@ManyToMany**

<img src="https://user-images.githubusercontent.com/80107049/188494885-ce88cca4-708d-45dc-bb91-02cb11d2f190.png" width=300 />



**Development Process: Many-to-Many**
1. Prep Work - Define database tables
2. Update **Course** class
3. Update **Student** class
4. Create Main App

_Step 1:Define database tables_

**join table:course_Student**
```POSTGRESQL
CREATE TABLE course_student
(
    course_id  BIGINT NOT NULL,
    student_id BIGINT NOT NULL,
    PRIMARY KEY (course_id, student_id)
  ...
);
```
**join table:course_Student - foreign keys**
```POSTGRESQL
CREATE TABLE course_student
(
  ...
   CONSTRAINT FK_COURSE_05
        FOREIGN KEY (course_id)
            REFERENCES course (id),
    CONSTRAINT FK_STUDENT
        FOREIGN KEY (student_id)
            REFERENCES student(id)
  ...
);
```

_Step 2:Update Course -reference students_
```JAVA
@Entity
@Table(name="course")
public class Course {
  ...
  
  private List<Student> students;
  // getters / setters
 ... 
}
```

**Add @ManyToMany annotation**
```JAVA
@Entity
@Table(name="course")
public class Course {
  ...
  
  @ManyToMany
  @JoinTable(
    name="course_student",
    joinColumns=@JoinColumn(name="course_id"),
    inverseJoinColumns=@JoinColumn(name="studnet_id")
    )
  private List<Studnet> student;
  // getters / setters
 ... 
}
```
+ `@JoinTable( name="course_student"` refer to join table "course_student"
+ `joinColumns=@JoinColumn(name="course_id"),` Refers to "course_id" column in "course_student" join table
+ `inverseJoinColumns=@JoinColumn(name="studnet_id")` Refers to "student_id" column in "course_student" join table
+ @JoinTable tells Hibernate
  + Look at the course_id column in the **course_student** table
  + For other side (inverse), look at the student_id column in the **course_student** table
  + Use this information to find relationship between **course** and **students**

**Inverse**
+ In this context, we are defining the relationship in the **Course** class
+ The **Student** class is on the "other side" ... so it is considered the "inverse"
+ "Inverse" refers to the "other side" of the relationship

<img src="https://user-images.githubusercontent.com/80107049/188494938-99c70f54-7393-49ef-8859-9adf057d5ecb.png" width= 400 />



> Now, let's do a similar thing for Student just going the other way ...
>

_Step 3:Update Student - reference course_
```JAVA
@Entity
@Table(name="student")
public class Student {
  ...
  
  private List<Course> courses;
  // getter / setters
 ... 
}
```

**Add @ManyToMany annotation**

```JAVA
@Entity
@Table(name="student")
public class Student {
  ...
  
  @ManyToMany
  @JoinTable(
    joinColumns=@JoinColumn(name="student_id"),
    inverseJoinColumns=@JoinColumn(name="course_id")
    )
  private List<Course> courses;
  
  // getters / setters
 ... 
}
```
+ `joinColumns=@JoinColumn(name="student_id")` Refers to "student_id" column in "course_student" join table
+ `inverseJoinColumns=@JoinColumn(name="course_id")` Refers to "course_id" column in "course_student" join table
+ @JoinTable tells Hibernate
  + Look at the student_id column in the **course_student** table
  + For other side(inverse), look at the course_id column in the **course_student** table
  + Use this information to find relationship between **student** and **course**

**inverse**
+ In this context, we are defining the relationship in the **Student** class
+ The **Course** class in in the "other side" ... so it is considered the "inverse"
+ "Inverse" refers to the "other side" of the relationship



<img src="https://user-images.githubusercontent.com/80107049/188494997-2515f0c4-678f-4ee1-86d1-eb74a41be631.png" width= 400 />


_Step 4:Create Main App_
```JAVA
public static void main(String[] args) {
  ...
    
  // get the object
  int theId = 10;
  Course tempCourse = session.get(Course.class, theId);
  
  // print the course
  System.out.println("tempCourse: " + tempCourse);
  
  // print the associated studens
  System.out.println("students: " + tempCourse.getStudents());
 ... 
}
```

**Real-World Project Requirement**
+ If you delete a course, DO NOT delete the students
+ DO NOT apply cascading deletes!!


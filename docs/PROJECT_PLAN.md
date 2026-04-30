# Project Plan & Todo List: Hostel Complaint & Resource Tracking System

This document outlines an atomic, sequentially ordered breakdown, incorporating overlapping dependencies to enable efficient development across the frontend and backend. 

## Phase 1: Project Setup & Database Configuration
**Dependency:** None (Initial Phase)

- [ ] **Task 1.1: Initialize Spring Boot Project**
  - [ ] Subtask 1.1.1: Open Spring Initializr (start.spring.io) or use IDE to initialize project.
  - [ ] Subtask 1.1.2: Select Java version and Maven/Gradle build system.
  - [ ] Subtask 1.1.3: Add Spring Web, Spring Data JPA, MySQL Driver, Thymeleaf, Spring Security, & DevTools dependencies.
  - [ ] Subtask 1.1.4: Extract and import the project into the IDE (Eclipse/IntelliJ).

- [ ] **Task 1.2: Configure Database Connections**
  - [ ] Subtask 1.2.1: Open `application.properties`/`application.yml`.
  - [ ] Subtask 1.2.2: Set `spring.datasource.url`, `username`, and `password` for MySQL connection.
  - [ ] Subtask 1.2.3: Configure `spring.jpa.hibernate.ddl-auto` to `update` or `create-drop` for dev.
  - [ ] Subtask 1.2.4: Test database connection locally by running the application.

- [ ] **Task 1.3: Setup Core Global Architectures**
  - [ ] Subtask 1.3.1: Create an `ApiResponse` DTO class for uniform JSON responses.
  - [ ] Subtask 1.3.2: Implement `GlobalExceptionHandler` with `@ControllerAdvice`.
  - [ ] Subtask 1.3.3: Add custom exceptions (e.g., `ResourceNotFoundException`, `UnauthorizedException`).

- [ ] **Task 1.4: Database Schema Design**
  - [ ] Subtask 1.4.1: Identify User fields (ID, Name, Email, Password, Role).
  - [ ] Subtask 1.4.2: Identify Complaint fields (ID, Title, Desc, Category, Room, Status, Student_ID, Staff_ID).
  - [ ] Subtask 1.4.3: Identify Resource fields (ID, Name, Quantity, Status).

- [ ] **Task 1.5: JPA Entity Creation**
  - [ ] Subtask 1.5.1: Create `User` entity with `@Entity` and define columns.
  - [ ] Subtask 1.5.2: Create `Complaint` entity with mappings (Many-to-One to Student user, Many-to-One to Staff user).
  - [ ] Subtask 1.5.3: Create `Resource` entity and field mappings.

## Phase 2: Authentication & Authorization Modules
**Dependency:** Completion of Phase 1

- [ ] **Task 2.1: Establish User Roles**
  - [ ] Subtask 2.1.1: Create a `Role` Enum containing `STUDENT`, `ADMIN`, `STAFF`.
  - [ ] Subtask 2.1.2: Add to the `User` entity the `Role` column (or multiple roles).

- [ ] **Task 2.2: Build Spring Security Groundwork**
  - [ ] Subtask 2.2.1: Create `UserRepository` interface extending `JpaRepository`.
  - [ ] Subtask 2.2.2: Implement `CustomUserDetailsService` to load users by email for auth.

- [ ] **Task 2.3: Configure Security Filter Chain**
  - [ ] Subtask 2.3.1: Create `SecurityConfig` class.
  - [ ] Subtask 2.3.2: Configure `PasswordEncoder` bean (e.g., BCrypt).
  - [ ] Subtask 2.3.3: Set up authorization boundaries (e.g., `/student/**` for students, etc.).
  - [ ] Subtask 2.3.4: Configure the login page and success handlers natively in Spring Security.

- [ ] **Task 2.4: Auth REST APIs / Controllers**
  - [ ] Subtask 2.4.1: Build `AuthController` to handle registration processes visually.
  - [ ] Subtask 2.4.2: Add endpoint for standard view returns (`/login`).

- [ ] **Task 2.5: Design Login Thymeleaf Frontend**
  - [ ] Subtask 2.5.1: Create base layout templates using Bootstrap.
  - [ ] Subtask 2.5.2: Build `login.html` containing Email/Password inputs exactly mapping into Spring Security fields (`username`, `password`).
  - [ ] Subtask 2.5.3: Handle invalid credentials error messaging on UI.

- [ ] **Task 2.6: Frontend/Backend Connect & Test**
  - [ ] Subtask 2.6.1: Seed a dummy admin/student/staff in DB or via ApplicationRunner.
  - [ ] Subtask 2.6.2: Ensure login redirects properly to respective dashboards.

## Phase 3: Core Backend Logic (Complaints & Resources)
**Dependency:** Completion of Task 1.5, Task 2.2. Can overlap with Phase 2's frontend tasks.

- [ ] **Task 3.1: Data Repositories**
  - [ ] Subtask 3.1.1: Build `ComplaintRepository`.
  - [ ] Subtask 3.1.2: Add specific `findByStudentId` or `findByStatus` queries in ComplaintRepository.
  - [ ] Subtask 3.1.3: Build `ResourceRepository`.

- [ ] **Task 3.2: Complaint Services**
  - [ ] Subtask 3.2.1: Write `createComplaint(studentId, dto)` logic.
  - [ ] Subtask 3.2.2: Write `assignComplaintToStaff(complaintId, staffId)` logic.
  - [ ] Subtask 3.2.3: Write `updateComplaintStatus(complaintId, newStatus)` logic.

- [ ] **Task 3.3: Resource Services**
  - [ ] Subtask 3.3.1: Write basic CRUD functions for resources.
  - [ ] Subtask 3.3.2: Write resource request logic tying to users.

- [ ] **Task 3.4: Student APIs (Complaints)**
  - [ ] Subtask 3.4.1: POST `/api/student/complaints` (Create new).
  - [ ] Subtask 3.4.2: GET `/api/student/complaints` (View own).

- [ ] **Task 3.5: Admin APIs (Global)**
  - [ ] Subtask 3.5.1: GET `/api/admin/complaints` (List all).
  - [ ] Subtask 3.5.2: PUT `/api/admin/complaints/{id}/assign` (Assign).
  - [ ] Subtask 3.5.3: GET `/api/admin/resources` (View usage/stock).

- [ ] **Task 3.6: Staff APIs (Tasks)**
  - [ ] Subtask 3.6.1: GET `/api/staff/complaints` (View assigned).
  - [ ] Subtask 3.6.2: PUT `/api/staff/complaints/{id}/status` (Update status).

## Phase 4: Student Dashboard Frontend Integration
**Dependency:** Completion of Phase 2, Tasks 3.2 & 3.4

- [ ] **Task 4.1: Dashboard Scaffolding**
  - [ ] Subtask 4.1.1: Make `student_dashboard.html`.
  - [ ] Subtask 4.1.2: Integrate persistent Navbar (Logout button, User Details).
  - [ ] Subtask 4.1.3: Setup Bootstrap layout sizing.

- [ ] **Task 4.2: Complaint History UI**
  - [ ] Subtask 4.2.1: Render a dynamic `<table>` taking advantage of `th:each="complaint : ${complaints}"`.
  - [ ] Subtask 4.2.2: Use badges/colors for differing statuses (Resolved=Green, Pending=Yellow).

- [ ] **Task 4.3: Raise Complaint Form**
  - [ ] Subtask 4.3.1: Create complaint entry UI (`Title`, `Description`, `RoomNumber`).
  - [ ] Subtask 4.3.2: Insert `<select>` for required categories defined in PRD.
  - [ ] Subtask 4.3.3: Wire model binding (`th:object="${newComplaint}"`) to the post endpoint.

- [ ] **Task 4.4: Request Resource Form**
  - [ ] Subtask 4.4.1: Implement dropdown/list of available basic resources.
  - [ ] Subtask 4.4.2: Implement quantity inputs and submit action.

## Phase 5: Admin Dashboard Frontend Integration
**Dependency:** Completion of Phase 2, Tasks 3.2, 3.3, & 3.5. Can overlap with Phase 4.

- [ ] **Task 5.1: Admin Dashboard Scaffolding**
  - [ ] Subtask 5.1.1: Build `admin_dashboard.html` wrapper.

- [ ] **Task 5.2: Global View Tables**
  - [ ] Subtask 5.2.1: Populate global Complaints list (`th:each`).
  - [ ] Subtask 5.2.2: Identify filters to view specifically Unassigned vs Assigned vs Resolved.

- [ ] **Task 5.3: Assignment Interface**
  - [ ] Subtask 5.3.1: For any unassigned complaint row, include an "Assign Staff" dropdown inside the row or a modal.
  - [ ] Subtask 5.3.2: Create a Form to POST updated assignment state linking Staff ID and Complaint ID.

- [ ] **Task 5.4: Basic Resource Approvals**
  - [ ] Subtask 5.4.1: Display pending resource requests.
  - [ ] Subtask 5.4.2: Approve / Deny toggles UI.

## Phase 6: Staff Dashboard Frontend Integration
**Dependency:** Completion of Phase 2, Tasks 3.2 & 3.6. Can overlap with Phase 4 and 5.

- [ ] **Task 6.1: Staff Dashboard Scaffolding**
  - [ ] Subtask 6.1.1: Build `staff_dashboard.html` wrapper.

- [ ] **Task 6.2: Assigned Task Lists**
  - [ ] Subtask 6.2.1: Bind active tasks query explicitly to the staff's ID context.

- [ ] **Task 6.3: Progress Toggles**
  - [ ] Subtask 6.3.1: On a specific Complaint row, add a Select box to change state (Action: Pending -> In Progress -> Resolved).
  - [ ] Subtask 6.3.2: Provide POST trigger to update state backend natively.

## Phase 7: Optimization & Final Verifications
**Dependency:** All above phases

- [ ] **Task 7.1: E2E Tests**
  - [ ] Subtask 7.1.1: Create a student and raise a complaint.
  - [ ] Subtask 7.1.2: Login as Admin and assign the complaint to a staff member.
  - [ ] Subtask 7.1.3: Login as Staff to advance it to Resolved.
  - [ ] Subtask 7.1.4: Confirm visually everywhere on Dashboards.

- [ ] **Task 7.2: UX Polishing**
  - [ ] Subtask 7.2.1: Make the site Responsive (mobile layout using Bootstrap container-fluid).
  - [ ] Subtask 7.2.2: Add generic modals or toast notifications for "Action Successful".

- [ ] **Task 7.3: JUnit Tests**
  - [ ] Subtask 7.3.1: Write Service tests testing behavior of `assignComplaintToStaff`.
  - [ ] Subtask 7.3.2: Verify exception throws appropriately if invalid values are passed.

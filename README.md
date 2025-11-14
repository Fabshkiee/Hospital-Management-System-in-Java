# Hospital-Management-System-in-Java
**Hospital Management System (Java OOP)**
This is a console-based application for managing patient records and appointments, written in Java.

🏥 This project is a complete, object-oriented refactor of an original first-year procedural C++ project. The primary goal was to re-architect the application from 
the ground up to correctly implement OOP principles, improve code quality, and enhance user-facing features.

**Key Features:**
- **Full Patient Management (CRUD):** Create, Read, Update, and Delete patient records.
_Not fully implemented yet_
- **Automatic MRN Generation:** Automatically creates a unique 6-digit MRN for new patients upon creation.
_Not implemented yet_
- **Dynamic Patient Search:**
_Not implemented yet_
  - **Search by Name:** A user-friendly, case-insensitive search that returns a list of all matching patients.
		_Not implemented yet_
  - **Search by MRN:** A direct, fast lookup for users who know the patient's MRN.
		_Not implemented yet_
- **Appointment Scheduling:** Schedule new appointments for patients with specific doctors and concerns.
_Not implemented yet_
- **Persistent Storage:** All patient and appointment data is saved to and loaded from local .txt files, managed in a dedicated patients/ directory.
_Not implemented yet_
- **Separation of Concerns:** The project is split into (int NULL) distinct classes, separating data (e.g., Patient), (so much more in future implementation if i figure it out)
_Not implemented yet_

🔁 **Refactoring from C++ to Java**
The switch from C++ to Java involved a complete architectural overhaul.

**Major Changes (Architectural)**
- **Procedural vs. Object-Oriented**: The C++ version was a single-file procedural program using global functions and structs. The Java version is a multi-file application built around classes, methods, and OOP principles.

- **Encapsulation:** Replaced C++ structs with Java classes, making all data fields (patientID, name, etc.) private and only allowing access via public getters and setters.

- **Inheritance & Abstraction:** An abstract class Person was created to store common data (name, DOB, sex). Both Patient and Doctor now extend this class, reducing code duplication.

**Minor Changes (Feature & Refinement)**
- **Search Workflow:** The C++ version required users to know a patient's MRN for all actions. The Java version defaults to a user-friendly "search by name" workflow for all operations (Update, Delete, Schedule). 
_Not implemented yet_
- **MRN Generation:** The C++ version required the user to manually input an MRN. The Java version now auto-generates a unique 6-digit MRN for new patients.
_Not implemented yet_
- **Robust Input:** The Java version uses try-catch blocks and validation loops to handle bad user input (like typing letters into a number menu) without crashing.
_Not implemented yet_

Stock Management System This project is a comprehensive stock management system developed as part of the Project Lab 3 course. It is built using Java with JavaFX for the front end and ASP.NET for the web interface. The system employs SQL Server for the database backend and utilizes APIs provided by the instructors for additional functionality.

Features:

Authentication System: Users can log in with three different roles: administrator, operator, and supplier. Each role has varying levels of access to system functions.
Supplier Integration: Suppliers can upload XML files in a specific XSD format. The program parses these files to add the products to the system.
Order Approval Workflow: After a supplier submits an order, operators or administrators can approve it. Approved orders automatically update the stock inventory.
CRUD Operations: The system supports CRUD operations for managing suppliers, clients, and operators.
Debt Tracking: After an order is approved, a debtor balance is created. Administrators and operators can view these balances, and payments can be made via direct debit or bank transfer. A SEPA file in XML format is generated and saved on the computer.
API Integration: Optional integration with APIs provided by the instructors for extended functionality.
Client Purchases: Clients registered through the front-end ASP.NET interface, approved by operators, can purchase products directly from the web interface.
Technology Stack: Backend: Java, SQL Server Frontend: JavaFX, ASP.NET

Additional Libraries: External libraries used are detailed in the project's UAT. The project includes unit tests and Javadocs for better understanding.

Development Process: The project was developed using the Agile Scrum methodology throughout the semester. Weekly sprints were conducted, and documentation was maintained through sprint reviews and retrospectives.

Team Members: David Fernandes Tiago Marquez Pedro Valente
Instructors: Roberto Silva Pedro Lucas
Course Details: Discipline: Project Lab 3 Course: Agile Software Development / ISEP - Instituto Superior de Engenharia do Porto For more information, please refer to the project's documentation and codebase.

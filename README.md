# Vehicle-Rental-System

## Project Description
This project implements a comprehensive Rent-a-Car System using Java for the backend and Bootstrap for the frontend. The system allows customers to browse available vehicles, make reservations, manage their rentals, and process payments. The application follows object-oriented principles and implements a clean separation between the backend logic and frontend presentation. As per requirements, the system must implement custom linked lists for data storage and selection sort for sorting operations.

## Core Features
- Vehicle inventory management and availability tracking
- Customer registration and profile management
- Rental reservation creation and processing
- Rental duration and cost calculation
- Payment simulation and receipt generation
- Administrative dashboard for system management
- **Custom linked list implementation** for data structure management
- **Selection sort algorithm** for sorting vehicles, customers, and rentals

## System Architecture
The system follows a layered architecture with clear separation of concerns:
- **Models:** Core domain objects (Vehicle, Customer, Rental, etc.)
- **Services:** Business logic implementation
- **Controllers:** Coordination between frontend and backend
- **Views:** Bootstrap-based user interfaces
- **Data Structures:** Custom linked list implementations for data management
- **Algorithms:** Sorting and searching implementations including selection sort

## Team Structure & Responsibilities

| Team Member | Responsibility Area | Key Components | Primary Features |
|-------------|---------------------|----------------|------------------|
| Shakya | Vehicle Management | Vehicle and VehicleType classes | • CRUD operations for vehicles<br>• Vehicle categorization<br>• Vehicle search functionality<br>• Vehicle availability tracking |
| Yusuf | Customer Management | Customer class and services | • Customer registration and authentication<br>• Profile management<br>• Customer search functionality<br>• Customer data security |
| Karishini | Rental Processing  | Rental class and services |• Rental creation and modification<br>• Duration calculation<br>• Status management<br>• Selection sort for rental listings |
| Ashwin | Payment System | Payment class and RentalAgency controller | • Payment processing<br>• Fee calculation<br>• Receipt generation<br>• Financial reporting |
| Abishek | Agency Management | Agency classes and admin functionality | • System administration<br>• Performance monitoring<br>• User management<br>• Configuration controls |
| Manusha | Review & Feedback System | Review classes and feedback management | • Review submission<br>• Feedback collection<br>• Rating calculations<br>• Review moderation |

## Data Structure Implementation
The project requires the use of custom-implemented linked lists instead of Java's built-in collections:

1. **Vehicle Linked List:** For managing the inventory of available vehicles
2. **Customer Linked List:** For storing and retrieving customer information
3. **Rental Linked List:** For tracking active and historical rentals

Each linked list implementation must include:
- Node class with appropriate data and reference fields
- Insert, delete, and search operations
- Traversal functionality

## Sorting Algorithm Implementation
The system will implement selection sort for various sorting needs:
- Sorting vehicles by price, model, or availability
- Sorting customers by name or rental history
- Sorting rentals by date or duration

Selection sort must be implemented from scratch rather than using built-in Java sorting methods.

## Integration Points
1. Vehicle data flows to rental processing system
2. Customer records connect to rental and payment systems
3. Rental processing interacts with vehicles, customers, and payments
4. Payment system connects to rentals and generates receipts
5. Frontend components communicate with backend services via REST endpoints
6. Linked lists provide data to frontend for display
7. Selection sort organizes data before presentation

## Development Workflow
- GitHub-based collaboration using feature branches
- Regular pull requests and code reviews
- Integration testing before merging to develop branch
- Weekly team meetings to coordinate efforts
- Coding standards compliance checks

## Technologies Used
- Backend: Java
- Frontend: HTML, CSS, JavaScript, Bootstrap
- Version Control: Git/GitHub
- Testing: JUnit
- Documentation: JavaDoc
- Data Structures: Custom linked list implementation
- Algorithms: Selection sort implementation


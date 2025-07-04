# Overview 
Budget Buddy is a comprehensive personal finance management application developed using Java and JavaFX, with a robust backend powered by Oracle Database. Designed with Object-Oriented Design (OOD) principles and the MVC architecture, Budget Buddy helps users track income and expenses, set and monitor financial goals, plan budgets, and gain actionable insights through reports and notifications—all within an intuitive user interface.

## Technology Stack

- **Frontend:** JavaFX for intuitive and responsive UI  
- **Backend:** Java for server-side logic and data processing  
- **Architecture:** Model View Controller (MVC) for modularity and maintenance  
- **Database:** Oracle SQL for data storage and management  
- **Authentication:** bcrypt for secure password hashing and user authentication  
- **Data Access:** JDBC for efficient database connectivity and operations  

# Key Components
## 1. User Authentication System
- Secure login and signup with bcrypt password hashing for data protection
## 2. Database Structure
- Employs multiple tables: Budget, Goal, Transactions, Transaction History, Reports, Notifications, and Users
- Designed for efficient data retrieval and management
## 3. Dashboard and Navigation
- Central dashboard with a left navigation menu for easy access to various features
## 4. Budget and Goal Management
- Allows users to set budgets for different spending categories
- Enables creation and tracking of financial goals
## 5. Transaction Management
- Facilitates adding new transactions with income and expense categorization
- Implements transaction history viewing with filtering options
## 6. Reporting System
- Generates three types of reports - Monthly spending overview, Income vs. expense breakdown by month and top spending categories analysis
## 7. Notification System
- Alerts users when expenses approach or exceed budget limits
- Provides high expense alerts based on predefined thresholds

# Object-Oriented Design Highlights

- **Class Structure:** Core classes include User, Transaction (with Income/Expense subclasses), Goal, Category, Budget, and Report.
- **Polymorphism & Inheritance:** Unified handling of different transaction and goal types.
- **Interfaces & Abstract Classes:** Shared logic for financial entities and trackable elements.
- **Collections:** Efficient data management using lists, sets, and maps for transactions, goals, and categories.
- **MVC Architecture:**
The application follows the Model-View-Controller pattern, ensuring modularity, ease of maintenance, and clear separation of concerns.
  
# Database Integration
The application uses Oracle SQL for persistent data storage. Required tables include:  
- Users  
- Budget  
- Goals  
- Transactions  
- TransactionHistory  
- Reports  
- Notifications

# User Interface Design
JavaFX is utilized to create a clean, intuitive interface that allows users to navigate easily between different sections of the application. The dashboard serves as the central hub, providing quick access to all features and a snapshot of the user's financial status.

# Instructions to run the project
### Step 1: Extract the Project Files
- Download the .zip file from the repository

### Step 2: Set Up the Project in Eclipse
- Open Eclipse IDE
- Import the project: 
    - File > Import > Existing Projects into Workspace
    - Browse to the extracted project folder
    - Click Finish

### Step 3: Add Required JAR Files to the Classpath
- JavaFX SDK
    - Right-click project > Build Path > Configure Build Path 
    - Libraries tab > Add Library > User Library > Next
    - Create new 'JavaFX' user library if needed
    - Add External JARs > Select JavaFX SDK JAR files
    - Apply and Close

- Add JavaFX to Module Path
    - Right-click project > Properties
    - Java Build Path > Modulepath tab
    - Add Library > User Library > Select 'JavaFX' library
    - Finish > Apply and Close

- Adding ojdbc and bcrypt JARs
    - Java Build Path window > Libraries tab
    - Add External JARs
    - Select ojdbc.jar and bcrypt.jar from lib folder
    - Apply and Close

### Step 4: Configure JavaFX in the Build Path
 - Right-click project > Properties
 - Java Build Path > Libraries tab
 - Ensure JavaFX is in Modulepath
 - Apply and Close

### Step 5: Add VM Arguments for JavaFX
 - Right-click Main.java > Run As > Run Configurations
- Select project under Java Application
- Arguments tab
- VM arguments: `--add-modules javafx.controls,javafx.fxml`
- Apply and Run

### Step 6: Running the Project
 - Locate Main.java
 - Right-click > Run As > Java Application
   
# Benefits
Budget Buddy demonstrates strong OOD principles and delivers a practical, extensible solution for personal finance management. Its modular design supports future enhancements, such as bank API integration or advanced analytics, making it well-suited for real-world application.

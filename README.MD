🚀 Key Features
1)JSON-Driven Logic: Uses Google Gson to parse travel regulations dynamically.

2)Automated Evaluation: Instantly determines if a visa is required and identifies the specific visa type.

3)Document Checklist: Provides travelers with a list of required documents (e.g., Passport, Photos).

4)afety Warnings: Displays specific travel alerts and processing time estimates

📂 Project Structure
Based on the current architecture:

dto/: Data Transfer Objects representing VisaRule and VisaDecision.

enums/: Type-safe constants for Country, VisaType, and TravelPurpose.

evaluator/: The core engine (VisaRuleEvaluator) that matches user input against rules.

loader/: Logic for reading and parsing the JSON data files.

repository/: In-memory storage for the loaded visa rules.

resources/: Contains the rules.json data file.

lib/: External libraries (contains gson-2.10.1.jar).

Installation & Execution
Prerequisites
Java Development Kit (JDK) installed.

Gson library (gson-2.10.1.jar) placed in the lib folder.

1. Compilation
Use the following command to compile all packages and the main entry point:
javac -cp ".:./lib/gson-2.10.1.jar" -d . dto/*.java enums/*.java repository/*.java loader/*.java evaluator/*.java Main.java

2. Running the Application
Run the compiled bytecode while including the resources and library in the classpath:
java -cp ".:./lib/gson-2.10.1.jar:./resources" Main

Example Usage & Output
When you run the system and provide travel details, it outputs a clear decision:
✅ === Visa Decision ===
Visa Required:      YES
Visa Type:          TOURIST_VISA
Documents Required: [PASSPORT, PHOTO]
Processing Days:    5
Warnings:           [Apply at least 1 week in advance]

Technical Implementation Details
Object-Relational Mapping: Maps JSON keys directly to Java fields using the Repository pattern.

Validation: Built-in loops to ensure user input matches the expected Enum values.

Scalability: New countries or rules can be added simply by updating the rules.json file without changing the Java source code.

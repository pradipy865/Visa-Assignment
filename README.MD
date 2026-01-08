# 🛂 Visa Requirement Evaluator

A robust Java-based decision engine designed to evaluate international travel eligibility. This system dynamically parses visa regulations from a JSON database and provides a detailed decision based on the traveler's destination, nationality, and purpose of stay.

## 🚀 Key Features

* **JSON-Driven Logic**: Utilizes Google Gson to deserialize and parse travel regulations dynamically.
* **Automated Evaluation**: Instantly determines if a visa is required and identifies the specific visa type.
* **Document Checklist**: Provides travelers with a comprehensive list of required documentation.
* **Safety Warnings**: Displays specific travel alerts, processing time estimates, and stay duration limits.

---

## 📂 Project Structure

The project is organized into modular packages to ensure separation of concerns:

| Directory | Description |
| :--- | :--- |
| **`dto/`** | Data Transfer Objects for `VisaRule` and `VisaDecision`. |
| **`enums/`** | Type-safe constants for `Country`, `VisaType`, and `TravelPurpose`. |
| **`evaluator/`** | The core engine (`VisaRuleEvaluator`) that matches user input against rules. |
| **`loader/`** | Logic for reading and parsing JSON configuration files. |
| **`repository/`** | In-memory storage for the collection of loaded visa rules. |
| **`resources/`** | Contains the `rules.json` data file. |
| **`lib/`** | External libraries, specifically `gson-2.10.1.jar`. |

---

## 🛠 Installation & Execution

### Prerequisites
* **Java Development Kit (JDK)** 17+ installed.
* **Gson library** (`gson-2.10.1.jar`) placed in the `lib` folder.

### 1. Compilation
Run this command from the project root to compile all packages:
```bash
javac -cp ".:./lib/gson-2.10.1.jar" -d . dto/*.java enums/*.java repository/*.java loader/*.java evaluator/*.java Main.java

###2. Running the Application
java -cp ".:./lib/gson-2.10.1.jar:./resources" Main

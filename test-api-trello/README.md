# 🚀 Trello API Automation Testing Project

This project demonstrates a comprehensive automated API testing suite for **Trello**, covering the core CRUD (Create, Read, Update, Delete) operations using **Postman** and **JavaScript**.

## 📌 Project Overview

The goal of this project was to validate Trello's REST API endpoints by automating the creation of boards, lists, and cards, performing advanced updates, and ensuring clean data deletion.

## 🛠️ Tools Used

- **Postman:** For API request construction and collection management.
- **JavaScript (Postman Scripts):** For automating variable handling and response validation.
- **Trello REST API:** The system under test.
- **Excel:** For detailed test case documentation and reporting.

## 📝 Test Scenarios Covered

The following scenarios were automated and documented in the test report:

| ID         | Test Case    | Method   | Description                                                           |
| :--------- | :----------- | :------- | :-------------------------------------------------------------------- |
| **TC-001** | Create Board | `POST`   | Creates a new Trello board and saves `boardId` automatically.         |
| **TC-002** | Create List  | `POST`   | Creates a "Testing Tasks" list inside the generated board.            |
| **TC-003** | Create Card  | `POST`   | Adds a new card with a description to the list.                       |
| **TC-004** | Update Card  | `PUT`    | Advanced update of card name, description, due date, and cover color. |
| **TC-005** | Delete Card  | `DELETE` | Removes the card and verifies successful deletion (200 OK).           |

## ⚙️ Setup & Installation

1.  **Clone the Collection:** Import the `Trello_Testing.postman_collection.json` into your Postman.
2.  **Environment Variables:** Create a new Environment in Postman and add:
    - `apiKey`: Your Trello API Key.
    - `apiToken`: Your Trello Personal Token.
    - `baseUrl`: `https://api.trello.com/1`
3.  **Run:** Click on the Collection and use the **Runner** to execute all tests at once.

## 📊 Automation Features

- **Dynamic Variables:** The scripts automatically extract IDs from responses and pass them to subsequent requests (e.g., `boardId` -> `listId` -> `cardId`).
- **Assertions:** Each request includes tests to verify Status Codes and JSON Body integrity.
- **Console Logging:** Detailed logs are printed to the Postman Console for debugging.

## 📁 Project Structure

- `Trello_Testing.postman_collection.json`: The full API suite.
- `Test_Report.xlsx`: The detailed manual/automation test execution log.
- `README.md`: Project documentation.

---

## Author
- Ali El-Shoraa

## Contributors
- Nourhan
  
**Date:** March 2026

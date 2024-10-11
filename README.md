## Problem: Expense Tracker

Build a simple expense tracker application to manage your finances. The application should allow users to add, delete, and view their expenses. The application should also provide a summary of the expenses.

## Requirements

Application should run from the command line and should have the following features:

- Users can add an expense with a description, amount and type (optional).
- Users can update an expense.
- Users can delete an expense.
- Users can view all expenses.
- Users can view a summary of all expenses.

Here are some additional features that you can add to the application:

- Add expense categories and allow users to filter expenses by category.
- Allow users to export expenses to a CSV file.

The list of commands and their expected output is shown below:

  ```bash
    $ expense-tracker add --description "Lunch" --amount 20
    # Expense added successfully (ID: 1)
    
    $ expense-tracker add --description "Taco" --amount 10 --type food
    # Expense added successfully (ID: 2)
    
    $ expense-tracker list
    # ID  Description  Amount Type
    # 1   Lunch        $20    other
    # 2   Taco         $10    food
    
    $ expense-tracker update --id 1 --description "Expensive Launch"
    #"Expense with ID: 1 updated successfully";
    
    $ expense-tracker delete --id 1
    # Expense deleted successfully
    
    $ expense-tracker total
    # Total expenses: $20
    
    $ expense-tracker find-by-type --type food
    # ID  Description  Amount Type
    # 2   Taco         $10    food
    # 3   Fish         $15    food
    
    $ expense-tracker export-csv
    # CSV file generated successfully
  ```

## Implementation

You can implement the application using any programming language of your choice. Here are some suggestions:

- Use any programming language for any available module for parsing command arguments (e.g. python with the argparse, node.js with commander etc).
- Use a simple text file to store the expenses data. You can use JSON, CSV, or any other format to store the data.
- Add error handling to handle invalid inputs and edge cases (e.g. negative amounts, non-existent expense IDs, etc).
- Use functions to modularize the code and make it easier to test and maintain.

This project idea is a great way to practice your logic building skills and learn how to interact with the filesystem using a CLI application. It will also help you understand how to manage data and provide useful information to users in a structured way.
the instruction was created by [roadmap.sh](https://roadmap.sh/projects/expense-tracker)

# Solution
## Language
The application is implemented in **Java**.

## Framework
The application uses the **Spring Framework** for core functionalities.

## Technologies
The project incorporates the following technologies:

- **Spring Boot**: Provides easy configuration and simplifies dependency management for rapid development.
- **Spring Data JPA**: Handles database interactions with ease through an ORM approach, allowing seamless integration with the database.
- **Lombok**: Reduces boilerplate code by generating commonly used code, such as getters, setters, and constructors.
- **Flyway**: Manages database migrations, enabling easy version control and updates to the database schema.
- **Hibernate**: Provides an abstraction layer for database operations, helping with object-relational mapping (ORM).
- **Spring Shell**: Allows a command-line interface (CLI) to be implemented directly within the application, providing an interactive experience for users.
- **OpenCSV**: Handles CSV file export, enabling users to download and share expense data.

## Database
The application uses **PostgreSQL** as the database to store expenses and any related information. The integration with PostgreSQL is managed through Spring Data JPA, which simplifies database operations.

## Additional Project Features

This project is designed to be **modular, scalable**, and easily maintainable. It validates various user inputs, ensuring that all data entries meet specific criteria and handle cases such as invalid IDs, missing fields, or unsupported commands.

The modular structure of this project means it is easy to extend with additional functionalities, such as:
- Additional filtering options for expenses.
- Integration with other data storage formats.
- Enhanced report generation for expense analysis.

Each part of the application is broken down into smaller, reusable components, allowing for easy testing, maintenance, and scalability.

---
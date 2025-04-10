
# Kawaii-Bank

Kawaii-Bank is a Java program that simulates a basic digital banking system. It lets users (bank tellers) create and manage customer accounts through a simple graphical interface.

## Features

- Add, edit, and remove accounts
- Deposit and withdraw money
- Account types: Everyday, Savings, and Current
- Enforces banking rules:
  - No overdraft on Everyday or Savings
  - $1000 overdraft allowed for Current accounts
  - $5000 max withdrawal per transaction
- Data is saved to and loaded from a CSV file

## How to Run

1. Clone the repo:
   ```bash
   git clone https://github.com/laingco/Kawaii-Bank.git
   ```

2. Open in BlueJ or another Java IDE

3. Run `KawaiiBank.java` to start the app

Make sure `bankData.csv` is in the same folder as the `.java` files.

## Files

- `Account.java` handles account data
- `EditCSV.java` reads/writes CSV files
- `GUI.java` builds the interface
- `KawaiiBank.java` main class to start the app
- `bankData.csv` stores customer data

## Testing

Testing was done for:
- Valid and invalid account input
- Deposits and withdrawals (including limits)
- GUI navigation
- File saving/loading

See the test plan for full details.

## Author

Made by **Cooper Laing**
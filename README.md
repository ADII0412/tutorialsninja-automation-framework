# 🧪 TutorialsNinja Automation Framework

A robust end-to-end automation framework built using Selenium WebDriver, Java, and TestNG for the TutorialsNinja demo e-commerce application. This project demonstrates real-world QA automation practices including scalable framework design, reusable components, and comprehensive test coverage across critical user flows.

---

## 🚀 Project Overview

This framework is designed using the Page Object Model (POM) to ensure maintainability, readability, and reusability of test code. It automates key e-commerce functionalities such as user registration, login, product search, wishlist management, cart operations, product comparison, and checkout flow.

The framework also integrates Extent Reports for detailed execution reporting and is structured to support CI/CD integration and containerized execution.

---

## 🧱 Framework Architecture

* Page Object Model (POM) design pattern
* Separate layers for Test Cases, Page Objects, Utilities, and Base setup
* Centralized WebDriver initialization using DriverFactory
* Reusable methods for common actions and wait handling
* Clean and scalable folder structure aligned with industry standards

---

## 🔄 Automated Test Coverage

The framework includes automation for the following modules:

🔹 Registration Module (Mandatory validation, duplicate email handling, privacy policy validation)
🔹 Login Module (Valid/invalid login, password validation, forgot password navigation)
🔹 Search Module (Keyword search, filters, partial match, product description search)
🔹 Product Display Module (Product details, image interactions, reviews, related products)
🔹 Wishlist Module (Add/remove products, validation)
🔹 Product Comparison Module (Add/remove products, multiple product comparison)
🔹 Cart Module (Add/update/remove products, cart total validation, persistence)
🔹 Checkout Module (End-to-end flow for guest and registered users, order validation)

---

## ⚡ Advanced Capabilities

* CI/CD integration using Jenkins for automated test execution
* Docker-based execution for consistent cross-environment testing
* Parallel test execution for faster test runs
* Cross-browser testing support
* Selenium Grid / Remote WebDriver execution support

---

## 🛠️ Tech Stack

Java
Selenium WebDriver
TestNG
Maven
Extent Reports
Git & GitHub
Jenkins (CI/CD)
Docker
Selenium Grid

---

## 📊 Reporting

* Integrated Extent Reports for detailed HTML test execution reports
* Captures test steps, status (Pass/Fail), and screenshots for failures

---

## ⚙️ How to Run the Project

1. Clone the repository:

   ```bash
   git clone https://github.com/ADII0412/tutorialsninja-automation-framework.git
   ```

2. Navigate to project directory:

   ```bash
   cd tutorials-ninja-automation-framework
   ```

3. Install dependencies:

   ```bash
   mvn clean install
   ```

4. Run tests:

   ```bash
   mvn test
   ```

---

## 🔮 Future Enhancements

* CI/CD integration using Jenkins
* Docker-based execution for cross-environment testing
* Parallel test execution
* Cross-browser testing support
* Integration with cloud platforms (Selenium Grid / remote execution)

---

## 🎯 Key Highlights

* Designed as a production-style automation framework
* Covers real-world end-to-end e-commerce scenarios
* Emphasizes code reusability and maintainability
* Demonstrates strong understanding of automation architecture and QA practices

---

## 💼 About This Project

This project is developed as part of hands-on practice to simulate real-time QA automation work and showcase practical expertise in Selenium-based framework design, test strategy, and implementation.

---

## 📌 Author

Aditya Singh | 
QA Automation Engineer

---

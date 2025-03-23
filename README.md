# Payroll Management System

A full-featured Payroll Management System built with a Spring Boot backend and a React frontend. This application enables HR and finance teams to manage payroll configurations, process payrolls, track leave applications, generate payslips, and search for employees with full details—all while adhering to modern design principles.

## Features

- **Dynamic Dashboard:** View live payroll summaries including total employees, total payroll, and net salary sums.
- **Employee Management:** CRUD operations for employee records with full details such as name, phone number, email, gender, department, and basic salary.
- **Payroll Processing:** Calculate payroll dynamically based on configurable tax bands, NHIF/NSSF settings, allowances, and deduction rules.
- **Leave Management:** Apply, approve, and reject leave requests with full tracking of leave dates and statuses.
- **Payslip Generation:** Access and download payslips for individual payroll periods.
- **Search Functionality:** Search employees by name, phone, email, or department.

## Tech Stack

- **Backend:** Spring Boot, Spring Data JPA, Hibernate, PostgreSQL
- **Frontend:** React, React Router, CSS Flexbox/Grid
- **Build Tools:** Maven (for backend), Create React App (for frontend)
- **Other:** CORS configuration, RESTful APIs, modern Java (with records for DTOs)

## Design Principles

- **Simplicity & Clarity:** Clean, minimal layout with clear typography and ample whitespace.
- **Consistency:** Uniform design language across all pages using reusable components.
- **Responsiveness:** Mobile-first design ensuring a seamless experience on desktops, tablets, and smartphones.
- **Accessibility:** Semantic HTML, ARIA attributes, sufficient contrast, and keyboard navigation.
- **User-Centered:** Intuitive navigation, immediate feedback on actions, and clear error messages.

## Getting Started

### Prerequisites

- **Java 11+**
- **Maven**
- **Node.js & npm**
- **PostgreSQL**

### Backend Setup

1. **Clone the Repository:**

   ```bash
   git clone https://github.com/your-username/payroll-management-system.git
   cd payroll-management-system/backend

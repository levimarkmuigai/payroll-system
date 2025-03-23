// src/components/Header.js
import React from 'react';
import './Header.css'; // Optional: Create a separate CSS file for Header

function Header() {
  return (
    <header className="header" role="banner">
      <h1>Payroll Management System</h1>
      <p>Manage your payroll, configurations, leave, and payslips all in one place.</p>
    </header>
  );
}

export default Header;

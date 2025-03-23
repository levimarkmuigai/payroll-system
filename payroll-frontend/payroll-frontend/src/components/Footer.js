// src/components/Footer.js
import React from 'react';
import './Footer.css'; // Optional: Create a separate CSS file for Footer

function Footer() {
  return (
    <footer className="footer" role="contentinfo">
      <p>&copy; {new Date().getFullYear()} Payroll Management System. All rights reserved.</p>
    </footer>
  );
}

export default Footer;

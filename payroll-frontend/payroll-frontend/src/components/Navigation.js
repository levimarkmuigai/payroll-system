// src/components/Navigation.js
import React from 'react';
import { NavLink } from 'react-router-dom';
import './Navigation.css'; // Optional: Create a separate CSS file for Navigation

function Navigation() {
  return (
    <nav className="navigation" role="navigation" aria-label="Main navigation">
      <ul>
        <li>
          <NavLink to="/" end activeclassname="active">
            Dashboard
          </NavLink>
        </li>
        <li>
          <NavLink to="/employees" activeclassname="active">
            Employee Search
          </NavLink>
        </li>  
        <li>
          <NavLink to="/configurations" activeclassname="active">
            Manage Configurations
          </NavLink>
        </li>
        <li>
          <NavLink to="/reports" activeclassname="active">
            Payroll Reports
          </NavLink>
        </li>
        <li>
          <NavLink to="/leave" activeclassname="active">
            Leave Applications
          </NavLink>
        </li>
        <li>
          <NavLink to="/payslips" activeclassname="active">
            Payslips
          </NavLink>
        </li>
      </ul>
    </nav>
  );
}

export default Navigation;

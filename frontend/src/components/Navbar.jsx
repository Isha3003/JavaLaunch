function Navbar() {
  return (
    <nav className="navbar">
      <div className="navbar-brand">
        <span className="logo-icon">🚀</span>

        <div>
          <h2>JavaLaunch</h2>
          <span>Deployment Management Platform</span>
        </div>
      </div>

      <div className="navbar-status">
        <span className="status-dot"></span>
        Backend Connected
      </div>
    </nav>
  )
}

export default Navbar
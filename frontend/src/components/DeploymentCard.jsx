function DeploymentCard({
  deployment,
  onStop,
  onRestart,
  onDelete,
  onLogs
}) {

  const isRunning = deployment.status === 'RUNNING'

  const openApplication = () => {
    window.open(
      `http://localhost:${deployment.port}`,
      '_blank'
    )
  }

  return (
    <div className="deployment-card">

      <div className="deployment-header">

        <div>
          <h3>{deployment.projectName}</h3>

          <a
            href={deployment.githubUrl}
            target="_blank"
            rel="noreferrer"
          >
            GitHub Repository ↗
          </a>
        </div>

        <span
          className={
            isRunning
              ? 'status-badge running'
              : 'status-badge stopped'
          }
        >
          <span className="status-dot"></span>
          {deployment.status}
        </span>

      </div>

      <div className="deployment-info">

        <div>
          <span>Port</span>
          <strong>{deployment.port}</strong>
        </div>

        <div>
          <span>Process ID</span>
          <strong>
            {deployment.processId ?? '—'}
          </strong>
        </div>

        <div>
          <span>Deployment ID</span>
          <strong>#{deployment.id}</strong>
        </div>

      </div>

      <div className="deployment-actions">

        {isRunning && (
          <button
            className="btn primary"
            onClick={openApplication}
          >
            🌐 Open
          </button>
        )}

        <button
          className="btn secondary"
          onClick={() => onLogs(deployment.id)}
        >
          📋 Logs
        </button>

        {isRunning ? (
          <button
            className="btn warning"
            onClick={() => onStop(deployment.id)}
          >
            ⏹ Stop
          </button>
        ) : (
          <button
            className="btn success"
            onClick={() => onRestart(deployment.id)}
          >
            🔄 Restart
          </button>
        )}

        {isRunning && (
          <button
            className="btn secondary"
            onClick={() => onRestart(deployment.id)}
          >
            🔄 Restart
          </button>
        )}

        <button
          className="btn danger"
          onClick={() => onDelete(deployment.id)}
        >
          🗑 Delete
        </button>

      </div>

    </div>
  )
}

export default DeploymentCard
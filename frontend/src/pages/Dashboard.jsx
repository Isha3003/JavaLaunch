import { useEffect, useState } from 'react'

import StatsCard from '../components/StatsCard'
import DeployForm from '../components/DeployForm'
import DeploymentCard from '../components/DeploymentCard'

import {
  getDeployments,
  deployProject,
  stopDeployment,
  restartDeployment,
  deleteDeployment,
  getDeploymentLogs
} from '../services/api'

function Dashboard() {

  const [deployments, setDeployments] = useState([])
  const [loading, setLoading] = useState(true)
  const [logs, setLogs] = useState('')
  const [showLogs, setShowLogs] = useState(false)

  const loadDeployments = async () => {

    try {

      setLoading(true)

      const data = await getDeployments()

      setDeployments(data)

    } catch (error) {

      console.error(error)

    } finally {

      setLoading(false)

    }
  }

  useEffect(() => {

    loadDeployments()

    const interval = setInterval(
      loadDeployments,
      5000
    )

    return () => clearInterval(interval)

  }, [])

  const handleDeploy = async (project) => {

    const response = await deployProject(project)

    alert(response.message)

    await loadDeployments()
  }

  const handleStop = async (id) => {

    try {

      const response = await stopDeployment(id)

      alert(response.message)

      await loadDeployments()

    } catch (error) {

      alert(error.message)

    }
  }

  const handleRestart = async (id) => {

    try {

      const response = await restartDeployment(id)

      alert(response.message)

      await loadDeployments()

    } catch (error) {

      alert(error.message)

    }
  }

  const handleDelete = async (id) => {

    const confirmed = window.confirm(
      'Are you sure you want to delete this deployment?'
    )

    if (!confirmed) {
      return
    }

    try {

      const response = await deleteDeployment(id)

      alert(response.message)

      await loadDeployments()

    } catch (error) {

      alert(error.message)

    }
  }

  const handleLogs = async (id) => {

    try {

      const response = await getDeploymentLogs(id)

      setLogs(response.data)

      setShowLogs(true)

    } catch (error) {

      alert(error.message)

    }
  }

  const totalDeployments = deployments.length

  const runningDeployments =
    deployments.filter(
      deployment => deployment.status === 'RUNNING'
    ).length

  const stoppedDeployments =
    deployments.filter(
      deployment => deployment.status === 'STOPPED'
    ).length

  return (
    <main className="dashboard">

      <section className="hero-section">

        <div>
          <p className="eyebrow">
            DEPLOYMENT CONTROL CENTER
          </p>

          <h1>
            Deploy. Run. Manage.
          </h1>

          <p className="hero-description">
            Deploy your Spring Boot applications
            directly from GitHub.
          </p>
        </div>

      </section>

      <section className="stats-grid">

        <StatsCard
          title="Total Deployments"
          value={totalDeployments}
          icon="📦"
        />

        <StatsCard
          title="Running"
          value={runningDeployments}
          icon="🟢"
        />

        <StatsCard
          title="Stopped"
          value={stoppedDeployments}
          icon="🔴"
        />

      </section>

      <DeployForm
        onDeploy={handleDeploy}
      />

      <section className="deployments-section">

        <div className="section-heading">

          <div>
            <h2>Your Deployments</h2>

            <p>
              Manage all your deployed applications.
            </p>
          </div>

          <button
            className="refresh-button"
            onClick={loadDeployments}
          >
            ↻ Refresh
          </button>

        </div>

        {loading ? (

          <div className="empty-state">
            Loading deployments...
          </div>

        ) : deployments.length === 0 ? (

          <div className="empty-state">
            <div className="empty-icon">🚀</div>

            <h3>No deployments yet</h3>

            <p>
              Deploy your first application using
              the form above.
            </p>
          </div>

        ) : (

          <div className="deployment-list">

            {deployments.map(deployment => (

              <DeploymentCard
                key={deployment.id}
                deployment={deployment}
                onStop={handleStop}
                onRestart={handleRestart}
                onDelete={handleDelete}
                onLogs={handleLogs}
              />

            ))}

          </div>

        )}

      </section>

      {showLogs && (

        <div
          className="modal-overlay"
          onClick={() => setShowLogs(false)}
        >

          <div
            className="logs-modal"
            onClick={(event) =>
              event.stopPropagation()
            }
          >

            <div className="logs-header">

              <div>
                <h2>Application Logs</h2>
                <p>Latest deployment output</p>
              </div>

              <button
                onClick={() => setShowLogs(false)}
                className="close-button"
              >
                ✕
              </button>

            </div>

            <pre className="logs-content">
              {logs || 'No logs available.'}
            </pre>

          </div>

        </div>

      )}

    </main>
  )
}

export default Dashboard
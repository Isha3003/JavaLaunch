import { useState } from 'react'

function DeployForm({ onDeploy }) {
  const [projectName, setProjectName] = useState('')
  const [githubUrl, setGithubUrl] = useState('')
  const [loading, setLoading] = useState(false)

  const handleSubmit = async (event) => {
    event.preventDefault()

    if (!projectName || !githubUrl) {
      alert('Please enter project name and GitHub URL.')
      return
    }

    try {
      setLoading(true)

      await onDeploy({
        projectName,
        githubUrl
      })

      setProjectName('')
      setGithubUrl('')
    } catch (error) {
      alert(error.message)
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="deploy-section">
      <div className="section-heading">
        <div>
          <h2>Deploy New Application</h2>
          <p>Deploy a Spring Boot project directly from GitHub.</p>
        </div>
      </div>

      <form onSubmit={handleSubmit} className="deploy-form">

        <div className="form-group">
          <label>Project Name</label>

          <input
            type="text"
            placeholder="e.g. PetClinic"
            value={projectName}
            onChange={(event) =>
              setProjectName(event.target.value)
            }
          />
        </div>

        <div className="form-group">
          <label>GitHub Repository URL</label>

          <input
            type="text"
            placeholder="https://github.com/user/project.git"
            value={githubUrl}
            onChange={(event) =>
              setGithubUrl(event.target.value)
            }
          />
        </div>

        <button
          type="submit"
          className="deploy-button"
          disabled={loading}
        >
          {loading ? 'Deploying...' : '🚀 Deploy Application'}
        </button>

      </form>
    </div>
  )
}

export default DeployForm
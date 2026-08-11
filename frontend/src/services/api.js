const API_BASE_URL = '/api'

async function request(url, options = {}) {

  const response = await fetch(
    `${API_BASE_URL}${url}`,
    {
      headers: {
        'Content-Type': 'application/json',
        ...options.headers
      },
      ...options
    }
  )

  const data = await response.json()

  if (!response.ok || data.success === false) {
    throw new Error(
      data.message || 'Something went wrong'
    )
  }

  return data
}


export async function getDeployments() {

  const response =
    await request('/deployments')

  return response.data
}


export async function deployProject(project) {

  return await request(
    '/deploy',
    {
      method: 'POST',
      body: JSON.stringify(project)
    }
  )
}


export async function stopDeployment(id) {

  return await request(
    `/deployments/${id}/stop`,
    {
      method: 'POST'
    }
  )
}


export async function restartDeployment(id) {

  return await request(
    `/deployments/${id}/restart`,
    {
      method: 'POST'
    }
  )
}


export async function deleteDeployment(id) {

  return await request(
    `/deployments/${id}`,
    {
      method: 'DELETE'
    }
  )
}


export async function getDeploymentLogs(id) {

  return await request(
    `/deployments/${id}/logs`
  )
}
const API_URL = "http://34.202.199.207:8080";

function buildQuery(params) {
  if (!params) return "";
  const esc = encodeURIComponent;
  return (
    "?" +
    Object.keys(params)
      .map(k => esc(k) + "=" + esc(params[k]))
      .join("&")
  );
}

async function request(url, opts = {}) {
  const {
    method = "GET",
    data = null,
    headers = {},
    params = null,
    credentials = "same-origin" // 'include' se precisar enviar cookies
  } = opts;

  const fullUrl = API_URL + url + buildQuery(params);

  const fetchOptions = {
    method,
    headers: {
      ...headers
    },
    credentials
  };

  if (data != null) {
    if (!fetchOptions.headers["Content-Type"]) {
      fetchOptions.headers["Content-Type"] = "application/json";
    }
    // se for FormData, não stringify
    if (data instanceof FormData) {
      delete fetchOptions.headers["Content-Type"];
      fetchOptions.body = data;
    } else {
      fetchOptions.body = JSON.stringify(data);
    }
  }

  let res;
  try {
    res = await fetch(fullUrl, fetchOptions);
  } catch (err) {
    // erro de rede (ex: conexão recusada)
    throw new Error(`Network error ao acessar ${fullUrl}: ${err.message}`);
  }

  const noContent = res.status === 204 || res.status === 205;
  let body = null;

  if (!noContent) {
    const contentType = res.headers.get("content-type") || "";
    if (contentType.includes("application/json")) {
      try {
        body = await res.json();
      } catch (err) {
        // corpo não é JSON válido
        throw new Error(`Resposta inválida JSON de ${fullUrl}: ${err.message}`);
      }
    } else {
      // texto ou outro
      body = await res.text();
    }
  }

  if (!res.ok) {
    const msg = (body && body.message) || body || res.statusText || `HTTP ${res.status}`;
    const err = new Error(`Erro ${res.status} ao acessar ${fullUrl}: ${msg}`);
    err.status = res.status;
    err.body = body;
    throw err;
  }

  return body;
}

/* helpers convenientes */

// GET /resource or GET /resource?params
async function apiGet(path, params = null, opts = {}) {
  return request(path, { method: "GET", params, ...opts });
}

// POST /resource  (data -> JSON body)
async function apiPost(path, data, opts = {}) {
  return request(path, { method: "POST", data, ...opts });
}

// PUT /resource/{id}
async function apiPut(path, data, opts = {}) {
  return request(path, { method: "PUT", data, ...opts });
}

// PATCH /resource/{id}
async function apiPatch(path, data, opts = {}) {
  return request(path, { method: "PATCH", data, ...opts });
}

// DELETE /resource/{id}
async function apiDelete(path, opts = {}) {
  // normalmente não retorna body (204)
  return request(path, { method: "DELETE", ...opts });
}

window.Api = {
  API_URL,
  request,
  get: apiGet,
  post: apiPost,
  put: apiPut,
  patch: apiPatch,
  del: apiDelete,
  buildQuery
};


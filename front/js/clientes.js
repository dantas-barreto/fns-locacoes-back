// clientes.js
async function carregarClientes() {
  const tbody = document.querySelector("#tabela-clientes tbody");
  tbody.innerHTML = "";
  try {
    const clientes = await Api.get("/clientes");
    (clientes || []).forEach(c => {
      const tr = document.createElement("tr");
      tr.innerHTML = `
        <td>${c.id ?? ""}</td>
        <td>${c.nome ?? ""}</td>
        <td>${c.cpf ?? ""}</td>
        <td>${c.telefone ?? ""}</td>
        <td>${c.endereco ?? ""}</td>
        <td>
          <button onclick="editarCliente(${c.id})">Editar</button>
          <button class="btn-danger" onclick="excluirCliente(${c.id})">Excluir</button>
        </td>
      `;
      tbody.appendChild(tr);
    });
  } catch (err) {
    console.error(err);
    alert("Erro ao carregar clientes.");
  }
}

function novoCliente() {
  document.getElementById("cliente-id").value = "";
  document.getElementById("cliente-nome").value = "";
  document.getElementById("cliente-cpf").value = "";
  document.getElementById("cliente-telefone").value = "";
  document.getElementById("cliente-endereco").value = "";
  document.getElementById("titulo-cliente").innerText = "Novo Cliente";
  document.getElementById("modal-cliente").classList.remove("hidden");
}

async function editarCliente(id) {
  try {
    const cliente = await Api.get(`/clientes/${id}`);
    document.getElementById("cliente-id").value = cliente.id;
    document.getElementById("cliente-nome").value = cliente.nome || "";
    document.getElementById("cliente-cpf").value = cliente.cpf || "";
    document.getElementById("cliente-telefone").value = cliente.telefone || "";
    document.getElementById("cliente-endereco").value = cliente.endereco || "";
    document.getElementById("titulo-cliente").innerText = "Editar Cliente";
    document.getElementById("modal-cliente").classList.remove("hidden");
  } catch (err) {
    console.error(err);
    alert("Erro ao carregar cliente.");
  }
}

async function salvarCliente() {
  const id = document.getElementById("cliente-id").value;
  const cliente = {
    nome: document.getElementById("cliente-nome").value,
    cpf: document.getElementById("cliente-cpf").value,
    telefone: document.getElementById("cliente-telefone").value,
    endereco: document.getElementById("cliente-endereco").value
  };

  try {
    if (id) await Api.put(`/clientes/${id}`, cliente);
    else await Api.post("/clientes", cliente);
    fecharModalCliente();
    carregarClientes();
  } catch (err) {
    console.error(err);
    alert("Erro ao salvar cliente.");
  }
}

async function excluirCliente(id) {
  if (!confirm("Confirma exclusão?")) return;
  try {
    await Api.del(`/clientes/${id}`);
    carregarClientes();
  } catch (err) {
    console.error(err);
    alert("Erro ao excluir cliente.");
  }
}

function fecharModalCliente() {
  document.getElementById("modal-cliente").classList.add("hidden");
}

carregarClientes();

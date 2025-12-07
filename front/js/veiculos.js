// veiculos.js
async function carregarVeiculos() {
  const tbody = document.querySelector("#tabela-veiculos tbody");
  tbody.innerHTML = "";
  try {
    const veiculos = await Api.get("/veiculos");
    (veiculos || []).forEach(v => {
      const status = (v.historicoEventos && v.historicoEventos.status) || "";
      const tr = document.createElement("tr");
      tr.innerHTML = `
        <td>${v.id ?? ""}</td>
        <td>${v.marca ?? ""}</td>
        <td>${v.modelo ?? ""}</td>
        <td>${v.ano ?? ""}</td>
        <td>${v.placa ?? ""}</td>
        <td>${v.renavam ?? ""}</td>
        <td>${status}</td>
        <td>
          <button onclick="editarVeiculo(${v.id})">Editar</button>
          <button class="btn-danger" onclick="excluirVeiculo(${v.id})">Excluir</button>
        </td>
      `;
      tbody.appendChild(tr);
    });
  } catch (err) {
    console.error(err);
    alert("Erro ao carregar veículos.");
  }
}

function novoVeiculo() {
  document.getElementById("veiculo-id").value = "";
  document.getElementById("veiculo-marca").value = "";
  document.getElementById("veiculo-modelo").value = "";
  document.getElementById("veiculo-ano").value = "";
  document.getElementById("veiculo-placa").value = "";
  document.getElementById("veiculo-renavam").value = "";
  document.getElementById("veiculo-status").value = "DISPONIVEL";
  document.getElementById("titulo-veiculo").innerText = "Novo Veículo";
  document.getElementById("modal-veiculo").classList.remove("hidden");
}

async function editarVeiculo(id) {
  try {
    const v = await Api.get(`/veiculos/${id}`);
    document.getElementById("veiculo-id").value = v.id;
    document.getElementById("veiculo-marca").value = v.marca || "";
    document.getElementById("veiculo-modelo").value = v.modelo || "";
    document.getElementById("veiculo-ano").value = v.ano || "";
    document.getElementById("veiculo-placa").value = v.placa || "";
    document.getElementById("veiculo-renavam").value = v.renavam || "";
    const status = (v.historicoEventos && v.historicoEventos.status) || "DISPONIVEL";
    document.getElementById("veiculo-status").value = status;
    document.getElementById("titulo-veiculo").innerText = "Editar Veículo";
    document.getElementById("modal-veiculo").classList.remove("hidden");
  } catch (err) {
    console.error(err);
    alert("Erro ao carregar veículo.");
  }
}

async function salvarVeiculo() {
  const id = document.getElementById("veiculo-id").value;
  const veiculo = {
    marca: document.getElementById("veiculo-marca").value,
    modelo: document.getElementById("veiculo-modelo").value,
    ano: Number(document.getElementById("veiculo-ano").value) || null,
    placa: document.getElementById("veiculo-placa").value,
    renavam: document.getElementById("veiculo-renavam").value,
    // enviar historicoEventos como objeto com status (API pode aceitar)
    historicoEventos: {
      status: document.getElementById("veiculo-status").value,
      data: new Date().toISOString(),
      observacao: "Atualização manual via frontend"
    }
  };

  try {
    if (id) await Api.put(`/veiculos/${id}`, veiculo);
    else await Api.post("/veiculos", veiculo);
    fecharModalVeiculo();
    carregarVeiculos();
  } catch (err) {
    console.error(err);
    alert("Erro ao salvar veículo.");
  }
}

async function excluirVeiculo(id) {
  if (!confirm("Confirma exclusão do veículo?")) return;
  try {
    await Api.del(`/veiculos/${id}`);
    carregarVeiculos();
  } catch (err) {
    console.error(err);
    alert("Erro ao excluir veículo.");
  }
}

function fecharModalVeiculo() {
  document.getElementById("modal-veiculo").classList.add("hidden");
}

carregarVeiculos();

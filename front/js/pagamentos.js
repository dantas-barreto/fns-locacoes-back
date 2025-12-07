// pagamentos.js
async function carregarPagamentos() {
  const tbody = document.querySelector("#tabela-pagamentos tbody");
  tbody.innerHTML = "";
  try {
    const pagamentos = await Api.get("/pagamentos");
    (pagamentos || []).forEach(p => {
      const locacaoRef = p.locacao ? `#${p.locacao.id}` : "";
      const data = p.data ? new Date(p.data).toLocaleString() : "";
      const tr = document.createElement("tr");
      tr.innerHTML = `
        <td>${p.id ?? ""}</td>
        <td>${data}</td>
        <td>${p.valor ?? ""}</td>
        <td>${p.metodoPagamento ?? ""}</td>
        <td>${locacaoRef}</td>
        <td>
          <button onclick="editarPagamento(${p.id})">Editar</button>
          <button class="btn-danger" onclick="excluirPagamento(${p.id})">Excluir</button>
        </td>
      `;
      tbody.appendChild(tr);
    });
  } catch (err) {
    console.error(err);
    alert("Erro ao carregar pagamentos.");
  }
}

async function carregarLocacoesNoSelect() {
  try {
    const select = document.getElementById("pagamento-locacao");
    select.innerHTML = "";
    const locacoes = await Api.get("/locacoes");
    (locacoes || []).forEach(l => {
      const opt = document.createElement("option");
      opt.value = l.id;
      opt.text = `#${l.id} - ${l.cliente ? l.cliente.nome : ''} / ${l.veiculo ? l.veiculo.placa : ''}`;
      select.appendChild(opt);
    });
  } catch (err) {
    console.error(err);
  }
}

function novoPagamento() {
  document.getElementById("pagamento-id").value = "";
  document.getElementById("pagamento-data").value = "";
  document.getElementById("pagamento-valor").value = "";
  document.getElementById("pagamento-metodo").value = "PIX";
  carregarLocacoesNoSelect();
  document.getElementById("titulo-pagamento").innerText = "Novo Pagamento";
  document.getElementById("modal-pagamento").classList.remove("hidden");
}

async function editarPagamento(id) {
  try {
    const p = await Api.get(`/pagamentos/${id}`);
    document.getElementById("pagamento-id").value = p.id;
    document.getElementById("pagamento-data").value = p.data ? new Date(p.data).toISOString().slice(0,16) : "";
    document.getElementById("pagamento-valor").value = p.valor ?? "";
    document.getElementById("pagamento-metodo").value = p.metodoPagamento || "PIX";
    await carregarLocacoesNoSelect();
    if (p.locacao) document.getElementById("pagamento-locacao").value = p.locacao.id;
    document.getElementById("titulo-pagamento").innerText = "Editar Pagamento";
    document.getElementById("modal-pagamento").classList.remove("hidden");
  } catch (err) {
    console.error(err);
    alert("Erro ao carregar pagamento.");
  }
}

async function salvarPagamento() {
  const id = document.getElementById("pagamento-id").value;
  const payload = {
    data: document.getElementById("pagamento-data").value ? new Date(document.getElementById("pagamento-data").value).toISOString() : null,
    valor: Number(document.getElementById("pagamento-valor").value) || 0,
    metodoPagamento: document.getElementById("pagamento-metodo").value,
    locacao: { id: Number(document.getElementById("pagamento-locacao").value) }
  };
  try {
    if (id) await Api.put(`/pagamentos/${id}`, payload);
    else await Api.post("/pagamentos", payload);
    fecharModalPagamento();
    carregarPagamentos();
  } catch (err) {
    console.error(err);
    alert("Erro ao salvar pagamento.");
  }
}

async function excluirPagamento(id) {
  if (!confirm("Confirma exclusão do pagamento?")) return;
  try {
    await Api.del(`/pagamentos/${id}`);
    carregarPagamentos();
  } catch (err) {
    console.error(err);
    alert("Erro ao excluir pagamento.");
  }
}

function fecharModalPagamento() {
  document.getElementById("modal-pagamento").classList.add("hidden");
}

carregarPagamentos();

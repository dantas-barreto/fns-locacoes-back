// =======================
//  Carregamento inicial
// =======================
document.addEventListener("DOMContentLoaded", () => {
    carregarClientes();
    carregarUsuarios();
    carregarVeiculos();
    carregarLocacoes();
});

// =======================
//  Carregar listas
// =======================
async function carregarClientes() {
    try {
        const clientes = await Api.get("/clientes");
        const select = document.getElementById("clienteSelect");
        select.innerHTML = "";

        clientes.forEach(c => {
            const op = document.createElement("option");
            op.value = c.id;
            op.textContent = c.nome;
            select.appendChild(op);
        });
    } catch (err) {
        console.error("Erro ao carregar clientes:", err);
    }
}

async function carregarUsuarios() {
    try {
        const usuarios = await Api.get("/usuarios");
        const select = document.getElementById("usuarioSelect");
        select.innerHTML = "";

        usuarios.forEach(u => {
            const op = document.createElement("option");
            op.value = u.id;
            op.textContent = u.nome;
            select.appendChild(op);
        });
    } catch (err) {
        console.error("Erro ao carregar usuários:", err);
    }
}

async function carregarVeiculos() {
    try {
        const veiculos = await Api.get("/veiculos");
        const select = document.getElementById("veiculoSelect");
        select.innerHTML = "";

        veiculos.forEach(v => {
            const op = document.createElement("option");
            op.value = v.id;
            op.textContent = `${v.marca} ${v.modelo} (${v.placa})`;
            select.appendChild(op);
        });
    } catch (err) {
        console.error("Erro ao carregar veículos:", err);
    }
}

// =======================
//  Listar locações
// =======================
async function carregarLocacoes() {
    try {
        const locacoes = await Api.get("/locacoes");
        const tabela = document.getElementById("tabelaLocacoes");
        tabela.innerHTML = "";

        locacoes.forEach(l => {
            const tr = document.createElement("tr");

            tr.innerHTML = `
                <td>${l.id}</td>
                <td>${l.dataInicio || ""}</td>
                <td>${l.dataFimPrevista || ""}</td>
                <td>${l.dataDevolucao || ""}</td>
                <td>${l.valorDiaria}</td>
                <td>${l.valorFinal}</td>
                <td>${l.cliente?.nome || "-"}</td>
                <td>${l.usuario?.nome || "-"}</td>
                <td>${l.veiculo ? l.veiculo.marca + " " + l.veiculo.modelo : "-"}</td>
                <td>
                    <button onclick="editarLocacao(${l.id})">Editar</button>
                    <button onclick="excluirLocacao(${l.id})">Excluir</button>
                </td>
            `;

            tabela.appendChild(tr);
        });
    } catch (err) {
        console.error("Erro ao carregar locações:", err);
    }
}

// =======================
//  Salvar locação
// =======================
async function salvarLocacao() {
    const id = document.getElementById("locacaoId").value;

    const locacao = {
        dataInicio: document.getElementById("dataInicio").value,
        dataFimPrevista: document.getElementById("dataFimPrevista").value,
        dataDevolucao: document.getElementById("dataDevolucao").value || null,
        valorDiaria: parseFloat(document.getElementById("valorDiaria").value),
        valorFinal: parseFloat(document.getElementById("valorFinal").value),

        cliente: { id: parseInt(document.getElementById("clienteSelect").value) },
        usuario: { id: parseInt(document.getElementById("usuarioSelect").value) },
        veiculo: { id: parseInt(document.getElementById("veiculoSelect").value) }
    };

    try {
        if (id) {
            await Api.put(`/locacoes/${id}`, locacao);
        } else {
            await Api.post("/locacoes", locacao);
        }

        fecharModalLocacao();
        carregarLocacoes();

    } catch (err) {
        console.error("Erro ao salvar locação:", err);
        alert("Erro ao salvar locação!");
    }
}

// =======================
//  Editar locação
// =======================
async function editarLocacao(id) {
    try {
        const l = await Api.get(`/locacoes/${id}`);

        document.getElementById("locacaoId").value = l.id;
        document.getElementById("dataInicio").value = l.dataInicio?.substring(0, 10);
        document.getElementById("dataFimPrevista").value = l.dataFimPrevista?.substring(0, 10);
        document.getElementById("dataDevolucao").value = l.dataDevolucao?.substring(0, 10) || "";
        document.getElementById("valorDiaria").value = l.valorDiaria;
        document.getElementById("valorFinal").value = l.valorFinal;

        document.getElementById("clienteSelect").value = l.cliente?.id;
        document.getElementById("usuarioSelect").value = l.usuario?.id;
        document.getElementById("veiculoSelect").value = l.veiculo?.id;

        abrirModalLocacao();

    } catch (err) {
        console.error("Erro ao editar locação:", err);
    }
}

// =======================
//  Excluir locação
// =======================
async function excluirLocacao(id) {
    if (!confirm("Deseja excluir esta locação?")) return;

    try {
        await Api.del(`/locacoes/${id}`);
        carregarLocacoes();
    } catch (err) {
        console.error("Erro ao excluir locação:", err);
    }
}

// =======================
//  Modal
// =======================
function abrirModalLocacao() {
    document.getElementById("modalLocacao").style.display = "block";
}

function fecharModalLocacao() {
    document.getElementById("modalLocacao").style.display = "none";

    // limpar campos
    document.getElementById("locacaoId").value = "";
    document.getElementById("dataInicio").value = "";
    document.getElementById("dataFimPrevista").value = "";
    document.getElementById("dataDevolucao").value = "";
    document.getElementById("valorDiaria").value = "";
    document.getElementById("valorFinal").value = "";
}

// =========================
// ELEMENTOS
// =========================
const tabelaBody = () => document.querySelector("#tabela-usuarios tbody");
const modal = () => document.getElementById("modal-usuario");

const inputId = () => document.getElementById("usuario-id");
const inputNome = () => document.getElementById("usuario-nome");
const inputLogin = () => document.getElementById("usuario-login");
const inputSenha = () => document.getElementById("usuario-senha");
const inputPerfil = () => document.getElementById("usuario-perfil");


// =========================
// CARREGAR LISTA
// =========================
async function carregarUsuarios() {
    try {
        const usuarios = await Api.get("/usuarios");

        const tbody = tabelaBody();
        tbody.innerHTML = "";

        usuarios.forEach(u => {
            const tr = document.createElement("tr");

            tr.innerHTML = `
                <td>${u.id}</td>
                <td>${u.nome}</td>
                <td>${u.login}</td>
                <td>${u.perfilUsuario}</td>
                <td>
                    <button onclick="editarUsuario(${u.id})">Editar</button>
                    <button onclick="excluirUsuario(${u.id})">Excluir</button>
                </td>
            `;

            tbody.appendChild(tr);
        });

    } catch (e) {
        console.error("Erro ao carregar usuários:", e);
    }
}


// =========================
// ABRIR / FECHAR MODAL
// =========================
function novoUsuario() {
    inputId().value = "";
    inputNome().value = "";
    inputLogin().value = "";
    inputSenha().value = "";
    inputPerfil().value = "ATENDENTE";
    document.getElementById("titulo-usuario").innerText = "Novo Usuário";
    modal().classList.remove("hidden");
}

function fecharModalUsuario() {
    modal().classList.add("hidden");
}


// =========================
// EDITAR
// =========================
async function editarUsuario(id) {
    const usuario = await Api.get(`/usuarios/${id}`);

    inputId().value = usuario.id;
    inputNome().value = usuario.nome;
    inputLogin().value = usuario.login;

    // senha NÃO vem da API (hash) → ficamos com campo vazio
    inputSenha().value = "";

    inputPerfil().value = usuario.perfilUsuario;

    document.getElementById("titulo-usuario").innerText = "Editar Usuário";

    modal().classList.remove("hidden");
}


// =========================
// SALVAR (CRIAR / EDITAR)
// =========================
async function salvarUsuario() {
    const id = inputId().value;

    const usuario = {
        nome: inputNome().value,
        login: inputLogin().value,
        senhaHash: inputSenha().value, // sua API recebe senhaHash
        perfilUsuario: inputPerfil().value
    };

    if (id) {
        await Api.put(`/usuarios/${id}`, usuario);
    } else {
        await Api.post("/usuarios", usuario);
    }

    fecharModalUsuario();
    carregarUsuarios();
}


// =========================
// EXCLUIR
// =========================
async function excluirUsuario(id) {
    if (!confirm("Deseja realmente excluir?")) return;

    await Api.del(`/usuarios/${id}`);
    carregarUsuarios();
}


// =========================
// INICIAR
// =========================
document.addEventListener("DOMContentLoaded", () => {
    carregarUsuarios();
});

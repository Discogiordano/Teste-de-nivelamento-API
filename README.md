# 🏥 Hospital API

## 📋 Descrição
API desenvolvida em **Quarkus** para gerenciamento de **fichas de pacientes**, planos de saúde e especialidades médicas.  
Faz parte do projeto de nivelamento com backend em **Java + Quarkus** e frontend em **Vue 3 + Vite**.

---

## ⚙️ Tecnologias
- [Quarkus](https://quarkus.io/) – Backend
- [Hibernate ORM com Panache](https://quarkus.io/guides/hibernate-orm-panache) – ORM
- [PostgreSQL](https://www.postgresql.org/) – Banco de Dados
---

## 🗄️ Modelagem (Entidades principais)
- **FichaPaciente**
- **PlanoDeSaude**
- **Especialidade**

---


### Pré-requisitos
- Java 17+
- Maven 3.9+
- PostgreSQL 14+ (local) — ou Docker, se preferir rodar via contêiner
- `psql` disponível no terminal (vem com a instalação do PostgreSQL)

## 🗃️ Como criar o banco de dados

> Os comandos abaixo criam **usuário**, **banco** e **permissões** automaticamente.  
> Execute no terminal com o usuário administrativo do PostgreSQL (ex.: `postgres`).

# cria o usuário da aplicação
psql -U postgres -h localhost -d postgres -v ON_ERROR_STOP=1 -c "CREATE USER hospital_user WITH PASSWORD 'hospital_pass';"

# cria o banco da aplicação e define o owner
psql -U postgres -h localhost -d postgres -v ON_ERROR_STOP=1 -c "CREATE DATABASE hospital WITH OWNER = hospital_user ENCODING 'UTF8' TEMPLATE template0;"

# concede permissões no schema public e define privilégios padrôes
psql -U postgres -h localhost -d hospital -v ON_ERROR_STOP=1 -c "GRANT ALL ON SCHEMA public TO hospital_user; ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON TABLES TO hospital_user; ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON SEQUENCES TO hospital_user;"

## 🚀 Como rodar o backend (Quarkus)

> Antes, crie o banco e o usuário conforme a seção **“Como criar o banco de dados”** deste README.

### ✅ Pré-requisitos
- **Java 17+** (JDK)
- **Maven 3.9+**
- **PostgreSQL** rodando localmente (porta padrão `5432`)
- **psql** disponível no terminal (vem com o PostgreSQL)
- **Git Bash**
- **IntelliJ IDEA** (ou outra IDE de sua preferência)

---

### 1) Clonar o projeto
crie uma pasta no explorador de arquivos, abra terminal do git bash, e use esse comando: 
git clone https://github.com/Discogiordano/Teste-de-nivelamento-API.git

### 2) Abrir o projeto no IntelliJ IDEA

1. Abra o IntelliJ → **File → Open...**  
2. Selecione a pasta **Teste-de-nivelamento-API** (onde está o `pom.xml`).  
3. Aguarde o IntelliJ **baixar as dependências Maven**.  
4. Se não importar automático: **View → Tool Windows → Maven → Reload All Maven Projects**.
5. ou digite no terminal: "mvn install" ou "./mvnw clean install"

---

### 3) Trocar para a branch `dev`

- Pelo IntelliJ (canto inferior direito): clique em gitlens branch (ex.: `main`) → selecione **`dev`**.  
- Se não aparecer, use o terminal:

  git fetch --all
  git checkout dev

### 4) Executar o projeto

#### ) Pelo terminal

mvnw quarkus:dev
ou
./mvnw quarkus:dev


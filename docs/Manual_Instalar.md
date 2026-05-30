# ⚙️ Instalação e Execução com Maven

## 📋 Pré-requisitos

Antes de iniciar, garanta que possui instalado:

- Java JDK 25
- Maven
- MySQL Server 8+
- Git

> O projeto utiliza Maven para gerenciar dependências como JavaFX e MySQL Connector.

---

## 📥 Clonando o repositório

```bash
git clone https://github.com/Niikoto/RESMOD.git
cd RESMOD
```

---

## 🗄️ Configuração do Banco de Dados

Crie o banco de dados:

```sql
CREATE DATABASE intellidog;
```

Depois, execute os scripts localizados em:

```text
src/db/
```

Ordem sugerida:

1. `IntelliDog.sql`
2. `insert.sql`
3. `Selects.sql` *(opcional)*

---

## 🔌 Configuração da Conexão

Verifique o arquivo:

```text
src/factory/ConnectionFactory.java
```

Confirme se as credenciais estão de acordo com seu ambiente local:

```java
private static final String URL = "jdbc:mysql://localhost/intellidog";
private static final String USER = "root";
private static final String PASSWORD = "";
```

> Ajuste usuário e senha conforme sua configuração do MySQL.

---

# 🚀 Execução

## 💻 Opção 1 — IntelliJ IDEA

1. Abra a pasta do projeto no IntelliJ.
2. Aguarde o Maven carregar as dependências.
3. Caso necessário, clique com o botão direito no `pom.xml` e selecione:

```text
Add as Maven Project
```

4. Verifique se o SDK do projeto está configurado como Java 25:

```text
File > Project Structure > Project SDK
```

5. Execute pelo Maven:

```text
Maven > Plugins > javafx > javafx:run
```

Ou pelo terminal integrado:

```bash
mvn javafx:run
```

---

## 💻 Opção 2 — VSCode

1. Abra a pasta do projeto no VSCode.
2. Instale as extensões:
   - Extension Pack for Java
   - Maven for Java
3. Aguarde o Maven reconhecer o `pom.xml`.
4. Execute pelo terminal:

```bash
mvn javafx:run
```

---

## ⚠️ Problemas comuns

### Java não compatível

O projeto está configurado para Java 25 no `pom.xml`.

Verifique com:

```bash
java -version
```

---

### Maven não reconhecido

Verifique se o Maven está instalado:

```bash
mvn -version
```

---

### Erro de conexão com banco

Verifique:

- se o MySQL está rodando
- se o banco `intellidog` foi criado
- se os scripts SQL foram executados
- se usuário e senha estão corretos no `ConnectionFactory.java`

---

### Erro ao executar JavaFX

Execute usando:

```bash
mvn javafx:run
```

Evite executar diretamente o `Main.java`, pois o Maven já configura as dependências necessárias do JavaFX.

---

## ℹ️ Observações

- As dependências JavaFX e MySQL Connector são gerenciadas pelo Maven.
- Não é necessário adicionar `.jar` manualmente.
- Não é necessário configurar `--module-path` manualmente ao executar com `mvn javafx:run`.
- O banco de dados MySQL continua sendo obrigatório para o funcionamento do sistema.

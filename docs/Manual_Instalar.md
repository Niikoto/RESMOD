# ⚙️ Instalação e Execução

## 📋 Pré-requisitos

Antes de iniciar, garanta que você possui instalado:

- Java JDK 17 ou superior
- JavaFX SDK compatível com sua versão do Java
- MySQL Server 8+
- Git
- IDE (IntelliJ, VSCode, Eclipse)
  
Opcional:

- Cliente SQL (MySQL Workbench, DBeaver, etc.)




## 📥 Clonando o repositório
```Bash
git clone https://github.com/Niikoto/RESMOD.git
cd RESMOD
```

 

## 🗄️ Configuração do Banco de Dados
**1. Criar o banco**
```CREATE DATABASE intellidog;```

**2. Executar scripts**

Localizados em:

```src/db/```

Execute:
- IntelliDog.sql
- (Opcional) insert.sql
- (Opcional) Selects.sql



## 🔌 Configuração da Conexão

Crie o arquivo:

```src/factory/ConnectionFactory.java```

```
package factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static final String URL = "jdbc:mysql://localhost/intellidog";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar com o banco de dados.", e);
        }
    }
}
```

**⚠️ Ajuste usuário e senha conforme seu ambiente. Crie com base no ```ConnectionFactoryExample.java```**



## 📦 Dependência: MySQL Connector

Baixe o MySQL Connector/J e adicione ao projeto.

## 🚀 Execução
#### 💻 Com IDE (IntelliJ / VSCode)
#### 🔧 Configuração
Abra a pasta do projeto e configure:
- JDK 17+
- JavaFX SDK
- MySQL Connector (.jar)

**No VM Options (JavaFX), Adicione:**

```--module-path <caminho-javafx>/lib --add-modules javafx.controls,javafx.fxml```

E Execute a classe:

```Main.java```


# ⚠️ Atenção 
- Configuração é manual
- Banco de dados é obrigatório
- JavaFX precisa ser configurado explicitamente

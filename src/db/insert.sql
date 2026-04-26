use intellidog;

-- Inserts

-- --------------------------------
-- Cargo
-- --------------------------------
insert into cargo(tipo,adm) values("Diretor",1);
insert into cargo(tipo,adm) values("Compras",0);
insert into cargo(tipo,adm) values("funcionario",0);

-- -------------------------------
-- Usuario
-- -------------------------------
insert into usuario(ID_email,Nome,Senha,COD_Cargo) values("diretor@gmail.com", "Diretor",123,1);
insert into usuario(ID_email,Nome,Senha,COD_Cargo) values("compras@gmail.com", "Compras",123,2);
insert into usuario(ID_email,Nome,Senha,COD_Cargo) values("funcionario@gmail.com", "funcionario",123,3);

-- -------------------------------
-- Fornecedores
-- -------------------------------
insert into fornecedor(nome_fornecedor,descricao) values("Fonecedor1","Teste fornecedor");
insert into fornecedor(nome_fornecedor,descricao) values("Fonecedor2","Teste fornecedor2");


-- -------------------------------
-- Categoria
-- -------------------------------
insert into categoria(categoria) values("Informatica");
insert into categoria(categoria) values("Limpeza");

-- -------------------------------
-- Produto
-- -------------------------------
insert into produto(nome_produto, preco, quantidade, minimo, COD_categoria, COD_fornecedor) values("Mouse",10.00,10,2,1,1);
insert into produto(nome_produto, preco, quantidade, minimo, COD_categoria, COD_fornecedor) values("Sabão",5.00,10,5,2,2);
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
insert into fornecedor(CNPJ, nome_fornecedor, descricao, estado, municipio, telefone) values("12345678901234","Aurélio Bispo LTDA.", "Vendemos peixe", "SP", "São josé dos Campos", "1299777333");
insert into fornecedor(CNPJ, nome_fornecedor, descricao, estado, municipio, telefone) values("22365675901276","Atacadão", "Vendemos coisa", "SP", "São josé do rio Preto", "12444335323");


-- -------------------------------
-- Categoria
-- -------------------------------
insert into categoria(categoria) values("Informatica");
insert into categoria(categoria) values("Limpeza");

-- -------------------------------
-- Produto
-- -------------------------------
insert into produto(nome_produto, preco, quantidade, minimo, COD_categoria, COD_CNPJ) values("Mouse",10.00,10,2,1,"12345678901234");
insert into produto(nome_produto, preco, quantidade, minimo, COD_categoria, COD_CNPJ) values("Sabão",5.00,10,5,2,"22365675901276");
use intellidog;

-- --------------------------------
-- Inserts
-- --------------------------------

-- --------------------------------
-- Cargo
-- --------------------------------
insert into cargo(tipo,adm) values("Diretor",1);

-- -------------------------------
-- Usuario
-- -------------------------------
insert into usuario(ID_email,Nome,Senha,COD_Cargo) values("joãokleber@gmail.com", "Kleber João",123,1);

-- -------------------------------
-- Fornecedores
-- -------------------------------
insert into fornecedor(nome_fornecedor,descrição) values("Juninhos","Só os brabos");

-- -------------------------------
-- Categoria
-- -------------------------------
insert into categoria(categoria) values("Informatica");

-- -------------------------------
-- Produto
-- -------------------------------
insert into produto(nome_produto, preço, quantidade, minimo, COD_categoria, COD_fornecedor) values("Mouse",10.00,10,2,1,1);


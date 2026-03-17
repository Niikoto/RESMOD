use intellidog;

-- --------------------------------
-- Inserts
-- --------------------------------
insert into cargo(tipo,adm) values("Diretor",1);
insert into usuario(ID_email,Nome,Senha,COD_Cargo) values("joãokleber@gmail.com", "Kleber João",123,1);
insert into fornecedor(nome_fornecedor,descrição) values("Juninhos","Só os brabos");
insert into categoria(categoria) values("Informatica");
insert into produto(nome_produto,preço,COD_categoria,COD_fornecedor) values("Mouse",10.00,1,1);
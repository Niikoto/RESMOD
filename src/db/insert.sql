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
insert into fornecedor(CNPJ, nome_fornecedor, descricao, estado, municipio, rua, numero, telefone) values("12345678901234","Aurélio Bispo LTDA.", "Vendemos peixe", "SP", "São josé dos Campos", "Peixinhos", 120,"1299777333");
insert into fornecedor(CNPJ, nome_fornecedor, descricao, estado, municipio, rua, numero, telefone) values("22365675901276","Atacadão", "Vendemos coisa", "SP", "São josé do rio Preto", "Rua seila", 12, "12444335323");


-- -------------------------------
-- Categoria
-- -------------------------------
insert into categoria(categoria) values("Informatica");
insert into categoria(categoria) values("Limpeza");

-- -------------------------------
-- Produto
-- -------------------------------
insert into produto(img_prod, nome_produto, preco, quantidade, minimo, COD_categoria, COD_CNPJ) values("img1.jpg","Mouse",10.00,10,2,1,"12345678901234");
insert into produto(img_prod, nome_produto, preco, quantidade, minimo, COD_categoria, COD_CNPJ) values("img2.jpg","Sabão",5.00,10,5,2,"22365675901276");

-- -------------------------------
-- Pedidos
-- -------------------------------
INSERT INTO pedido(criado, `status`, motivo, preco_total, COD_email) VALUES(NOW(), "aprovado", "Mostrar aprovado", 0, "diretor@gmail.com");
INSERT INTO pedido(criado, `status`, motivo, preco_total, COD_email) VALUES(NOW(), "negado", "Mostrar negado", 0, "diretor@gmail.com");
INSERT INTO pedido(criado, `status`, motivo, preco_total, COD_email) VALUES(NOW(), "em aberto", "Mostrar em aberto", 0, "compras@gmail.com");

-- -------------------------------
-- Produto no pedido
-- -------------------------------
insert into produto_has_pedido(COD_produto, COD_pedido, quantidade, preco_unitario) values(1,1,10,100.00);
insert into produto_has_pedido(COD_pedido, COD_produto, quantidade, preco_unitario) values (1,2,10,200.00);
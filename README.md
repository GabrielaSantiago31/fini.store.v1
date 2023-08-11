# fini.store.v1
Sistema de vendas de gerenciamento de vendas e estoque de uma loja de doces. 

<p>
<img src="https://img.shields.io/badge/STATUS-COMPLETO-green"/>
</p>

### :arrow_forward: Abrir e rodar o projeto
---
Após baixar o projeto, você pode abri-lo com o Eclipse IDE 2022-12. Para isso, na tela de launcher:
- Clique com o botão esquerdo do mouse em File -> Import -> Existing Pojects into Workspace -> Clique no botão Browse... 
-> Selecione o projeto -> Selecione a opção finish.
  
### :hammer_and_pick: Funcionalidade do Projeto 
---
- <kbd>Sign in:</kbd> O sistema permite o cadastro do cliente no sistema. Para isso, deverá ser selecionada a opção sign in, a qual pedirá os seguintes dados: nome, sobrenome, email, telefone, cpf, data de nascimento e senha.
Os dados são verificados assim que inseridos, não permitindo o registro de dados inválidos.
A confirmação do endereço se dá através de um Web, o qual é utilizado para completar e validar o endereço do cliente quando o mesmo insere o cep.
Caso o cliente já esteja registrado no banco de dados, a ele serão apresentadas as opções de fazer log in ou sair.

- <kbd>Log in:</kbd> O usuário poderá fazer o login no sistema utilizando o email e a senha cadastrados. Caso haja um equívoco no login e/ou na senha, o sistema informará que há um erro e o usuário poderá tentar novamente.

- <kbd>Catálogo de produtos:</kbd> Uma vez que o login é confirmado, o cliente poderá conferir os produtos disponíveis, escolhendo a opção 1.
- 
- <kbd>Fazer um pedido:</kbd> O cliente poderá escolher a opção 2, caso queira fazer um pedido. Ao clicar na opção, ele deverá informar o código do produto e a quantidade desejada.
- A quantidade de ingredientes no estoque é decrescida de acordo com o produto (e os ingredientes que são necessários para fabricar um pacote) e a quantidade desejada (a quantidade total dos ingredientes para a quantidade de pacotes desejados),
- assim, caso não haja ingredientes suficientes para a realização do pedido, o cliente é informado e, assim, poderá escolher outro produto ou retornar ao menu.
- Caso o pedido seja confirmado, o cliente receberá um email de confirmação.

- <kbd>Atualizar dados:</kbd> O cliente ainda poderá atualizar os seus dados cadastrais ao escolher a opção 3.

- <kbd>Log out:</kbd> Ao selecionar a opção 4 e sair do sistema.

O sistema ainda pode ser acessado pelo administrador, o qual pode se logar no sistema através de um email e uma senha já cadastrada no banco de dados. Uma vez logado, poderá consultar o catálogo e:

- <kbd>Registrar um produto:</kbd> O administrador poderá cadastrar um produto novo, inserindo as informações: sabor, preço, gramas por pacote, nome, ingredientes dentre outras.

- <kbd>Registrar ingredientes:</kbd> O admnistrador pode aumentar a quantidade de ingredientes no estoque, bem como adicionar um ingrediente novo caso necessário.

- <kbd>Atualizar dados:</kbd> O administrador poderá atualizar os dados de acesso ao sistema.

- <kbd>Log out:</kbd> Ao digitar a opção 5, o administrador poderá encerrar a sessão.

- ATENÇÃO!!!
- O sistema não apresenta uma view real, mas, sim, uma simulação da view feita em linha de comando.

###  :octocat: Técnicas e Tecnologias Utilizadas
---
- Java 17
- Web Service (Via Cep)
- MySQL Workbench
- Eclipse IDE
- POO

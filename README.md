# Github Repositories <img src="https://github.com/alisonViana/app-repositories-bootcampInter/blob/master/images/app_icon.png" alt="ícone" width="48" height="48" />

Olá, este é o repositório do meu aplicativo Github Repositories, projeto desenvolvido para o Bootcamp Android Developer oferecido pela Digital Innovation One em parceria com o Inter. O aplicativo consiste em um visualizador de repositórios do github do usuário pesquisado. Ele traz, em forma de cartões, as principais informações, como o nome do repositório, a descrição resumida, a linguagem de programação e as estrelas recebidas. A partir daí, é possível selecionar um repositório específico e abrir suas informações mais detalhadas, incluindo um link que leva direto para a página do Github do repositório. 

O aplicativo foi desenvolvido utilizando os princípios de arquitetura MVVM com Clean Architecture, contando com a utilização de corrotinas e libs como o Koin para injeção de dependência e o Retrofit para consumo de API do Github.



<p align="center"> 
     <img src="https://github.com/alisonViana/app-repositories-bootcampInter/blob/master/images/search.png" alt="Tela de pesquisa" width="240" height="520" />
</p>

<p align="center">
     [ Imagem: tela de pesquisa ]
</p>



## Toque pessoal

A partir do projeto base desenvolvido durante o bootcamp, resolvi implementar algumas modificações. Elas agregam novas funcionalidades e tornam mais dinâmico o uso, alterando elementos do layout e as funções já existentes. Abaixo estão algumas delas.



### Detalhes do repositório

Adicionei uma nova funcionalidade ao aplicativo, a possibilidade de selecionar, através de um toque, algum repositório específico dos retornados da pesquisa e com isso visualizar suas informações de forma mais detalhada, trazendo a descrição completa do repositório, as quantidades de "stars", "watchers" e "forks" e um link que leva para a página do repositório no site do GitHub!



<p align="center"> 
     <img src="https://github.com/alisonViana/app-repositories-bootcampInter/blob/master/images/repo_detail.png" alt="Detalhes repositório" width="240" height="520" />
</p>

<p align="center">
     [ Imagem: tela com detalhes do repositório ]
</p>



### Mensagens

Adicionei algumas mensagens para melhor orientar o usuário. A primeira delas, é uma mensagem inicial, na qual o usuário é instruído a como realizar a pesquisa pelos repositórios do GitHub de algum usuário da plataforma. A outra mensagem, é referente ao retorno recebido quando o usuário pesquisado não possui repositórios, antes, era apresentado apenas a tela em branco, agora, a mensagem esclarece que o perfil pesquisado está vazio. 

<p align="center"> 
     <img src="https://github.com/alisonViana/app-repositories-bootcampInter/blob/master/images/initial_message.png" alt="Mensagem inicial" width="240" height="520" />
     <img src="https://github.com/alisonViana/app-repositories-bootcampInter/blob/master/images/empty_repo_message.png" alt="Mensagem repositório vazio" width="240" height="520" /> 
</p>

<p align="center">
     [ Imagens: mensagem inicial e mensagem de repositório vazio]
</p>
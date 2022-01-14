# GuardianVIPS


### Sistema de Vips



Este sistema possui compatibilidade com sistema de permissoes que utiliza comandos para adiconar ou remover os cargos de vips.
<br>
É possível adiconar comandos que serão executados caso o usuario receba uma vip, sistema de setvip sem a ativação de comando bonus, entre outras funcionalidades.

****


# Comandos

***
## Adicionar Vip a um jogador
Comando suporta adiconar Vip para jogadores offline, comandos que precisa acessar o jogador online não irao funcionar.
###Adicionar tempo limitado para um jogador.
```
/darvip <Player> <Vip> <Days> <Hours> <Minutes>
```
###Adicionar tempo ilimitado para um jogador

```
/darvip <Player> <Vip> eterno
```
#### Permissão: guardianvips.addvip

***
## Adicionar Tempo Vip
Comando suporta adiconar Vip para jogadores offline, comandos que precisa acessar o jogador online não irao funcionar.
###Adicionar tempo para um jogador que ja possui vip
```
/addtempovip <Player> <Days> <Hours> <Minutes>
```
###Adicionar tempo para para todos os jogadores que possuem vip

```
/addtempovip * <Vip> <Days> <Hours> <Minutes>
```
#### Permissão: guardianvips.addtimevip

***
## Remover Vip a um jogador
Este comando remove determinado vip de um jogador, o comando tem suporte para ser executado com o jogador offline.
###Remove vip de um jogador.
```
/removervip <Player> <Vip>
```
#### Permissão: guardianvips.removevip

***

***
## Setar Vip a um jogador
Este comando seta determinado vip em um jogador, o comando tem suporte para ser executado com o jogador offline e ele não executa os comandos de bonus de ativação de vip.
###Set vip em um jogador.
```
/setvip <Player> <Vip> <Days> <Hours> <Minutes>
```
#### Permissão: guardianvips.setvip

***

***
## Setar Vip a um jogador
Este comando seta determinado vip em um jogador, o comando tem suporte para ser executado com o jogador offline e ele não executa os comandos de bonus de ativação de vip.
###Set vip em um jogador.
```
/setvip <Player> <Vip> <Days> <Hours> <Minutes>
```
#### Permissão: guardianvips.setvip

***

***
## Gerar Chave de ativação Vip
Este comando gera 2 codigos que deixa possivel qualquer jogador que tenha acesso a esses codigos ativar um determinado vip quando desejar uma quantidade de vezes definidas na geração, o comando não depende de jogadores e qualquer jogador com esse codigo pode utilziar caso possua quantidade de utilização e a chave esteja tiva
###Set vip em um jogador.
```
/gerarkeyvip <Vip> <Days> <Hours> <Minutes> <Quantidade de Usos>
```
#### Permissão: guardianvips.key.generate

***

***
## Gerar Chave de ativação Vip
Este comando desabiltia uma chave de ativação de vip já existente, a chave não é excluida e caso queira ativa a mesma novamente, é precisso alterar no arquivo keys.yml
###Set vip em um jogador.
```
/removerkeyvip <Chave>
```
Obs: A chave pode ser desabilitada pela chave numérica ou a chave alfabética.
#### Permissão: guardianvips.removekeyvip
***

***
## Lista todos os vips ativos no servidor
Este comando lista todos os usuarios que possuem alguma vip ativada, e as informações sobre ele
###Set vip em um jogador.
```
/listvip
```
#### Permissão: guardianvips.listvips
***

***
## Lista todos os chaves de vips no servidor
Este comando lista todos as chaves de vips que possuem no servidor ou as chaves de um jogador
###Listar chaves vip.
```
/listarkeysvip
```

###Listar chaves vip.
```
/listarkeysvip <Player>
```
#### Permissão: guardianvips.key.list
Obs: Este comando lista as chaves de um jogador
***

***
## Lista todos os chaves de vips no servidor
Este comando lista todos as chaves de vips que possuem no servidor ou as chaves de um jogador
###Listar chaves vip.
```
/listarkeysvip
```

###Listar chaves vip.
```
/listarkeysvip <Player>
```
#### Permissão: guardianvips.key.list
Obs: Este comando lista as chaves de um jogador
***




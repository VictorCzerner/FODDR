# FODDR

Aplicacao Spring Boot para apoiar montagens de elencos e desafios SBC (Squad Building Challenges) no contexto de jogos de futebol. O sistema calcula combinacoes possiveis de cartas por faixa de rating para atingir um OVR alvo e ajuda a aproveitar os jogadores (forragens) disponiveis.

## Visao geral
- Stack principal: Java 21, Spring Boot 3.5.7, Spring Web, Spring Data JPA, PostgreSQL e Lombok.
- Arquitetura guiada por camadas hexagonais: dominio central, casos de uso (aplicacao) e adaptadores de entrada/saida.
- API exposta via REST para buscar tabelas de combinacoes por OVR e resolver desafios SBC utilizando o estoque de cartas.
- Persistencia do recurso `elenco` em PostgreSQL com matriz de inteiros serializada em JSON.

## Estrutura de pastas (resumida)
```
src/main/java/com/czerner/foddr
├── adaptadores
│   ├── apresentacao          # Controllers REST e presenters
│   └── dados                 # Implementacoes JPA do repositorio
├── aplicacao                 # Casos de uso e DTOs de resposta
└── dominio                   # Entidades, servicos e portas de dados
```

## Camadas e responsabilidades
- **Dominio**  
  - `entidades`: modelos centrais (`elenco`, `forragens`, `SBC`).  
  - `servicos`: regras para gerar combinacoes (`elencoService`), manipular estoque (`forragensService`) e resolver desafios (`SBCService`).  
  - `dados`: porta `elencoRepository` e conversor `IntMatrixJsonConverter` que transforma `int[][]` em JSON para o banco.
- **Aplicacao**  
  - `BuscaMenorPossivelUC`: caso de uso que valida entrada e delega ao dominio a geracao de combinacoes.  
  - `Responses`: objetos imutaveis enviados para a camada de adaptadores.
- **Adaptadores**  
  - `ElencoController`: endpoint GET para consultar combinacoes por OVR e faixa de rating.  
  - `SBCController`: endpoint POST que cria um SBC em memoria e procura solucoes com base nas forragens informadas.  
  - `SolucoesPresenter`: ajusta o formato final para a API.  
  - `ElencoRepositoryJPA`: implementa a porta de persistencia usando `JpaRepository`.

## Entidades e algoritmos principais
- `elenco`: representa um desafio com OVR alvo e uma tabela de combinacoes possiveis de cartas (matriz `int[][]` em que cada linha indica quantos jogadores de cada rating sao usados).  
- `forragens`: armazena o inventario de cartas do rating 79 a 93; inclui getters/setters individuais para facilitar integracao com front-ends.  
- `SBC`: agrega varios `elenco` com base nos OVRS solicitados e usa `elencoService` para gerar as tabelas.
- `elencoService`: realiza um backtracking sobre as quantidades de jogadores por rating para encontrar combinacoes de 11 cartas que atinjam exatamente o OVR alvo, aplicando a formula de bonus usada nos SBC oficiais.  
- `SBCService`: aplica busca recursiva para consumir o estoque (`forragens`) e tentar completar todos os elencos do desafio, registrando combinacoes completas, totais usados e o que faltou.

## Fluxos da API
- `GET /elenco/{ovr}/{min}/{max}`  
  1. O controller valida parametros basicos e chama `BuscaMenorPossivelUC`.  
  2. O caso de uso delega a `elencoService.buscaPorOvr`, que recalcula a matriz de combinacoes em tempo real.  
  3. O resultado (`ElencoResponse`) e retornado como `SolucoesPresenter` com a matriz `solucoes`.

- `POST /sbc/encontrar`  
  1. Entrada esperada:
     ```json
     {
       "sbc": {
         "elencos": [
           { "ovr": 84, "informs": 0 },
           { "ovr": 85, "informs": 1 },
           { "ovr": 86, "informs": 0 }
         ]
       },
       "forragem": [2,1,0,3,4,5,1,0,0,0,0,0,0,0,0]
     }
     ```  
  2. `SBCService` cria o objeto `SBC`, gera combinacoes para cada OVR e executa busca recursiva para cobrir os elencos com as cartas fornecidas.  
  3. Resposta (`SBCResponse`) inclui listas de elencos completos, total de cartas utilizadas e combinacoes faltantes.

## Persistencia e configuracao
- Banco principal: PostgreSQL (ver `src/main/resources/application.properties` para URL e parametros).  
- A tabela `elenco` possui coluna `tabela` do tipo JSON, populada pelo `IntMatrixJsonConverter`.  
- `spring.jpa.hibernate.ddl-auto=none`: espera-se que o schema exista previamente (migracoes devem ser tratadas externamente).  
- Logs SQL estao habilitados (`spring.jpa.show-sql=true`) para facilitar diagnosticos durante o desenvolvimento.

## Como executar localmente
1. Configure o banco PostgreSQL e garanta que a tabela `elenco` exista com as colunas `ovr` (PK) e `tabela` (JSON).  
2. Ajuste as credenciais de conexao em `application.properties` ou defina variaveis de ambiente equivalentes (`SPRING_DATASOURCE_URL`, etc.).  
3. Rode a aplicacao:  
   ```bash
   ./mvnw spring-boot:run
   ```  
4. A API sobe, por padrao, em `http://localhost:8080`.

## Testes e manutencao
- O projeto ainda nao possui testes automatizados; recomenda-se adicionar testes unitarios para `elencoService` (validados do backtracking) e `SBCService` (cenarios de combinacoes viaveis/inviaveis).  
- Ao introduzir novas faixas de rating ou mudancas de regra, atualize as constantes (ex.: intervalo 79-93 em `forragens`) e revise o algoritmo de media ponderada em `elencoService`.  
- Prefira extender as portas (`elencoRepository`) quando adicionar novas fontes de dados ou cache.

## Proximos passos sugeridos
1. Documentar o contrato da tabela `elenco` (DDL e exemplos de carga) em um arquivo SQL ou ferramenta de migracao.  
2. Expor health checks personalizados via Actuator para monitorar conexao com o banco.  
3. Criar testes de integracao exercitando os endpoints principais com dados em memoria (ex.: `@DataJpaTest` + `@SpringBootTest`).

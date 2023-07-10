
# EP-MAC0350
EP proposto para desenvolvimento de um banco de dados aliado a um projeto web para manuseio deste banco.
## Autores:
Guilherme Mota Pereira, NUSP: 12543307
Rafael de Oliveira Magalhães, NUSP: 12566122
## Projeto do Banco de Dados
### DER

**Arquivo:** EP_MAC0350_DER.pdf

Construímos as seguintes relações intra:
- Pessoa
- Aluno
- Docente
- Funcionário
- Perfil
- Serviço
- Disciplina

E as seguintes relações inter:
- rel_pessoa_perfil
- rel_pessoa_servico
- rel_pessoa_aluno
- rel_pessoa_docente
- rel_pessoa_funcionario
- rel_oferecimento_aluno
- rel_oferecimento_disciplina_docente

As chaves primárias de cada relação estão identificadas pelo sublinhado.

### SQL

**Arquivo**: ep1.sql

Observações:

*Consulta 3.3*. O enunciado não diz que as disciplinas devem estar ordenadas pela quantidade de oferecimento, portanto apenas retornamos os valores pedidos de modo agrupado. 

*Consulta 3.4*. O Enunciado não deixou claro se tanto a data de início do oferecimento quanto a data de fim devem estar dentro do intervalo de maio de 2020 à maio de 2023, portanto consideramos também que a disciplina poderia se iniciar antes se o fim estivesse dentro do intervalo de tempo, ou encerrar depois se o início estivesse dentro do intervalo.  Nesta consulta o enunciado também não pede os 5 docentes que mais ministraram disciplinas de forma ordenada, portanto a consulta apenas retorna os docentes em uma ordem qualquer.

Adicionamos inserções manuais no final do arquivo para cada tabela. Mas também é possível editar pelo sistema web.

### MER

**Arquivo:** EP_MAC0350_MER.pdf

## Manuseio do banco através da web.

**Diretório:** (diretório do arquivo completo)/demo

**Preparação:**
Certifique-se de que seu arquivo ./demo/src/main/application.properties esteja conforme:
~~~java
spring.datasource.url=jdbc:postgresql://localhost:5432/ep
spring.datasource.username=testeusr
spring.datasource.password=testeusr
spring.mvc.hiddenmethod.filter.enabled=true
~~~

**Execução**
Igual à forma de execução apresentada nos slides: rode em ./demo/
```cmd
gradle buildRun
```
e entre na página em http://localhost:8080/pessoas

Observação:

Insira datas no formato: *ano-mes-dia* (ex: 2020-03-26)
<body>
    <h1>💈 Barber API</h1>
    <p>API para agendamento e gerenciamento de serviços de uma barbearia. Este projeto permite que usuários se cadastrem, façam login, visualizem horários disponíveis, agendem, cancelem ou remarquem seus atendimentos. Também permite que os barbeiros se organizem e tenham acesso a agenda e seus compromissos.</p>
    <h2>🚀 Tecnologias Utilizadas</h2>
    <ul>
        <li>Java 21</li>
        <li>Spring Boot</li>
        <li>Spring Web</li>
        <li>Spring Data JPA / Hibernate</li>
        <li>Spring Security</li>
        <li>PostgreSQL</li>
        <li>Java JWT</li>
        <li>SpringDoc OpenAPI (Swagger)</li>
        <li>Docker e Docker Compose</li>
        <li>Maven</li>
        <li>Lombok</li>
    </ul>
    <h2>📌 Funcionalidades</h2>
    <ul>
        <li><strong>Autenticação e Segurança:</strong>
            <ul>
                <li>Cadastro e Login de usuários.</li>
                <li>Autenticação baseada em JSON Web Tokens (JWT).</li>
                <li>Controle de acesso por papéis (<code>ROLE_ADMIN</code>, <code>ROLE_BASIC</code>).</li>
            </ul>
        </li>
        <li><strong>Gerenciamento de Usuários (Acesso <code>ADMIN</code>):</strong>
            <ul>
                <li>Listar, atualizar e deletar usuários.</li>
            </ul>
        </li>
        <li><strong>Gerenciamento de Barbeiros (Acesso <code>ADMIN</code>):</strong>
            <ul>
                <li>CRUD completo para barbeiros.</li>
            </ul>
        </li>
        <li><strong>Gerenciamento de Agendamentos:</strong>
            <ul>
                <li><strong>Usuários (<code>BASIC</code>):</strong>
                    <ul>
                        <li>Visualizar horários livres para uma data específica.</li>
                        <li>Criar, cancelar e remarcar um agendamento.</li>
                    </ul>
                </li>
                <li><strong>Administradores (<code>ADMIN</code>):</strong>
                    <ul>
                        <li>Visualizar todos os agendamentos.</li>
                        <li>Filtrar agendamentos por data e deletar.</li>
                    </ul>
                </li>
            </ul>
        </li>
        <li><strong>Sistema Automatizado:</strong>
            <ul>
                <li>Um serviço agendado (<code>@Scheduled</code>) invalida automaticamente agendamentos passados.</li>
            </ul>
        </li>
        <li><strong>Documentação da API:</strong>
            <ul>
                <li>Documentação interativa com Swagger (OpenAPI).</li>
            </ul>
        </li>
    </ul>
    <h2>🛠️ Configuração e Execução</h2>
                    <p><strong>Ambiente de Produção (Online):</strong><br>
                    A API já está disponível na internet, hospedada na Render. Você pode acessá-la e testá-la diretamente pela documentação interativa do Swagger no link:  <strong><a href="https://barber-api-5f07.onrender.com/swagger-ui.html" target="_blank">https://barber-api-5f07.onrender.com/swagger-ui.html</a></strong>
                        <p></p>
                    <em><strong>Atenção:</strong> Por ser um plano gratuito, o primeiro acesso ao link online pode demorar até um minuto para "acordar" o servidor.</em></p>
        <ol>
        <li>
            <p><strong>Clone o repositório:</strong></p>
            <pre><code>git clone https://github.com/MatheusSButh/barberapp
cd nome-da-pasta</code></pre>
        </li>
        <li>
            <p><strong>Configure as variáveis de ambiente:</strong><br>
            Crie um arquivo <code>.env</code> na raiz do projeto com as credenciais para o banco de dados.</p>
            <pre><code class="language-properties">DB_URL=jdbc:postgresql://db:5432/schedule
DB_USERNAME=postgres
DB_PASSWORD=coloque_sua_senha_aqui
JWT_SECRET=coloque_seu_secret_aqui</code></pre>
        </li>
        <li>
            <p><strong>Execute o projeto com Docker Compose:</strong><br>
            Este comando irá construir a imagem da aplicação e iniciar os containers.</p>
            <pre><code>docker-compose up --build</code></pre>
        </li>
        <li>
            <p><strong>Acesse a aplicação com postman:</strong><br>
            A API estará disponível em <a href="http://localhost:8080">http://localhost:8080</a>.</p>
        </li>
    </ol>
    <h2>📖 Documentação da API (Swagger)</h2>
    <p>Com a aplicação em execução, a documentação completa e interativa da API pode ser acessada em:</p>
    <p><a href="http://localhost:8080/swagger-ui.html"><strong>http://localhost:8080/swagger-ui.html</strong></a></p>
    <p>Para testar os endpoints protegidos, use o endpoint <code>POST /auth/register e POST /auth/login</code> para obter um token e clique no botão "Authorize" para inseri-lo no formato <code>Bearer &lt;seu-token&gt;</code>.</p>
    <h2>🔗 Endpoints da API</h2>
    <h3>Autenticação (/auth)</h3>
    <table>
        <thead>
            <tr>
                <th>Método</th>
                <th>Rota</th>
                <th>Descrição</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td><code>POST</code></td>
                <td>/auth/login</td>
                <td>Autentica um usuário e retorna um token JWT.</td>
            </tr>
            <tr>
                <td><code>POST</code></td>
                <td>/auth/register</td>
                <td>Registra um novo usuário (ADMIN ou BASIC).</td>
            </tr>
        </tbody>
    </table>
    <h3>Agenda do Usuário (/userSchedule) - Rota BASIC</h3>
     <table>
        <thead>
            <tr>
                <th>Método</th>
                <th>Rota</th>
                <th>Descrição</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td><code>GET</code></td>
                <td>/userSchedule/freeTimes</td>
                <td>Lista todos os horários livres para uma data.</td>
            </tr>
            <tr>
                <td><code>POST</code></td>
                <td>/userSchedule</td>
                <td>Cria um novo agendamento para o usuário autenticado.</td>
            </tr>
            <tr>
                <td><code>PUT</code></td>
                <td>/userSchedule/cancel</td>
                <td>Cancela um agendamento.</td>
            </tr>
             <tr>
                <td><code>PUT</code></td>
                <td>/userSchedule/reschedule</td>
                <td>Remarca um agendamento.</td>
            </tr>
        </tbody>
    </table>
    <h3>Gerenciamento (/users, /barbers, /schedule) - Rotas ADMIN</h3>
    <table>
         <thead>
            <tr>
                <th>Método</th>
                <th>Rota</th>
                <th>Descrição</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td><code>GET/PUT/DELETE</code></td>
                <td>/users/{id}</td>
                <td>Gerenciamento completo de usuários.</td>
            </tr>
             <tr>
                <td><code>GET/POST/PUT/DELETE</code></td>
                <td>/barbers/{id}</td>
                <td>Gerenciamento completo de barbeiros.</td>
            </tr>
             <tr>
                <td><code>GET/DELETE</code></td>
                <td>/schedule/{id}</td>
                <td>Gerenciamento completo de agendamentos.</td>
            </tr>
        </tbody>
    </table>
   <h2>📝 Autor</h2>
    <p>Matheus de Souza Buth - <a href="https://www.linkedin.com/in/matheusbuth/">https://www.linkedin.com/in/matheusbuth/</a></p>
    <hr>
</body>
</html>

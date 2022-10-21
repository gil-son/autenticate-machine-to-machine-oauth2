# dsbootcamp-bds04
Challenge - Integration Test and Security

<p>This is a system of events and cities with an N-1 relationship between them:</p>

## Diagram

<a href="https://imgbox.com/Luq43BZ7" target="_blank"><img src="https://thumbs2.imgbox.com/9e/58/Luq43BZ7_t.png" alt="image host"/></a>


<p>
   In this system, only the read (GET) routes of events and cities are <b>public</b> (no login required). <b>CLIENT</b> users can also post (POST) new events. Other accesses are only allowed to <b>ADMIN</b> users.
</p>

<p>
Note: If you have questions about ResourceServerConfig authorization rules, read the Suggestion part of this document.
</p>


<ul>

<li>Validações de City:
         <ul>
         <li>Nome não pode ser vazio</li>
         </ul>
</li>
<li>Validações de Event:
       <ul>
       <li>Nome não pode ser vazio</li>
       <li>Data não pode ser passada</li>
       <li>Cidade não pode ser nula</li>
       </ul>
</li>
</ul>



## Suggestion

<ul>
   <li>ResourceServerConfig authorization rules described in natural language:
        <ol>
        <li>Login and H2 endpoints must be public</li>
        <li>The GET endpoints for /cities and /events must be public</li>
        <li>The POST endpoint of /events must require ADMIN or CLIENT login</li>
        <li>All other endpoints must require ADMIN login</li>
        </ol>
   </li>
</ul>

## Source

<ul>
  <li><a href="https://github.com/gil-son/dscatalog-bootcamp/blob/main/info/02.%20Automated%20testing.md">Dev Superior - dscatalog-bootcamp</a></li>
</ul>

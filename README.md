Projeto prático desenvolvido para a disciplina de Programação de Dispositivos Móveis.


Este é um aplicativo Android nativo (Java) que cumpre os requisitos da Atividade Prática Orientada (APO). O aplicativo é um cliente de previsão do tempo que consome a API da HG Brasil (via `woeid`) e apresenta os dados em uma lista.

## Funcionalidades Implementadas
Linguagem Java: O projeto foi inteiramente desenvolvido em Java.
Múltiplas Activities (3+):
    AberturaActivity: Uma Splash Screen com Handler (3 segundos).
    MainActivity: A tela principal com a navegação.
    SobreActivity: Tela de informações pessoais, acessada pelo menu.
Abas e Fragments:
    Aba 1 (Previsão): Um Fragment (PrevisaoFragment) que consome a API da HG Brasil (via Retrofit) e exibe os dados.
    Aba 2 (Mapa): Um Fragment (MapaFragment) que exibe um mapa (OSMdroid) com um marcador na cidade da previsão.
Lista com RecyclerView e CardView: A previsão do tempo é exibida em uma lista vertical usando RecyclerView, com cada item formatado como um CardView.
Menu na AppBar: A tela principal possui um menu na barra superior para acessar a tela "Sobre".
Material Design: Os componentes (Toolbar, TabLayout, FloatingActionButton, CardView) seguem as diretrizes do Material Design.
Leitor de QR Code (Zxing): Um FloatingActionButton na primeira aba permite ao usuário escanear um QR Code (contendo um woeid) para trocar a cidade da previsão.
Mapa Dinâmico: O marcador no mapa é atualizado para refletir a cidade selecionada na aba de previsão.

## Bibliotecas Utilizadas
Retrofit & Gson: Para consumo da API REST (HG Brasil).
RecyclerView & CardView: Para exibição da lista de previsão.
OSMdroid: Para exibição do mapa (alternativa gratuita que não exige chave de API).
Zxing (JourneyApps): Para a implementação do leitor de QR Code.
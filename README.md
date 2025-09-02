# imagej-plugin-histology-bone-matrix-ufu

Plugin ImageJ para automatização do processamento de imagens histológicas a fim de quantificação dá matriz óssea.

Como usar

Inicialmente, faça o download do software ImageJ, disponível em https://imagej.nih.gov/ij/download.html.

Em seguida
Faça o download do arquivo matrix_quantifier-0.0.1.jar, e cole-o na pasta plugins do diretório onde foi feita a instalação do ImageJ.

Execute o ImageJ e procure pelo menu Plugin/Histology/Threshold, conforme a imagem abaixo:

<img width="615" height="512" alt="image" src="https://github.com/user-attachments/assets/317c6fd2-a10e-44b9-ac1a-526dca929038" />

Uma janela irá aparecer, selecione a pasta onde estão contidas as imagens a serem processadas e clique em OK.

Quando o processo terminar, uma nova pasta, chamada matriz_plugin, será gerada com as novas imagens, com a matriz óssea destacada em vermelho. Uma janela com os resultados individuais de cada imagem contabilizando algumas informações da matriz óssea, tais como perímetro, área, etc.

Um exemplo do resultado final é mostrado abaixo:

<img width="1436" height="522" alt="image" src="https://github.com/user-attachments/assets/09e87df5-90d3-46f3-8a8c-5e26f0000a81" />

Na janela de Results é possível exportar os dados resultantes como .csv.

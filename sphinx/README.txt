indexer --config C:\focab\sphinx\sphinx_win.conf --all 

mysql -P9306 -ufocab -p123456
use focab;
selelct * from idioms;

http://localhost:9080/search?index=commonwords&match=coffee
http://localhost:9080/sql?query=select%20*%20from%20commonwords%20where%20common_word_level%3d2%20limit%205000


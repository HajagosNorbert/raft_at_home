A "game.Main" osztályt kell futtatni. 
16 -os Java-val biztosan működik, előbbivel nem biztos.
A programot érdemes IDE-ben futtatni, IntelliJ -ben biztosan működik a megjelenítés.
Amennyiben rendellenes a kinézete, futtasd az egyszerűsített változatát, amit úgy érhetsz el, hogy
argumentumnak megadod a "simple" szót

java game.Main [simple]

Amennyiben IntelliJ-ből futtatod a programot, állítsd át a run configuration-ben "working directory-t"
úgy, mintha egyszerűen futtatnád a programot. Tehát ha van egy raft_at_home gyökérkönyvtár, lehet 
valami olyasmit kell beálítani, mint [directory]/raft_at_home/out/production/raft_at_home
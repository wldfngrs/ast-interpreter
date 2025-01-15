obj: 
	javac jlox/*.java
	javac tool/GenerateAst.java

run: 
	java jlox.Lox

ast:
	java tool.GenerateAst jlox

printer:
	java jlox.AstPrinter

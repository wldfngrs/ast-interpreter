# AST (Abstract Syntax Tree) Interpreter
A small high-level language that compiles statements and expressions to an abstract syntax tree, executed by a Java backend.

## Cloning the repository
`git clone git@github.com:wldfngrs/ast-interpreter.git`

or

`git clone https://github.com/wldfngrs/ast-interpreter.git`

## Compilation
Initiate the `javac` compiler to compile the Java code to bytecode with the following command:

`make bc`

Generate the nodes of the AST:

`make ast`

Test the ast printer with a hard-coded example:

`make printer`

And, finally, run the REPL(Read-Execute-Print-Loop):

`make run`

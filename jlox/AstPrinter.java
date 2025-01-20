package jlox;

import java.util.List;

class AstPrinter implements Expr.Visitor<String>,
			    Stmt.Visitor<String> {
  String print(Expr expr) {
    return expr.accept(this);
  }

  String print(Stmt statement) {
    return statement.accept(this);
  }

  @Override
  public String visitExpressionStmt(Stmt.Expression stmt) {
    return parenthesize("exprStmt", stmt.expression);
  }

  @Override
  public String visitPrintStmt(Stmt.Print stmt) {
    return parenthesize("printStmt", stmt.expression);
  }

  @Override
  public String visitVarStmt(Stmt.Var stmt) {
    return parenthesize("=: " + stmt.name.lexeme, stmt.initializer);
  }

  @Override
  public String visitBlockStmt(Stmt.Block stmt) {
    return parenthesize("block", stmt.statements);
  }

  @Override
  public String visitClassStmt(Stmt.Class stmt) {
    return parenthesize("class " + stmt.name.lexeme, stmt.methods);
  }
  
  @Override
  public String visitIfStmt(Stmt.If stmt) {
    return parenthesize("if", stmt);
  }

  @Override
  public String visitWhileStmt(Stmt.While stmt) {
    return parenthesize("while", stmt);
  }

  @Override
  public String visitFunctionStmt(Stmt.Function stmt) {
    return parenthesize("fun " + stmt.name.lexeme, stmt.params, stmt.body);
  }

  @Override
  public String visitReturnStmt(Stmt.Return stmt) {
    return parenthesize(stmt.keyword.lexeme, stmt.value);
  }
  
  @Override
  public String visitBinaryExpr(Expr.Binary expr) {
    return parenthesize(expr.operator.lexeme, expr.left, expr.right);
  }

  @Override
  public String visitLogicalExpr(Expr.Logical expr) {
    return parenthesize(expr.operator.lexeme, expr.left, expr.right);
  }

  @Override
  public String visitSetExpr(Expr.Set expr) {
    return parenthesize("Set " + expr.name.lexeme, expr.object, expr.value);
  }

  @Override
  public String visitThisExpr(Expr.This expr) {
    return parenthesize("this");
  }

  @Override
  public String visitCallExpr(Expr.Call expr) {
    return parenthesize("call", expr.arguments, expr.callee);
  }

  @Override
  public String visitGetExpr(Expr.Get expr) {
    return parenthesize("get " + expr.name.lexeme, expr.object);
  }
  
  @Override
  public String visitGroupingExpr(Expr.Grouping expr) {
    return parenthesize("group", expr.expression);
  }
  
  @Override
  public String visitLiteralExpr(Expr.Literal expr) {
    if (expr.value == null) return "nil";
    return expr.value.toString();
  }

  @Override
  public String visitUnaryExpr(Expr.Unary expr) {
    return parenthesize(expr.operator.lexeme, expr.right);
  }

  @Override
  public String visitVariableExpr(Expr.Variable expr) {
    return parenthesize(expr.name.lexeme);
  }

  @Override
  public String visitAssignExpr(Expr.Assign expr) {
    return parenthesize("= " + expr.name.lexeme, expr.value);
  }
  
  private String parenthesize(String name, Expr... exprs) {
    StringBuilder builder = new StringBuilder();

    builder.append("(").append(name);
    for (Expr expr : exprs) {
      builder.append(" ");
      builder.append(expr.accept(this));
    }
    
    builder.append(")");

    return builder.toString();
  }

  private String parenthesize(String name, List<Token> tokens, List<Stmt> stmts) {
    StringBuilder builder = new StringBuilder();

    builder.append("(").append(name);
    
    builder.append(" params:");
    for (Token token : tokens) {
      builder.append(" ");
      builder.append(token.lexeme);
    }

    builder.append(" body:");
    for (Stmt stmt : stmts) {
      builder.append(" ");
      builder.append(stmt.accept(this));
    }

    return builder.toString();
  }

  public String parenthesize(String name) {
    StringBuilder builder = new StringBuilder(); 
    
    builder.append("(ref ").append(name).append(")");

    return builder.toString();
  }

  public String parenthesize(String name, List<? extends Stmt> stmts) {
    StringBuilder builder = new StringBuilder();

    builder.append("(").append(name);

    for (Stmt stmt : stmts) {
      builder.append(" ");
      builder.append(stmt.accept(this));
    }

    builder.append(")");

    return builder.toString();
  }

  public String parenthesize(String name, List<Expr> arguments, Expr expr) {
    StringBuilder builder = new StringBuilder();

    builder.append("(").append(name);
    builder.append(expr.accept(this));
    builder.append("args:");

    for (Expr argument : arguments) {
      builder.append(" ");
      builder.append(argument.accept(this));
    }

    builder.append(")");

    return builder.toString();
  }

  public String parenthesize(String name, Stmt stmt) {
    StringBuilder builder = new StringBuilder();

    builder.append("(").append(name).append(" ");

    if (stmt instanceof Stmt.If) {
      builder.append(((Stmt.If)stmt).condition.accept(this));
      builder.append(" ");
      builder.append("then ").append(((Stmt.If)stmt).thenBranch.accept(this));
      builder.append(" ");
      builder.append("else ").append(((Stmt.If)stmt).elseBranch.accept(this));
    } else if (stmt instanceof Stmt.While){
      builder.append(((Stmt.While)stmt).condition.accept(this));
      builder.append(" ");
      builder.append(((Stmt.While)stmt).body.accept(this));
    }

    builder.append(")");
    
    return builder.toString();
  }

  public static void main(String[] args) {
    Expr expression = new Expr.Binary(
		    new Expr.Unary(
			    new Token(TokenType.MINUS, "-", null, 1),
			    new Expr.Literal(123)),
		    new Token(TokenType.STAR, "*", null, 1),
		    new Expr.Grouping(
			    new Expr.Literal(45.67))
		    );
    System.out.println(new AstPrinter().print(expression));
  }
}

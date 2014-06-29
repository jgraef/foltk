foltk - First-Order Logic ToolKit
=================================

__WORK IN PROGESS__

A toolkit for parsing, transforming and reasoning in first-order logic.
This toolkit is intended for educational purpose. Therefore everything was
implemented from scratch. At this point there are __no__ external dependencies.

As soon I get the transformation to Clausal Normal Form going, I will implement
unification. I also intend to extend the toolkit to a fully functional compiler.
In the end this toolkit should be able to do any first-order logic reasoning
only given a set of source files.
Nevertheless it should also be possible to use algorithms by only using internal
data structures - without the need to generate source code. This will be useful
for integration into other projects.

Following things are already implemented:

* Parser for first-order logic formulas
* Transformation to Prenex Normal Form
* Transformation to Skolem Normal Form


Formula
-------

foltk will parse any first-order logic formula that uses predicates, negation,
conjunction, disjunction, implication, biconditional, existential or universal
quantors. Predicates work over terms which can consist of constants, functions
and variables (which are quantified over).

Also one source file can contain more than one propositions. Propositions are
seperated by a semicolon `;`.

I'm also working on imports which will import other source files or
(pre-compiled) clausal normal forms

A sample source file could look like this:

```
Cat(Garfield);
Dog(Lassie);
Chases(Lassie, Garfield);

forall x Dog(x) => exists y Chases(x, y); // Every dog chases something
```


TODO
----

* Transformation to Clausal Normal Form
* I'm not sure yet, if I want to implement renaming in Prenex transformation
  step. This isn't necessary, since variables with same symbol are
  distinguishable by the scope the come from.
* Compiler, which will do all the work in the end. The compiler will also
  resolve imports, by either loading precompiled CNF or plain formulas.
* Generate a AST transformer from a source file, which specifies some rewriting
  rules. Again, this isn't necessary at all, but would be cool.
* **Unification**: I definitely need algorithms for unification. After all this
  resolves to an algorithm for finding the most general unifier.
* Nice error messages. Especially type errors don't output any messages at all
  at the moment.


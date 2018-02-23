JAVA = javac
RM = rm -rf
OBJECTS = Maze.class Node.class main.class

.SUFFIXES: .class .java
.java.class:
	$(JAVA) $< 

.PHONY: all clean 

all: $(OBJECTS)
	$(JAVA) main.java

clean: 
	$(RM) *.class
